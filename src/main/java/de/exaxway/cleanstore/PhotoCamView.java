package de.exaxway.cleanstore;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CaptureEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

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

	private String code = null;

	public StreamedContent getImage() {
		return xx;
	}

	public StreamedContent getImageNew() {
		return image;
	}

	public String getFilename() {
		return filename;
	}


	public String getLastCapturedName() {
		return lastCapturedName;
	}
	
	public void oncaptureCode(final CaptureEvent captureEvent) {
		LOG.info("oncaptureCode");
		byte[] data = captureEvent.getData();
		try {
			code = readQRCode(data);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "scan failed", e);
		}
		
	}
	
	public String getCode() {
		return code;
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
}