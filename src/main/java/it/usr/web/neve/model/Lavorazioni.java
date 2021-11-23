/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.model;

import java.io.Serializable;

/**
 *
 * @author riccardo.iovenitti
 */
public class Lavorazioni implements Serializable {
    private String username;
    private long numLavorazioni;
    private double percentuale;

    public Lavorazioni() {
    }
        
    public Lavorazioni(String username, long numLavorazioni, double percentuale) {
        this.username = username;
        this.numLavorazioni = numLavorazioni;
        this.percentuale = percentuale;
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

    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }     
}
