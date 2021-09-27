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
import it.usr.web.neve.model.Stat;
import java.math.BigDecimal;
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
public class StatisticheService {
    @PersistenceContext
    EntityManager em;
    
    public List<Stat> getStatistichePerComune() {
        List<Stat> stat = new ArrayList<>();
        
        List<Comune> lCom = em.createNamedQuery("Comune.findAll").getResultList();
        lCom.forEach(com -> {
            List<Istruttoria> lIst = em.createNamedQuery("Istruttoria.findByComune").setParameter("comune", com).getResultList();
            int numeroPratiche = lIst.size();
            BigDecimal totale = new BigDecimal(0);
            for(Istruttoria ist : lIst) {
                totale = totale.add(ist.getTotale());
            }
            
            stat.add(new Stat(com.getComune(), numeroPratiche, totale));
        });
        
        return stat;
    }
    
    public List<Stat> getStatistichePerEsito() {
        List<Stat> stat = new ArrayList<>();
        
        List<Esito> lEsito = em.createNamedQuery("Esito.findByAbilitato").setParameter("abilitato", true).getResultList();
        lEsito.forEach(esito -> {
            List<Istruttoria> lIst = em.createNamedQuery("Istruttoria.findByEsito").setParameter("esito", esito).getResultList();
            int numeroPratiche = lIst.size();
            BigDecimal totale = new BigDecimal(0);
            for(Istruttoria ist : lIst) {
                totale = totale.add(ist.getTotale());
            }
            
            stat.add(new Stat(esito.getEsito(), numeroPratiche, totale));
        });
        
        return stat;
    }
    
    public List<Stat> getStatistichePerStato() {
        List<Stat> stat = new ArrayList<>();
        
        List<Statolavori> lStatoLav = em.createNamedQuery("Statolavori.findByAbilitato").setParameter("abilitato", true).getResultList();
        lStatoLav.forEach(statoLav -> {
            List<Istruttoria> lIst = em.createNamedQuery("Istruttoria.findByStato").setParameter("stato", statoLav).getResultList();
            int numeroPratiche = lIst.size();
            BigDecimal totale = new BigDecimal(0);
            for(Istruttoria ist : lIst) {
                totale = totale.add(ist.getTotale());
            }
            
            stat.add(new Stat(statoLav.getStato(), numeroPratiche, totale));
        });
        
        return stat;
    }
}
