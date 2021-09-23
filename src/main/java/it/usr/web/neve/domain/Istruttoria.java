/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
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
    @NamedQuery(name = "Istruttoria.findAll", query = "SELECT i FROM Istruttoria i"),
    @NamedQuery(name = "Istruttoria.findById", query = "SELECT i FROM Istruttoria i WHERE i.id = :id"),
    @NamedQuery(name = "Istruttoria.findByCognome", query = "SELECT i FROM Istruttoria i WHERE i.cognome = :cognome"),
    @NamedQuery(name = "Istruttoria.findByComune", query = "SELECT i FROM Istruttoria i WHERE i.comune = :comune"),
    @NamedQuery(name = "Istruttoria.findByEsito", query = "SELECT i FROM Istruttoria i WHERE i.esito = :esito"),
    @NamedQuery(name = "Istruttoria.findByStato", query = "SELECT i FROM Istruttoria i WHERE i.stato = :stato"),
    @NamedQuery(name = "Istruttoria.findByNome", query = "SELECT i FROM Istruttoria i WHERE i.nome = :nome"),
    @NamedQuery(name = "Istruttoria.findByIdpratica", query = "SELECT i FROM Istruttoria i WHERE i.idpratica = :idpratica"),
    @NamedQuery(name = "Istruttoria.findByIdpraticaOtherID", query = "SELECT i FROM Istruttoria i WHERE i.idpratica = :idpratica AND i.id <> :id"),
    @NamedQuery(name = "Istruttoria.findByOggettolavori", query = "SELECT i FROM Istruttoria i WHERE i.oggettolavori = :oggettolavori"),
    @NamedQuery(name = "Istruttoria.findByImportolavori", query = "SELECT i FROM Istruttoria i WHERE i.importolavori = :importolavori"),
    @NamedQuery(name = "Istruttoria.findByIvalavori", query = "SELECT i FROM Istruttoria i WHERE i.ivalavori = :ivalavori"),
    @NamedQuery(name = "Istruttoria.findBySpesetecniche", query = "SELECT i FROM Istruttoria i WHERE i.spesetecniche = :spesetecniche"),
    @NamedQuery(name = "Istruttoria.findByStperizia", query = "SELECT i FROM Istruttoria i WHERE i.stperizia = :stperizia"),
    @NamedQuery(name = "Istruttoria.findByIvastperizia", query = "SELECT i FROM Istruttoria i WHERE i.ivastperizia = :ivastperizia"),
    @NamedQuery(name = "Istruttoria.findByTotale", query = "SELECT i FROM Istruttoria i WHERE i.totale = :totale"),
    @NamedQuery(name = "Istruttoria.findByNote", query = "SELECT i FROM Istruttoria i WHERE i.note = :note"),
    @NamedQuery(name = "Istruttoria.findByDocumento", query = "SELECT i FROM Istruttoria i WHERE i.documento = :documento")})  
public class Istruttoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String cognome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String nome;
    @Basic(optional = false)
    @NotNull
    private int idpratica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String oggettolavori;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal importolavori;
    @Basic(optional = false)
    @NotNull
    private BigDecimal ivalavori;
    @Basic(optional = false)
    @NotNull
    private BigDecimal spesetecniche;
    @Basic(optional = false)
    @NotNull
    private BigDecimal stperizia;
    @Basic(optional = false)
    @NotNull
    private BigDecimal ivastperizia;
    @Basic(optional = false)
    @NotNull
    private BigDecimal totale;
    @Size(max = 2000)
    private String note;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Version
    private int version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "istruttoria", fetch = FetchType.EAGER)
    private List<Allegato> allegatoList;
    @JoinColumn(name = "comune", referencedColumnName = "comune")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Comune comune;
    @JoinColumn(name = "esito", referencedColumnName = "esito")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Esito esito;
    @JoinColumn(name = "stato", referencedColumnName = "stato")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Statolavori stato;
    @JoinColumn(name = "proprietario", referencedColumnName = "username")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Utente proprietario;

    public Istruttoria() {
    }

    public Istruttoria(Integer id) {
        this.id = id;
    }

    public Istruttoria(Integer id, String cognome, String nome, int idpratica, String oggettolavori, BigDecimal importolavori, BigDecimal ivalavori, BigDecimal spesetecniche, BigDecimal stperizia, BigDecimal ivastperizia, BigDecimal totale, String documento, int version) {
        this.id = id;
        this.cognome = cognome;
        this.nome = nome;
        this.idpratica = idpratica;
        this.oggettolavori = oggettolavori;
        this.importolavori = importolavori;
        this.ivalavori = ivalavori;
        this.spesetecniche = spesetecniche;
        this.stperizia = stperizia;
        this.ivastperizia = ivastperizia;
        this.totale = totale;
        this.documento = documento;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdpratica() {
        return idpratica;
    }

    public void setIdpratica(int idpratica) {
        this.idpratica = idpratica;
    }

    public String getOggettolavori() {
        return oggettolavori;
    }

    public void setOggettolavori(String oggettolavori) {
        this.oggettolavori = oggettolavori;
    }

    public BigDecimal getImportolavori() {
        return importolavori;
    }

    public void setImportolavori(BigDecimal importolavori) {
        this.importolavori = importolavori;
    }

    public BigDecimal getIvalavori() {
        return ivalavori;
    }

    public void setIvalavori(BigDecimal ivalavori) {
        this.ivalavori = ivalavori;
    }

    public BigDecimal getSpesetecniche() {
        return spesetecniche;
    }

    public void setSpesetecniche(BigDecimal spesetecniche) {
        this.spesetecniche = spesetecniche;
    }

    public BigDecimal getStperizia() {
        return stperizia;
    }

    public void setStperizia(BigDecimal stperizia) {
        this.stperizia = stperizia;
    }

    public BigDecimal getIvastperizia() {
        return ivastperizia;
    }

    public void setIvastperizia(BigDecimal ivastperizia) {
        this.ivastperizia = ivastperizia;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @XmlTransient
    public List<Allegato> getAllegatoList() {
        return allegatoList;
    }

    public void setAllegatoList(List<Allegato> allegatoList) {
        this.allegatoList = allegatoList;
    }

    public Comune getComune() {
        return comune;
    }

    public void setComune(Comune comune) {
        this.comune = comune;
    }

    public Esito getEsito() {
        return esito;
    }

    public void setEsito(Esito esito) {
        this.esito = esito;
    }

    public Statolavori getStato() {
        return stato;
    }

    public void setStato(Statolavori stato) {
        this.stato = stato;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
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
        if (!(object instanceof Istruttoria)) {
            return false;
        }
        Istruttoria other = (Istruttoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Istruttoria[ id=" + id + " ]";
    }
    
}
