/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author riccardo.iovenitti
 */
public class Lavorazioni implements Serializable {
    private String username;
    private long numLavorazioni;
    private BigDecimal importo;
    private double percentuale;

    public Lavorazioni() {
    }

    public Lavorazioni(long numLavorazioni, BigDecimal importo) {
        this.numLavorazioni = numLavorazioni;
        this.importo = (importo!=null) ? importo : new BigDecimal(0);
    }
              
    public Lavorazioni(String username, long numLavorazioni, BigDecimal importo) {
        this.username = username;
        this.numLavorazioni = numLavorazioni;
        this.importo = (importo!=null) ? importo : new BigDecimal(0);
        this.percentuale = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getNumLavorazioni() {
        return numLavorazioni;
    }

    public void setNumLavorazioni(long numLavorazioni) {
        this.numLavorazioni = numLavorazioni;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = (importo!=null) ? importo : new BigDecimal(0);
    }
        
    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }     
}
