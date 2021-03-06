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
    @NamedQuery(name = "Utente.findAll", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "Utente.findByUsername", query = "SELECT u FROM Utente u WHERE u.username = :username"),
    @NamedQuery(name = "Utente.findByAdmin", query = "SELECT u FROM Utente u WHERE u.admin = :admin"),
    @NamedQuery(name = "Utente.findByAbilitato", query = "SELECT u FROM Utente u WHERE u.abilitato = :abilitato"),
    @NamedQuery(name = "Utente.findByAbilitatoSorted", query = "SELECT u FROM Utente u WHERE u.abilitato = :abilitato ORDER BY u.username")})
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String username;
    @Basic(optional = false)
    @NotNull
    private boolean admin;
    @Basic(optional = false)
    @NotNull
    private boolean abilitato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proprietario")
    private List<Istruttoria> istruttoriaList;

    public Utente() {
    }

    public Utente(String username) {
        this.username = username;
    }

    public Utente(String username, boolean admin, boolean abilitato) {
        this.username = username;
        this.admin = admin;
        this.abilitato = abilitato;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Utente[ username=" + username + " ]";
    }
    
}
