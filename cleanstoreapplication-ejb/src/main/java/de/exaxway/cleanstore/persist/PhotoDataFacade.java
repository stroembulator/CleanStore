/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exaxway.cleanstore.persist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

/**
 *
 * @author steff
 */
@Stateless
public class PhotoDataFacade extends AbstractFacade<PhotoData> implements PhotoDataFacadeLocal {

    @PersistenceContext(unitName = "de.exaxway.cleanstore_cleanstoreapplication-ejb_ejb_1.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoDataFacade() {
        super(PhotoData.class);
    }

    public PhotoData findByName(final String imageName) {
        return (PhotoData) getEntityManager().createQuery("SELECT p FROM " + PhotoData.class.getSimpleName() + " p WHERE p.name=:photoName")
                .setParameter("photoName", imageName)
                .getSingleResult();
    }
}
