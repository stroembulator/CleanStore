package de.exaxway.cleanstore;

import java.util.List;

public class BoxData {
    private final String id;
    private final String qrCode;
    private final String keywords;
    private final String description;
    private List<byte[]> photoList;

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }

    BoxData(final String id, final String qrCode, final String keywords, String description) {
        this.id = id;
        this.qrCode = qrCode;
        this.keywords = keywords;
        this.description = description;
    }   

    public String getQrCode() {
        return qrCode;
    }
}
