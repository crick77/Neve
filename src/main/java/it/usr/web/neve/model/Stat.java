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
public class Stat implements Serializable {
    public String voce;
    public int numeroPratiche;
    public BigDecimal totale;

    public Stat(String voce, int numeroPratiche, BigDecimal totale) {
        this.voce = voce;
        this.numeroPratiche = numeroPratiche;
        this.totale = totale;
    }

    public String getVoce() {
        return voce;
    }

    public int getNumeroPratiche() {
        return numeroPratiche;
    }

    public BigDecimal getTotale() {
        return totale;
    }        
}
