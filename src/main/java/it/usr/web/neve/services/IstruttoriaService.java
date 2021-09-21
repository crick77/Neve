/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.services;

import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class IstruttoriaService {
    public final static boolean SOLO_ABILITATI = true;
    public final static boolean ABILITATI_E_DISABILITATI = !SOLO_ABILITATI;
    @PersistenceContext
    EntityManager em;

    public List<Istruttoria> getIstruttorie() {
        return em.createNamedQuery("Istruttoria.findAll").getResultList();
    }
        
    public Istruttoria getIstruttoria(int id) {
        return em.find(Istruttoria.class, id);
    }
    
    public Istruttoria getIstruttorieAllegati(Istruttoria i) {
        i = em.find(Istruttoria.class, i.getId());
        i.getAllegatoList();
        return i;
    }
    
    public List<Statolavori> getStatiLavoro() {
        return em.createNamedQuery("Statolavori.findAll").getResultList();
    }
    
    public List<Esito> getEsiti() {
        return em.createNamedQuery("Esito.findAll").getResultList();
    }
    
    public Esito getEsito(String esito) {
        return em.find(Esito.class, esito);
    }
    
    public Statolavori getStatoLavori(String statoLavori) {
        return em.find(Statolavori.class, statoLavori);
    }
    
    public List<Comune> getComuni(boolean soloAbilitati) {
        if(soloAbilitati) {
            return em.createNamedQuery("Comune.findByAbilitato").setParameter("abilitato", soloAbilitati).getResultList();
        }
        else {
            return em.createNamedQuery("Comune.findAll").getResultList();
        }
    }
    
    public boolean isPraticaValida(int idPratica) {
        List<Istruttoria> lIstr = em.createNamedQuery("Istruttoria.findByIdpratica").setParameter("idpratica", idPratica).getResultList();
        return lIstr.isEmpty();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Istruttoria save(Istruttoria is) {
        em.persist(is);
        return is;
    }
}
