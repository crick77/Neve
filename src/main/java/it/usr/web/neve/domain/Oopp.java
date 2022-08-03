/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Oopp.findAll", query = "SELECT o FROM Oopp o"),
    @NamedQuery(name = "Oopp.findById", query = "SELECT o FROM Oopp o WHERE o.id = :id"),
    @NamedQuery(name = "Oopp.findByIdPratica", query = "SELECT o FROM Oopp o WHERE o.idPratica = :idPratica"),
    @NamedQuery(name = "Oopp.findByEnte", query = "SELECT o FROM Oopp o WHERE o.ente = :ente"),
    @NamedQuery(name = "Oopp.findByPriorita", query = "SELECT o FROM Oopp o WHERE o.priorita = :priorita"),
    @NamedQuery(name = "Oopp.findByLocalizzazione", query = "SELECT o FROM Oopp o WHERE o.localizzazione = :localizzazione"),
    @NamedQuery(name = "Oopp.findByOggetto", query = "SELECT o FROM Oopp o WHERE o.oggetto = :oggetto"),
    @NamedQuery(name = "Oopp.findByAttuatore", query = "SELECT o FROM Oopp o WHERE o.attuatore = :attuatore"),
    @NamedQuery(name = "Oopp.findByContoTu", query = "SELECT o FROM Oopp o WHERE o.contoTu = :contoTu"),
    @NamedQuery(name = "Oopp.findByImportoAssegnato", query = "SELECT o FROM Oopp o WHERE o.importoAssegnato = :importoAssegnato"),
    @NamedQuery(name = "Oopp.findByCup", query = "SELECT o FROM Oopp o WHERE o.cup = :cup"),
    @NamedQuery(name = "Oopp.findByPanticImporto", query = "SELECT o FROM Oopp o WHERE o.panticImporto = :panticImporto"),
    @NamedQuery(name = "Oopp.findByPanticNDecreto", query = "SELECT o FROM Oopp o WHERE o.panticNDecreto = :panticNDecreto"),
    @NamedQuery(name = "Oopp.findByPanticDataDecreto", query = "SELECT o FROM Oopp o WHERE o.panticDataDecreto = :panticDataDecreto"),
    @NamedQuery(name = "Oopp.findByPanticNMandato", query = "SELECT o FROM Oopp o WHERE o.panticNMandato = :panticNMandato"),
    @NamedQuery(name = "Oopp.findByPanticDataMandato", query = "SELECT o FROM Oopp o WHERE o.panticDataMandato = :panticDataMandato"),
    @NamedQuery(name = "Oopp.findBySanticImporto", query = "SELECT o FROM Oopp o WHERE o.santicImporto = :santicImporto"),
    @NamedQuery(name = "Oopp.findBySanticNDecreto", query = "SELECT o FROM Oopp o WHERE o.santicNDecreto = :santicNDecreto"),
    @NamedQuery(name = "Oopp.findBySanticDataDecreto", query = "SELECT o FROM Oopp o WHERE o.santicDataDecreto = :santicDataDecreto"),
    @NamedQuery(name = "Oopp.findBySanticNMandato", query = "SELECT o FROM Oopp o WHERE o.santicNMandato = :santicNMandato"),
    @NamedQuery(name = "Oopp.findBySanticDataMandato", query = "SELECT o FROM Oopp o WHERE o.santicDataMandato = :santicDataMandato"),
    @NamedQuery(name = "Oopp.findByPsalImporto", query = "SELECT o FROM Oopp o WHERE o.psalImporto = :psalImporto"),
    @NamedQuery(name = "Oopp.findByPsalNDecreto", query = "SELECT o FROM Oopp o WHERE o.psalNDecreto = :psalNDecreto"),
    @NamedQuery(name = "Oopp.findByPsalDataDecreto", query = "SELECT o FROM Oopp o WHERE o.psalDataDecreto = :psalDataDecreto"),
    @NamedQuery(name = "Oopp.findByPsalNMandato", query = "SELECT o FROM Oopp o WHERE o.psalNMandato = :psalNMandato"),
    @NamedQuery(name = "Oopp.findByPsalDataMandato", query = "SELECT o FROM Oopp o WHERE o.psalDataMandato = :psalDataMandato"),
    @NamedQuery(name = "Oopp.findBySsalImporto", query = "SELECT o FROM Oopp o WHERE o.ssalImporto = :ssalImporto"),
    @NamedQuery(name = "Oopp.findBySsalNDecreto", query = "SELECT o FROM Oopp o WHERE o.ssalNDecreto = :ssalNDecreto"),
    @NamedQuery(name = "Oopp.findBySsalDataDecreto", query = "SELECT o FROM Oopp o WHERE o.ssalDataDecreto = :ssalDataDecreto"),
    @NamedQuery(name = "Oopp.findBySsalNMandato", query = "SELECT o FROM Oopp o WHERE o.ssalNMandato = :ssalNMandato"),
    @NamedQuery(name = "Oopp.findBySsalDataMandato", query = "SELECT o FROM Oopp o WHERE o.ssalDataMandato = :ssalDataMandato"),
    @NamedQuery(name = "Oopp.findBySaldoImporto", query = "SELECT o FROM Oopp o WHERE o.saldoImporto = :saldoImporto"),
    @NamedQuery(name = "Oopp.findBySaldoNDecreto", query = "SELECT o FROM Oopp o WHERE o.saldoNDecreto = :saldoNDecreto"),
    @NamedQuery(name = "Oopp.findBySaldoDataDecreto", query = "SELECT o FROM Oopp o WHERE o.saldoDataDecreto = :saldoDataDecreto"),
    @NamedQuery(name = "Oopp.findBySaldoNMandato", query = "SELECT o FROM Oopp o WHERE o.saldoNMandato = :saldoNMandato"),
    @NamedQuery(name = "Oopp.findBySaldoDataMandato", query = "SELECT o FROM Oopp o WHERE o.saldoDataMandato = :saldoDataMandato"),
    @NamedQuery(name = "Oopp.findByEconomie", query = "SELECT o FROM Oopp o WHERE o.economie = :economie"),
    @NamedQuery(name = "Oopp.findByVersion", query = "SELECT o FROM Oopp o WHERE o.version = :version")})
