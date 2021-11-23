/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.service;

import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.model.Lavorazioni;
import java.util.ArrayList;
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
         
    public List<Statolavori> getStatiLavoro() {
        return em.createNamedQuery("Statolavori.findAll").getResultList();
    }
    
    public List<Esito> getEsiti() {
        return em.createNamedQuery("Esito.findAll").getResultList();
    }
    
    public Comune getComune(String comune) {
        return em.find(Comune.class, comune);
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
    
    public boolean isPraticaValida(String idPratica, Integer id) {
        // Nuova pratica?
        if(id==null) {
            // Non devono essercene altre
            List<Istruttoria> lIstr = em.createNamedQuery("Istruttoria.findByIdpratica").setParameter("idpratica", idPratica).getResultList();
            return lIstr.isEmpty();
        }
        else {
            // Modifica, recupera la precedente versione
            Istruttoria i = em.find(Istruttoria.class, id);
            // Se la pratica è cambiata
            if(!i.getIdpratica().equalsIgnoreCase(idPratica)) {
                // Il nuovo numero è già stato usato?
                List<Istruttoria> lIstr = em.createNamedQuery("Istruttoria.findByIdpraticaOtherID").setParameter("idpratica", idPratica).setParameter("id", id).getResultList();
                return lIstr.isEmpty();
            }
            
            // La pratica non è cambiata, tutto ok
            return true;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Istruttoria salva(Istruttoria is) {
        if(is.getId()!=null) {
            return em.merge(is);
        }
        
        em.persist(is);
        return is;        
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elminia(Istruttoria is) {
        if(is==null || is.getId()==null) throw new IllegalArgumentException("istruttoria or id null");
        
        Istruttoria i = em.find(Istruttoria.class, is.getId());
        em.remove(i);        
    }
    
    public List<Lavorazioni> getLavorazioniUtente() {
        List<Utente> utenti = em.createNamedQuery("Utente.findByAbilitatoSorted", Utente.class).setParameter("abilitato", true).getResultList();
        List<Lavorazioni> lLav = new ArrayList<>();
        double total = 0;
        for(Utente u : utenti) {
            long count = em.createQuery("SELECT count(i) FROM Istruttoria i WHERE i.proprietario = :utente", Long.class).setParameter("utente", u).getSingleResult();
            total+=count;
            Lavorazioni lav = new Lavorazioni(u.getUsername(), count, 0);
            
            lLav.add(lav);
        }
        
        for(Lavorazioni l : lLav) {
            double perc = l.getNumLavorazioni()/total;
            l.setPercentuale(perc);
        }
        
        return lLav;
    }
}
