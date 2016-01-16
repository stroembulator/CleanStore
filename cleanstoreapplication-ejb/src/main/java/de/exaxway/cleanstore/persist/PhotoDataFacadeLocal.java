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
public interface PhotoDataFacadeLocal {

    void create(PhotoData photoData);

    void edit(PhotoData photoData);

    void remove(PhotoData photoData);

    PhotoData find(Object id);

    List<PhotoData> findAll();

    List<PhotoData> findRange(int[] range);

    int count();

// TODO: extract to service class    
    public PhotoData findByName(String imageName);
}
