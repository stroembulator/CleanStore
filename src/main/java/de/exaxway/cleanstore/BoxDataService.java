/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name = "boxDataService")
@ApplicationScoped
public class BoxDataService {
     
    private final static String[] colors;
     
    private final static String[] brands;
     
    static {
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";
         
        brands = new String[10];
        brands[0] = "BMW";
        brands[1] = "Mercedes";
        brands[2] = "Volvo";
        brands[3] = "Audi";
        brands[4] = "Renault";
        brands[5] = "Fiat";
        brands[6] = "Volkswagen";
        brands[7] = "Honda";
        brands[8] = "Jaguar";
        brands[9] = "Ford";
    }
     
    public List<Car> createCars(int size) {
        List<Car> list = new ArrayList<Car>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState()));
        }
         
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }
     
    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
    }
     
    private String getRandomBrand() {
        return brands[(int) (Math.random() * 10)];
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
 
    public List<String> getColors() {
        return Arrays.asList(colors);
    }
     
    public List<String> getBrands() {
        return Arrays.asList(brands);
    }




     
    private final static String[] descriptions;
    private final static String[] keywords;
     
    private final static String[] qrCodes;
    private BoxData selectedBoxData = null;
    
    static {
        keywords = new String[10];
        keywords[0] = "Naegel";
        keywords[1] = "Glaeser";
        keywords[2] = "Werkzeug";
        keywords[3] = "Lampen Kabel";
        keywords[4] = "Buerokram";
        keywords[5] = "Bastel";
        keywords[6] = "Unsortiert";
        keywords[7] = "Besteck";
        keywords[8] = "Toepfe";
        keywords[9] = "Computerkram";
         
        descriptions = new String[10];
        descriptions[0] = "Mein Zeug.";
        descriptions[1] = "Muss durchgesehen werden.";
        descriptions[2] = "Soll verkauft werden.";
        descriptions[3] = "Keine Beschreibung.";
        descriptions[4] = "Geliehen.";
        descriptions[5] = "Meins!";
        descriptions[6] = "Mein Zeug.";
        descriptions[7] = "?";
        descriptions[8] = "Verschenken.";
        descriptions[9] = "Aufräumen.";
         
        qrCodes = new String[10];
        qrCodes[0] = "box123";
        qrCodes[1] = "box124";
        qrCodes[2] = "box125";
        qrCodes[3] = "box126";
        qrCodes[4] = "box127";
        qrCodes[5] = "box128";
        qrCodes[6] = "box129";
        qrCodes[7] = "box130";
        qrCodes[8] = "box131";
        qrCodes[9] = "box132";
    }
     
    private String getDummyId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private String getDummyDescription() {
        return descriptions[(int) (Math.random() * 10)];
    }
    
     
    private String getDummyQrCode() {
        return qrCodes[(int) (Math.random() * 10)];
    }
    
     private String getDummyKeyword() {
        return keywords[(int) (Math.random() * 10)];
    }
        
    public BoxData getSelectedBoxData() {
        return selectedBoxData;
    }
}