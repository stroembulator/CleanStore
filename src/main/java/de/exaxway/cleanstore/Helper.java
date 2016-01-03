package de.exaxway.cleanstore;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.imageio.ImageIO;
	
@Startup
@Singleton
public class Helper {
	private Map<String, byte[]> byteArray = new HashMap<>();


	public byte[] getLD(final String sessionId) {
//		return byteArray.get(sessionId);
		return byteArray.get("x");
	}

	@PostConstruct
	public void init() {
		try {
	         //Graphic Text
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            byteArray.put("xx", os.toByteArray());
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public void add(final String sessionId, final byte[] data) {
		byteArray.put(sessionId, data);
	}
}
