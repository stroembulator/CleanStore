/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CaptureEvent;
import org.primefaces.model.DefaultStreamedContent;

@ManagedBean(name = "boxDataView")
@SessionScoped
public class BasicView implements Serializable {

    @EJB
    FakePersistent boxDataPersistent;

    Logger LOG = Logger.getLogger(PhotoCamView.class.getName());
    private String lastCapturedName = null;

    private List<BoxData> boxData;

    @ManagedProperty("#{boxDataService}")
    private BoxDataService boxDataService;

    private BoxData selectedBoxData;
    private String sessionId;

    @PostConstruct
    public void init() {
        boxData = boxDataService.createBoxData(10);
        selectedBoxData = boxDataService.getSelectedBoxData();
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        sessionId = session.getId();

    }

    public List<BoxData> getBoxDataList() {
        return boxData;
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
        LOG.info("oncapture");

        byte[] data = captureEvent.getData();

        lastCapturedName = boxDataPersistent.putImageData(sessionId, selectedBoxData.getId(), data);

        DefaultStreamedContent lastCaptured = new DefaultStreamedContent(new ByteArrayInputStream(data), "image/png");
        lastCaptured.setContentType("image/png");
        lastCaptured.setName(lastCapturedName);
        lastCaptured.setContentEncoding("image/png");

        this.selectedBoxData.getPhotoNameList().add(lastCapturedName);
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

}
