/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.service;

import it.usr.web.neve.domain.Oopp;
import it.usr.web.neve.domain.Stato;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class OOPPService {
    @PersistenceContext
    EntityManager em;
    
    public List<Oopp> getOOPP() {
        return em.createNamedQuery("Oopp.findAll").getResultList();
    }

    public Oopp getOp(Integer id) {
        return em.find(Oopp.class, id);
    }
    
    public List<String> getEnti() {
        return em.createQuery("SELECT DISTINCT o.ente FROM Oopp o", String.class).getResultList();
    }
    
    public List<String> getSoggettiAttuatori() {
        return em.createQuery("SELECT DISTINCT o.attuatore FROM Oopp o", String.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salva(Oopp op) {
        if(op.getId()!=null) {
            em.merge(op);
        }
        else {
            em.persist(op);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elimina(Oopp op) {
        Oopp _o = em.find(Oopp.class, op.getId());
        if(_o!=null) em.remove(_o);
    }

    public List<Stato> getStati() {
        return em.createNamedQuery("Stato.findAll").getResultList();
    }
    
    public Stato getStato(Integer id) {
        return em.find(Stato.class, id);
    }
    
    public Oopp findByIdPratica(String idPratica) {
        try {
            return em.createNamedQuery("Oopp.findByIdPratica", Oopp.class).setParameter("idPratica", idPratica).getSingleResult();
        }
        catch(NoResultException nre) {
            return null;
        }
    }
    
    public Oopp findByCUP(String cup) {
        try {
            return em.createNamedQuery("Oopp.findByCup", Oopp.class).setParameter("cup", cup).getSingleResult();
        }
        catch(NoResultException nre) {
            return null;
        }
    }
}
