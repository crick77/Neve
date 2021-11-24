/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.model.Lavorazioni;
import it.usr.web.neve.producer.NeveLogger;
import it.usr.web.neve.service.IstruttoriaService;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class LavorazioniController extends BaseController {
    @Inject
    IstruttoriaService is;
    @Inject
    @NeveLogger
    Logger logger;
    List<Lavorazioni> lavorazioni;
    long totalePratiche;
    BigDecimal totaleImporto;
    
    public String initialize() {
        Utente u = getUtente();
        if(!u.getAdmin()) {
            logger.debug("L'utente {} non dispone dei diritti di visualizzione delle lavorazioni", u.getUsername());
            return "unauth";
        }
        
        lavorazioni = is.getLavorazioniUtente();
        totalePratiche = 0;
        totaleImporto = new BigDecimal(0);
        lavorazioni.forEach(v -> {
            totalePratiche+=v.getNumLavorazioni();
            if(v.getImporto()!=null) {
                totaleImporto = totaleImporto.add(v.getImporto());
            }
        });
        return SAME_VIEW;
    }

    public List<Lavorazioni> getLavorazioni() {
        return lavorazioni;
    }

    public void setLavorazioni(List<Lavorazioni> lavorazioni) {
        this.lavorazioni = lavorazioni;
    }   
    
    public long getTotaleLavorate() {
        return this.totalePratiche;
    }
    
    public BigDecimal getTotaleImporto() {
        return this.totaleImporto;
    }
}
