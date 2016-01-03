package de.exaxway.cleanstore;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.primefaces.event.CaptureEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class PhotoCamView {
	private List<String> images = Arrays.asList(new String[]{
//		"1.jpg", "2.jpg", "3.jpg"
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
	private String lastCapturedName = "ss";
	private DefaultStreamedContent graphicText;
	private byte[] byteArray;

	@PostConstruct
	public void init() {
		try {
	         //Graphic Text
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            byteArray = os.toByteArray();
			graphicText = new DefaultStreamedContent(new ByteArrayInputStream(byteArray), "image/png"); 
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			File f = new File("/tmp/a2.png");
			xx =  new DefaultStreamedContent(new FileInputStream(f), "image/png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
	}

	public StreamedContent getImage() {
		return xx;
	}
	
	public StreamedContent getImageNew() {
		return image;
	}

	private String getRandomImageName() {
		int i = (int) (Math.random() * 10000000);

		return String.valueOf(i);
	}

	public String getFilename() {
		return filename;
	}

	public void oncapture(CaptureEvent captureEvent) {
		filename = getRandomImageName();
		byte[] data = captureEvent.getData();
		

		lastCaptured = new DefaultStreamedContent(new ByteArrayInputStream(data), "image/png");
		lastCaptured.setContentType("image/png");
		lastCaptured.setName("lc.png");
		String contentEncoding;
		lastCaptured.setContentEncoding("image/png");
		lastCapturedName = "x0";
//		lastCaptured.setContentType(contentType);
//		lastCaptured.s
//		contentType.

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String newFileName = "/tmp/a.png";
//		String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo"
//				+ File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";

		 FileImageOutputStream imageOutput;
		 try {
		 imageOutput = new FileImageOutputStream(new File(newFileName));
		 imageOutput.write(data, 0, data.length);
		 imageOutput.close();
		 }
		 catch(IOException e) {
		 throw new FacesException("Error in writing captured image.", e);
		 }

	}
	
	public void oncaptureNew(CaptureEvent captureEvent) {
		filename = getRandomImageName();
		byte[] data = captureEvent.getData();

//		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
//				.getContext();
////		String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo"
//				+ File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";

		// FileImageOutputStream imageOutput;
		// try {
		// imageOutput = new FileImageOutputStream(new File(newFileName));
		// imageOutput.write(data, 0, data.length);
		// imageOutput.close();
		// }
		// catch(IOException e) {
		// throw new FacesException("Error in writing captured image.", e);
		// }
		///(ImageIO.write(im, formatName, output)
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(data, 0, data.length);
//		image = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
		image = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()));

	}
	
	public StreamedContent getLastCaptured() {
//		return lastCaptured;
		return graphicText;
	}
	
	public String getLastCapturedName() {
		return lastCapturedName;
	}
	
    public StreamedContent getChart() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            return graphicText;
       }

     }


}