public class Oopp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "id_pratica")
    private String idPratica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String ente;
    @NotNull
    @Size(min = 1, max = 2)
    private String prov;
    @Basic(optional = false)
    @NotNull
    private int priorita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String localizzazione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String oggetto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String attuatore;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "conto_tu")
    private String contoTu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "importo_assegnato")
    private BigDecimal importoAssegnato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    private String cup;
    @Column(name = "pantic_importo")
    private BigDecimal panticImporto;
    @Column(name = "pantic_n_decreto")
    private Integer panticNDecreto;
    @Column(name = "pantic_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date panticDataDecreto;
    @Column(name = "pantic_n_mandato")
    private Integer panticNMandato;
    @Column(name = "pantic_data_mandato")
    @Temporal(TemporalType.DATE)
    private Date panticDataMandato;
    @Column(name = "santic_importo")
    private BigDecimal santicImporto;
    @Column(name = "santic_n_decreto")
    private Integer santicNDecreto;
    @Column(name = "santic_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date santicDataDecreto;
    @Column(name = "santic_n_mandato")
    private Integer santicNMandato;
    @Column(name = "santic_data_mandato")
    @Temporal(TemporalType.DATE)
    private Date santicDataMandato;
    @Column(name = "psal_importo")
    private BigDecimal psalImporto;
    @Column(name = "psal_n_decreto")
    private Integer psalNDecreto;
    @Column(name = "psal_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date psalDataDecreto;
    @Column(name = "psal_n_mandato")
    private Integer psalNMandato;
    @Column(name = "psal_data_mandato")
    @Temporal(TemporalType.DATE)
    private Date psalDataMandato;
    @Column(name = "ssal_importo")
    private BigDecimal ssalImporto;
    @Column(name = "ssal_n_decreto")
    private Integer ssalNDecreto;
    @Column(name = "ssal_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date ssalDataDecreto;
    @Column(name = "ssal_n_mandato")
    private Integer ssalNMandato;
    @Column(name = "ssal_data_mandato")
    @Temporal(TemporalType.DATE)
    private Date ssalDataMandato;
    @Column(name = "saldo_importo")
    private BigDecimal saldoImporto;
    @Column(name = "saldo_n_decreto")
    private Integer saldoNDecreto;
    @Column(name = "saldo_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date saldoDataDecreto;
    @Column(name = "saldo_n_mandato")
    private Integer saldoNMandato;
    @Column(name = "saldo_data_mandato")
    @Temporal(TemporalType.DATE)
    private Date saldoDataMandato;
    private BigDecimal economie;
    @Basic(optional = false)
    @NotNull
    private long version;
    @JoinColumn(name = "id_stato", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stato idStato;

    public Oopp() {
    }

    public Oopp(Integer id) {
        this.id = id;
    }

    public Oopp(Integer id, String idPratica, String ente, String prov, int priorita, String localizzazione, String oggetto, String attuatore, String contoTu, BigDecimal importoAssegnato, String cup, BigDecimal panticImporto, long version) {
        this.id = id;
        this.idPratica = idPratica;
        this.ente = ente;
        this.prov = prov;
        this.priorita = priorita;
        this.localizzazione = localizzazione;
        this.oggetto = oggetto;
        this.attuatore = attuatore;
        this.contoTu = contoTu;
        this.importoAssegnato = importoAssegnato;
        this.cup = cup;
        this.panticImporto = panticImporto;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }
        
    public int getPriorita() {
        return priorita;
    }

    public void setPriorita(int priorita) {
        this.priorita = priorita;
    }

    public String getLocalizzazione() {
        return localizzazione;
    }

    public void setLocalizzazione(String localizzazione) {
        this.localizzazione = localizzazione;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getAttuatore() {
        return attuatore;
    }

    public void setAttuatore(String attuatore) {
        this.attuatore = attuatore;
    }

    public String getContoTu() {
        return contoTu;
    }

    public void setContoTu(String contoTu) {
        this.contoTu = contoTu;
    }

    public BigDecimal getImportoAssegnato() {
        return importoAssegnato;
    }

    public void setImportoAssegnato(BigDecimal importoAssegnato) {
        this.importoAssegnato = importoAssegnato;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public BigDecimal getPanticImporto() {
        return panticImporto;
    }

    public void setPanticImporto(BigDecimal panticImporto) {
        this.panticImporto = panticImporto;
    }

    public Integer getPanticNDecreto() {
        return panticNDecreto;
    }

    public void setPanticNDecreto(Integer panticNDecreto) {
        this.panticNDecreto = panticNDecreto;
    }

    public Date getPanticDataDecreto() {
        return panticDataDecreto;
    }

    public void setPanticDataDecreto(Date panticDataDecreto) {
        this.panticDataDecreto = panticDataDecreto;
    }

    public Integer getPanticNMandato() {
        return panticNMandato;
    }

    public void setPanticNMandato(Integer panticNMandato) {
        this.panticNMandato = panticNMandato;
    }

    public Date getPanticDataMandato() {
        return panticDataMandato;
    }

    public void setPanticDataMandato(Date panticDataMandato) {
        this.panticDataMandato = panticDataMandato;
    }

    public BigDecimal getSanticImporto() {
        return santicImporto;
    }

    public void setSanticImporto(BigDecimal santicImporto) {
        this.santicImporto = santicImporto;
    }

    public Integer getSanticNDecreto() {
        return santicNDecreto;
    }

    public void setSanticNDecreto(Integer santicNDecreto) {
        this.santicNDecreto = santicNDecreto;
    }

    public Date getSanticDataDecreto() {
        return santicDataDecreto;
    }

    public void setSanticDataDecreto(Date santicDataDecreto) {
        this.santicDataDecreto = santicDataDecreto;
    }

    public Integer getSanticNMandato() {
        return santicNMandato;
    }

    public void setSanticNMandato(Integer santicNMandato) {
        this.santicNMandato = santicNMandato;
    }

    public Date getSanticDataMandato() {
        return santicDataMandato;
    }

    public void setSanticDataMandato(Date santicDataMandato) {
        this.santicDataMandato = santicDataMandato;
    }

    public BigDecimal getPsalImporto() {
        return psalImporto;
    }

    public void setPsalImporto(BigDecimal psalImporto) {
        this.psalImporto = psalImporto;
    }

    public Integer getPsalNDecreto() {
        return psalNDecreto;
    }

    public void setPsalNDecreto(Integer psalNDecreto) {
        this.psalNDecreto = psalNDecreto;
    }

    public Date getPsalDataDecreto() {
        return psalDataDecreto;
    }

    public void setPsalDataDecreto(Date psalDataDecreto) {
        this.psalDataDecreto = psalDataDecreto;
    }

    public Integer getPsalNMandato() {
        return psalNMandato;
    }

    public void setPsalNMandato(Integer psalNMandato) {
        this.psalNMandato = psalNMandato;
    }

    public Date getPsalDataMandato() {
        return psalDataMandato;
    }

    public void setPsalDataMandato(Date psalDataMandato) {
        this.psalDataMandato = psalDataMandato;
    }

    public BigDecimal getSsalImporto() {
        return ssalImporto;
    }

    public void setSsalImporto(BigDecimal ssalImporto) {
        this.ssalImporto = ssalImporto;
    }

    public Integer getSsalNDecreto() {
        return ssalNDecreto;
    }

    public void setSsalNDecreto(Integer ssalNDecreto) {
        this.ssalNDecreto = ssalNDecreto;
    }

    public Date getSsalDataDecreto() {
        return ssalDataDecreto;
    }

    public void setSsalDataDecreto(Date ssalDataDecreto) {
        this.ssalDataDecreto = ssalDataDecreto;
    }

    public Integer getSsalNMandato() {
        return ssalNMandato;
    }

    public void setSsalNMandato(Integer ssalNMandato) {
        this.ssalNMandato = ssalNMandato;
    }

    public Date getSsalDataMandato() {
        return ssalDataMandato;
    }

    public void setSsalDataMandato(Date ssalDataMandato) {
        this.ssalDataMandato = ssalDataMandato;
    }

    public BigDecimal getSaldoImporto() {
        return saldoImporto;
    }

    public void setSaldoImporto(BigDecimal saldoImporto) {
        this.saldoImporto = saldoImporto;
    }

    public Integer getSaldoNDecreto() {
        return saldoNDecreto;
    }

    public void setSaldoNDecreto(Integer saldoNDecreto) {
        this.saldoNDecreto = saldoNDecreto;
    }

    public Date getSaldoDataDecreto() {
        return saldoDataDecreto;
    }

    public void setSaldoDataDecreto(Date saldoDataDecreto) {
        this.saldoDataDecreto = saldoDataDecreto;
    }

    public Integer getSaldoNMandato() {
        return saldoNMandato;
    }

    public void setSaldoNMandato(Integer saldoNMandato) {
        this.saldoNMandato = saldoNMandato;
    }

    public Date getSaldoDataMandato() {
        return saldoDataMandato;
    }

    public void setSaldoDataMandato(Date saldoDataMandato) {
        this.saldoDataMandato = saldoDataMandato;
    }

    public BigDecimal getEconomie() {
        return economie;
    }

    public void setEconomie(BigDecimal economie) {
        this.economie = economie;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Stato getIdStato() {
        return idStato;
    }

    public void setIdStato(Stato idStato) {
        this.idStato = idStato;
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
        if (!(object instanceof Oopp)) {
            return false;
        }
        Oopp other = (Oopp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.neve.domain.Oopp[ id=" + id + " ]";
    }
    
}
