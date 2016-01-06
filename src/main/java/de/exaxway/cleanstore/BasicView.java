/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
 
@ManagedBean(name="dtBasicView")
@ViewScoped
public class BasicView implements Serializable {
     
    private List<BoxData> cars;
     
    @ManagedProperty("#{carService}")
    private BoxDataService service;
 
    @PostConstruct
    public void init() {
        cars = service.createCars(10);
    }
     
    public List<BoxData> getCars() {
        return cars;
    }
 
    public void setService(BoxDataService service) {
        this.service = service;
    }
    
    public void setSelectedBoxData(final String id) {
        String u = id;
    }
    
    public String getSelectedBoxData() {
        return null;
    }
}