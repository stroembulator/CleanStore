/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CaptureEvent;
import org.primefaces.model.DefaultStreamedContent;

@ManagedBean(name="boxDataView")
@ViewScoped
public class BasicView implements Serializable {

//    @EJB
//    FakePersistent boxDataPersistent;

    Logger LOG = Logger.getLogger(PhotoCamView.class.getName());

    private List<BoxData> boxData;

//    @ManagedProperty("#{carService}")
    private BoxDataService boxDataServicexx;
    private BoxData selectedBoxData;

    @PostConstruct
    public void init() {
//        boxData = boxDataServicexx.createCars(10);
//        selectedBoxData = boxDataServicexx.getSelectedBoxData();
    }

    public List<BoxData> getBoxDataList() {
        return boxData;
    }

    public void setService(BoxDataService service) {
        this.boxDataServicexx = service;
    }

    public void setSelectedBoxData(final BoxData selectedBoxData) {
        this.selectedBoxData = selectedBoxData;
    }

    public BoxData getSelectedBoxData() {
        return selectedBoxData;
    }

    public void oncapture(final CaptureEvent captureEvent) {
        LOG.info("oncapture");
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();

        byte[] data = captureEvent.getData();

        DefaultStreamedContent lastCaptured = new DefaultStreamedContent(new ByteArrayInputStream(data), "image/png");
        lastCaptured.setContentType("image/png");
        lastCaptured.setName("lc.png");
        lastCaptured.setContentEncoding("image/png");
        //Name = "photo_" + imageId++;

     //    boxDataService.putImageData(sessionId, imageName, data);
        //   this.selectedBoxData.getPhotoList().add(data);
    }
}
