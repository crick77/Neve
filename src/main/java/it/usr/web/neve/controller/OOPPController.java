/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Oopp;
import it.usr.web.neve.service.OOPPService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named(value = "OOPPController")
@ViewScoped
public class OOPPController extends BaseController {
    @Inject
    OOPPService os;
    List<Oopp> oopp;
    List<Oopp> filteredOopp;
    BigDecimal totaleErogato;
    BigDecimal totaleAssegnato;
    BigDecimal totaleRimanenza;
    Oopp selezionata;
    
    public void initialize() {
        oopp = os.getOOPP();
        filteredOopp = oopp;
        selezionata = null;
        
        ricalcolaTotali();
    }

    public List<Oopp> getOopp() {
        return oopp;
    }

    public void setOopp(List<Oopp> oopp) {
        this.oopp = oopp;
    }       

    public List<Oopp> getFilteredOopp() {
        return filteredOopp;
    }

    public void setFilteredOopp(List<Oopp> filteredOopp) {
        this.filteredOopp = filteredOopp;
        
        ricalcolaTotali();
    } 
        
    public BigDecimal getTotaleErogato() {
        return totaleErogato;
    }

    public BigDecimal getTotaleRimanenza() {
        return totaleRimanenza;
    }

    public BigDecimal getTotaleAssegnato() {
        return totaleAssegnato;
    }
        
    public Oopp getSelezionata() {
        return selezionata;
    }

    public void setSelezionata(Oopp selezionata) {
        this.selezionata = selezionata;
    }
                     
    public BigDecimal erogato(Oopp oopp) { 
        BigDecimal _totale = BigDecimal.ZERO;
        if(oopp.getPanticImporto()!=null) _totale = _totale.add(oopp.getPanticImporto());
        if(oopp.getSanticImporto()!=null) _totale = _totale.add(oopp.getSanticImporto());
        if(oopp.getPsalImporto()!=null) _totale = _totale.add(oopp.getPsalImporto());
        if(oopp.getSsalImporto()!=null) _totale = _totale.add(oopp.getSsalImporto());
        if(oopp.getSaldoImporto()!=null) _totale = _totale.add(oopp.getSaldoImporto());
        
        return _totale;        
    }
    
    public BigDecimal rimanenza(Oopp oopp) {
        BigDecimal _totale = erogato(oopp);
        return oopp.getImportoAssegnato().subtract(_totale);
    }

    private void ricalcolaTotali() {
        totaleErogato = BigDecimal.ZERO;
        totaleAssegnato = BigDecimal.ZERO;
        totaleRimanenza = BigDecimal.ZERO;
        filteredOopp.forEach(op -> {
            totaleErogato = totaleErogato.add(erogato(op));
            totaleAssegnato = totaleAssegnato.add(op.getImportoAssegnato());
            totaleRimanenza = totaleRimanenza.add(rimanenza(op));
        });
    }
    
    public BigDecimal getRapporto() {
        if(totaleAssegnato.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return totaleErogato.divide(totaleAssegnato,  3, RoundingMode.CEILING);
    }
    
    public String nuova() {
        return redirect("op");
    }
    
    public String modifica() {
        return viewParam(redirect("op"), "id", selezionata.getId());
    }
    
    public void elimina() {
        os.elimina(selezionata);
        initialize();        
    }
    
    public void ricarica() {
        initialize();
    }
}
