package de.exaxway.cleanstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class FakePersistent {

    private int imageId = 0;
    private Map<String, Map<String, byte[]>> sessionBoxIdImageMap = new HashMap<>();

    public byte[] getImageData(final String sessionId, final String imageName) {
        final Map<String, byte[]> imageMap = sessionBoxIdImageMap.get(sessionId);
        return imageMap.get(imageName);
    }

    public String putImageData(final String sessionId, final String boxDataId, final byte[] data) {
        final String imageName = "photo_" + imageId++ + ".png";

        Map<String, byte[]> imageMap = sessionBoxIdImageMap.get(sessionId);

        if (imageMap == null) {
            imageMap = new HashMap<>();
            sessionBoxIdImageMap.put(sessionId, imageMap);
        }

        imageMap.put(imageName, data);

        return imageName;
    }

    public void remove(String sessionId, final String imageName) {
        sessionBoxIdImageMap.get(sessionId).remove(imageName);
    }

    public List<String> getImageNames(final String sessionId) {
        final Map<String, byte[]> imageMap = sessionBoxIdImageMap.get(sessionId);
        if (imageMap == null) {
            return new ArrayList<String>();
        }
        return new ArrayList<String>(imageMap.keySet());
    }
}
