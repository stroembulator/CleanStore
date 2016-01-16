/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore.experimental;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Exp {
    private Map image = new HashMap();

    public void setCimage(Map image) {
        this.image = image;
    }

    public Map getCimage() {
        return image;
    }
}
