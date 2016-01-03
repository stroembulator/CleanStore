package de.exaxway.cleanstore;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;

@Stateless
public class Helper {
	private byte[] byteArray;


	public byte[] getLD() {
		// TODO Auto-generated method stub
		return byteArray;
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
            byteArray = os.toByteArray();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
