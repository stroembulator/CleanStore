package de.exaxway.cleanstore;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class BoxData {
    @Id()
    protected long id;
    private String qrCode;
    private String description;
    private List<byte[]> photoList;
}