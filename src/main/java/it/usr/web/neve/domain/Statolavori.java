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
    @NamedQuery(name = "Statolavori.findAll", query = "SELECT s FROM Statolavori s"),
    @NamedQuery(name = "Statolavori.findByStato", query = "SELECT s FROM Statolavori s WHERE s.stato = :stato"),
    @NamedQuery(name = "Statolavori.findByAbilitato", query = "SELECT s FROM Statolavori s WHERE s.abilitato = :abilitato")})
public class Statolavori implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String stato;
    @Basic(optional = false)
    @NotNull
    private boolean abilitato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stato")
    private List<Istruttoria> istruttoriaList;

    public Statolavori() {
    }

    public Statolavori(String stato) {
        this.stato = stato;
    }

    public Statolavori(String stato, boolean abilitato) {
        this.stato = stato;
        this.abilitato = abilitato;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
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
        hash += (stato != null ? stato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statolavori)) {
            return false;
        }
        Statolavori other = (Statolavori) object;
        if ((this.stato == null && other.stato != null) || (this.stato != null && !this.stato.equals(other.stato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Statolavori[ stato=" + stato + " ]";
    }
    
}
