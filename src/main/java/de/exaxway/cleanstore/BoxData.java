package de.exaxway.cleanstore;

import java.util.ArrayList;
import java.util.List;

public class BoxData {
    private final String id;
    private String qrCode;

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    private String keywords;

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private String description;
    private final List<byte[]> photoList = new ArrayList<>();
    private final List<String> photoNameList = new ArrayList<>();

    public List<byte[]> getPhotoList() {
        return photoList;
    }

    public String getId() {
        return id;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }

    public BoxData(final String id) {
        this.id = id;
    }   

    public String getQrCode() {
        return qrCode;
    }

    List<String> getPhotoNameList() {
        return photoNameList;
    }
}
