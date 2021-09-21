/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Allegato.findAll", query = "SELECT a FROM Allegato a"),
    @NamedQuery(name = "Allegato.findById", query = "SELECT a FROM Allegato a WHERE a.id = :id"),
    @NamedQuery(name = "Allegato.findByAllegato", query = "SELECT a FROM Allegato a WHERE a.allegato = :allegato")})
public class Allegato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String allegato;
    @JoinColumns({@JoinColumn(name = "idistruttoria", referencedColumnName = "id")})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Istruttoria istruttoria;

    public Allegato() {
    }

    public Allegato(Integer id) {
        this.id = id;
    }

    public Allegato(Integer id, String allegato) {
        this.id = id;
        this.allegato = allegato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAllegato() {
        return allegato;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public Istruttoria getIstruttoria() {
        return istruttoria;
    }

    public void setIstruttoria(Istruttoria istruttoria) {
        this.istruttoria = istruttoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Allegato)) {
            return false;
        }
        Allegato other = (Allegato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Allegato[ id=" + id + " ]";
    }
    
}
