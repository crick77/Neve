/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Esito.findAll", query = "SELECT e FROM Esito e"),
    @NamedQuery(name = "Esito.findByEsito", query = "SELECT e FROM Esito e WHERE e.esito = :esito"),
    @NamedQuery(name = "Esito.findByAbilitato", query = "SELECT e FROM Esito e WHERE e.abilitato = :abilitato")})
public class Esito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String esito;
    @Basic(optional = false)
    @NotNull
    private boolean abilitato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "esito")
    private List<Istruttoria> istruttoriaList;

    public Esito() {
    }

    public Esito(String esito) {
        this.esito = esito;
    }

    public Esito(String esito, boolean abilitato) {
        this.esito = esito;
        this.abilitato = abilitato;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    @XmlTransient
    public List<Istruttoria> getIstruttoriaList() {
        return istruttoriaList;
    }

    public void setIstruttoriaList(List<Istruttoria> istruttoriaList) {
        this.istruttoriaList = istruttoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esito != null ? esito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Esito)) {
            return false;
        }
        Esito other = (Esito) object;
        if ((this.esito == null && other.esito != null) || (this.esito != null && !this.esito.equals(other.esito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Esito[ esito=" + esito + " ]";
    }
    
}
