/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore;

import de.exaxway.cleanstore.persist.BoxData;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name = "boxDataService")
@ApplicationScoped
public class BoxDataService {
    private final static String[] descriptions;
    private final static String[] keywords;
     
    private final static String[] qrCodes;
    private BoxData selectedBoxData = null;
    private long maxid = 500;
    
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
     
    private long getDummyId() {
        return maxid++;
    }
     
    private String getDummyDescription() {
        return descriptions[(int) (Math.random() * 10)];
    }
    
     
    private String getDummyQrCode(final int x) {
        return qrCodes[x];
    }
    
     private String getDummyKeyword() {
        return keywords[(int) (Math.random() * 10)];
    }
        
    public BoxData getSelectedBoxData() {
        return selectedBoxData;
    }
    
    public List<BoxData> createBoxData(int size) {
        List<BoxData> list = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            BoxData boxData = new BoxData();
            boxData.setId(getDummyId());
            boxData.setQrCode(getDummyQrCode(i));
            boxData.setKeywords(getDummyKeyword());
            boxData.setDescription(getDummyDescription());
            list.add(boxData);
        }
         
        return list;
    }

}