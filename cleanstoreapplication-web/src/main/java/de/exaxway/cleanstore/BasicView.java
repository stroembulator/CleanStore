/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import de.exaxway.cleanstore.persist.BoxData;
import de.exaxway.cleanstore.persist.PhotoData;
import de.exaxway.cleanstore.persist.PhotoDataFacadeLocal;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CaptureEvent;

@ManagedBean(name = "boxDataView")
@SessionScoped
public class BasicView implements Serializable {
    private static final Logger LOG = Logger.getLogger(BasicView.class.getName());
    
    private String a;

    @EJB
    private PhotoDataFacadeLocal photoDataFacade;

    @EJB
    private de.exaxway.cleanstore.persist.BoxDataFacadeLocal boxDataFacade;

    private boolean mobileWorkaroundEnabled = false;

    private String lastCapturedName = null;

    private List<BoxData> boxDataList;

    private BoxData selectedBoxData;
    private String code = null;

    @PostConstruct
    public void init() {
        boxDataList = boxDataFacade.findAll();
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
    }

    public List<BoxData> getBoxDataList() {
        return boxDataList;
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

            PhotoData photoData = new PhotoData();
            photoDataFacade.create(photoData);
            lastCapturedName = "img_" + photoData.getId();

            photoData.setName(lastCapturedName);
            photoData.setPhotoData(data);
            photoDataFacade.edit(photoData);

            this.selectedBoxData.getPhotoList().add(photoData);
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

    public List<String> getImageNames() {
        return selectedBoxData.getPhotoList().stream().map(p -> p.getName()).collect(Collectors.toList());
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
        } catch (IOException | NotFoundException e) {
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

    public boolean isMobileWorkaroundEnabled() {
        return mobileWorkaroundEnabled;
    }

    public void setMobileWorkaroundEnabled(boolean mobileWorkaroundEnabled) {
        this.mobileWorkaroundEnabled = mobileWorkaroundEnabled;
    }

    public static String readQRCode(byte[] data)
            throws FileNotFoundException, IOException, NotFoundException {
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new ByteArrayInputStream(data)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                hintMap);
        return qrCodeResult.getText();
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
