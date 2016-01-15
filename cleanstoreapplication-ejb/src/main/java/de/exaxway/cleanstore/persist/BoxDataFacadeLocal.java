/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore.persist;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author steff
 */
@Local
public interface BoxDataFacadeLocal {

    void create(BoxData boxData);

    void edit(BoxData boxData);

    void remove(BoxData boxData);

    BoxData find(Object id);

    List<BoxData> findAll();

    List<BoxData> findRange(int[] range);

    int count();
    
}
