/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import static de.exaxway.cleanstore.PhotoCamView.readQRCode;
import de.exaxway.cleanstore.persist.BoxData;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CaptureEvent;

@ManagedBean(name = "boxDataView")
@SessionScoped
public class BasicView implements Serializable {

    @EJB
    private de.exaxway.cleanstore.persist.BoxDataFacadeLocal boxDataFacade;

    private boolean mobileWorkaroundEnabled = false;

    @EJB
    FakePersistent boxDataPersistent;

    Logger LOG = Logger.getLogger(PhotoCamView.class.getName());
    private String lastCapturedName = null;

    private List<BoxData> boxDataList;

    @ManagedProperty("#{boxDataService}")
    private BoxDataService boxDataService;

    private BoxData selectedBoxData;
    private String sessionId;
    private String code = null;

    private long maxid = 1000;

    @PostConstruct
    public void init() {
        boxDataList = boxDataFacade.findAll();
      //  boxDataList = boxDataService.createBoxData(10);
        selectedBoxData = boxDataService.getSelectedBoxData();
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        sessionId = session.getId();
    }

    public List<BoxData> getBoxDataList() {
        return boxDataList;
    }

    public void setBoxDataService(final BoxDataService service) {
        this.boxDataService = service;
    }

    public void setSelectedBoxData(final BoxData selectedBoxData) {
        this.selectedBoxData = selectedBoxData;
    }

    public BoxData getSelectedBoxData() {
        return selectedBoxData;
    }

    public void oncapture(final CaptureEvent captureEvent) {
        try {
            LOG.info("oncapture");
            FacesContext fCtx = FacesContext.getCurrentInstance();

            HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();

            byte[] data = captureEvent.getData();

            data = mobileWorkaround(data);

            lastCapturedName = boxDataPersistent.putImageData(sessionId, selectedBoxData.getId(), data);

            this.selectedBoxData.getPhotoNameList().add(lastCapturedName);
        } catch (IOException ex) {
            Logger.getLogger(BasicView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private byte[] mobileWorkaround(byte[] data) throws IOException {
        if (mobileWorkaroundEnabled) {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
            AffineTransform txR180 = AffineTransform.getScaleInstance(-1, -1);
            AffineTransform txFlip = AffineTransform.getScaleInstance(-1, 1);

            txFlip.translate(-image.getWidth(null), 0);
            txR180.translate(-image.getWidth(null), -image.getHeight(null));

            AffineTransform tx = txFlip;
            tx.concatenate(txR180);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            data = baos.toByteArray();
        }
        return data;
    }

    private List<String> images = Arrays.asList(new String[]{ // "1.jpg", "2.jpg", "3.jpg"
    });

    public List<String> getImages() {
        return images;
    }

    public List<String> getImageNames() {
        return selectedBoxData.getPhotoNameList();
    }

    public void setImages(final List<String> images) {
        this.images = images;
    }

    public String getLastCapturedName() {
        return lastCapturedName;
    }

    public void oncaptureCode(final CaptureEvent captureEvent) {
        LOG.info("oncaptureCode");
        byte[] data = captureEvent.getData();

        try {
            data = mobileWorkaround(data);
            code = readQRCode(data);
            selectedBoxData = boxDataList.stream().filter(b -> b.getQrCode().equals(code)).findFirst().get();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "scan failed", e);
        }

    }

    public String getCode() {
        return code;
    }

    public void add() {
        BoxData boxData = new BoxData();
        boxDataFacade.create(boxData);
        // TODO: remove
        boxDataList.add(boxData);
    }

    public void savedetail() {
        boxDataFacade.edit(selectedBoxData);
    }
 
    public void delete() {
        selectedBoxData = null;
        boxDataFacade.remove(selectedBoxData);
        // TODO: remove
        boxDataList.remove(selectedBoxData);
    }

    private Map image = new HashMap();

    public void setCimage(Map image) {
        this.image = image;
        LOG.log(Level.INFO, "setImage: " + image.toString());
    }

    public Map getCimage() {
        return image;
    }

    public boolean isMobileWorkaroundEnabled() {
        return mobileWorkaroundEnabled;
    }

    public void setMobileWorkaroundEnabled(boolean mobileWorkaroundEnabled) {
        this.mobileWorkaroundEnabled = mobileWorkaroundEnabled;
    }
}
