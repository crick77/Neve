/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.producer.NeveLogger;
import it.usr.web.neve.service.IstruttoriaService;
import java.util.Map;
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
    Map<String, Long> lavorazioni;
    long totale;
    
    public String initialize() {
        Utente u = getUtente();
        if(!u.getAdmin()) {
            logger.debug("L'utente {} non dispone dei diritti di visualizzione delle lavorazioni", u.getUsername());
            return "unauth";
        }
        
        lavorazioni = is.getLavorazioniUtente();
        totale = 0;
        lavorazioni.values().forEach(v -> {
            totale+=v;
        });
        return SAME_VIEW;
    }

    public Map<String, Long> getLavorazioni() {
        return lavorazioni;
    }

    public void setLavorazioni(Map<String, Long> lavorazioni) {
        this.lavorazioni = lavorazioni;
    }   
    
    public long getTotaleLavorate() {
        return this.totale;
    }
}
