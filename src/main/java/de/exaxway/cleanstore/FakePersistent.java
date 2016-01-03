package de.exaxway.cleanstore;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class FakePersistent {
	private Map<String, Map<String, byte[]>> sessionImageMap = new HashMap<>();

	public byte[] putImageData(final String sessionId, final String imageName) {
		final Map<String, byte[]> imageMap = sessionImageMap.get(sessionId);
		return imageMap.get(imageName);
	}

	public void getImageData(final String sessionId, final String imageName, final byte[] data) {
		Map<String, byte[]> imageMap = sessionImageMap.get(sessionId);

		if (imageMap == null) {
			imageMap = new HashMap<>();
			sessionImageMap.put(sessionId, imageMap);
		}

		imageMap.put(imageName, data);
	}

	public void remove(String sessionId, final String imageName) {
		sessionImageMap.get(sessionId).remove(imageName);
	}
}
