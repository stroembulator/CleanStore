/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore.persist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author steff
 */
@Entity
public class BoxData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qrCode;
    private String description;
    private List<byte[]> photoList = new ArrayList<>();
    private List<String> photoNameList = new ArrayList<>();
    private String keywords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<byte[]> getPhotoList() {
        return photoList;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }

    public String getQrCode() {
        return qrCode;
    }

    public List<String> getPhotoNameList() {
        return photoNameList;
    }

    public void setPhotoList(final List<byte[]> photoList) {
        this.photoList = photoList;
    }

    public void setPhotoNameList(final List<String> photoNameList) {
        this.photoNameList = photoNameList;
    }

    @Override
    public String toString() {
        return "de.exaxway.cleanstore.persist.BoxData[ id=" + id + " ]";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoxData)) {
            return false;
        }
        BoxData other = (BoxData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
