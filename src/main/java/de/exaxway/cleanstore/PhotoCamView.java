package de.exaxway.cleanstore;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CaptureEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class PhotoCamView {
	Logger LOG = Logger.getLogger(PhotoCamView.class.getName());

	@EJB
	FakePersistent photoCamView;

	private int imageId = 0;
	
	private List<String> images = Arrays.asList(new String[] {
			// "1.jpg", "2.jpg", "3.jpg"
	});

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	private String filename;
	private DefaultStreamedContent image = null;
	private DefaultStreamedContent xx;
	private DefaultStreamedContent lastCaptured = null;
	private String lastCapturedName = null;

	public StreamedContent getImage() {
		return xx;
	}

	public StreamedContent getImageNew() {
		return image;
	}

	public String getFilename() {
		return filename;
	}

	public void oncapture(final CaptureEvent captureEvent) {
		LOG.info("oncapture");
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();

		byte[] data = captureEvent.getData();

		lastCaptured = new DefaultStreamedContent(new ByteArrayInputStream(data), "image/png");
		lastCaptured.setContentType("image/png");
		lastCaptured.setName("lc.png");
		lastCaptured.setContentEncoding("image/png");
		lastCapturedName = "photo_" + imageId++;

		// photoCamView.add(sessionId, data);
		photoCamView.getImageData(sessionId, lastCapturedName, data);
	}

	public String getLastCapturedName() {
		return lastCapturedName;
	}
}