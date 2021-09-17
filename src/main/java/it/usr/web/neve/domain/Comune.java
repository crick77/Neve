/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@Table(name = "comune")
@NamedQueries({
    @NamedQuery(name = "Comune.findAll", query = "SELECT c FROM Comune c"),
    @NamedQuery(name = "Comune.findByComune", query = "SELECT c FROM Comune c WHERE c.comune = :comune"),
    @NamedQuery(name = "Comune.findByAbilitato", query = "SELECT c FROM Comune c WHERE c.abilitato = :abilitato")})
public class Comune implements Serializable {    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String comune;
    @Basic(optional = false)
    @NotNull
    private boolean abilitato;

    public Comune() {
    }
    
    public Comune(String comune) {
        this.comune = comune;
    }

    public Comune(String comune, boolean abilitato) {
        this.comune = comune;
        this.abilitato = abilitato;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comune != null ? comune.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comune)) {
            return false;
        }
        Comune other = (Comune) object;
        return !((this.comune == null && other.comune != null) || (this.comune != null && !this.comune.equals(other.comune)));
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Comune[ comune=" + comune + " ]";
    }        
}
