/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.services.IstruttoriaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class IstruttorieController implements Serializable {
    @Inject
    IstruttoriaService is;
    private List<Istruttoria> istruttorie;
    private List<Istruttoria> filteredIstruttorie; 
    private List<Statolavori> statilavoro;    
    private List<Esito> esiti;
    private List<Comune> comuni;
    private BigDecimal totale;

    public void initialize() {
        istruttorie = is.getIstruttorie();
        filteredIstruttorie = istruttorie;
        statilavoro = is.getStatiLavoro();
        esiti = is.getEsiti();
        comuni = is.getComuni(IstruttoriaService.ABILITATI_E_DISABILITATI);
                
        ricalcolaTotale();
    }

    public List<Istruttoria> getIstruttorie() {
        return istruttorie;
    }
   
    public BigDecimal getTotale() {
        return totale;
    }
    
    public String format(Object data, String format) {
        return new DecimalFormat(format).format(data);
    }
      
    public List<Istruttoria> getFilteredIstruttorie() {
        return filteredIstruttorie;
    }

    public void setFilteredIstruttorie(List<Istruttoria> filteredIstruttorie) {
        this.filteredIstruttorie = filteredIstruttorie;
          
        ricalcolaTotale();
    }        
           
    public List<Statolavori> getStatiLavoro() { 
        return statilavoro;
    }

    public List<Esito> getEsiti() {
        return esiti;
    }

    public List<Comune> getComuni() {
        return comuni;
    }
            
    public void loadAllegati(Istruttoria i) {
        Istruttoria _i = is.getIstruttorieAllegati(i);
        i.setAllegatoList(_i.getAllegatoList());
    }
               
    public String editPratica(int idPratica) {
        return "istruttoria?includeViewParams=true&faces-redirect=true&id="+idPratica;
    }     
    
    public void ricalcolaTotale() {
        totale = new BigDecimal(0);
        filteredIstruttorie.forEach(i -> {
            totale = totale.add(i.getTotale());
        });
        System.out.println("Totale = "+totale);
    }
}
 