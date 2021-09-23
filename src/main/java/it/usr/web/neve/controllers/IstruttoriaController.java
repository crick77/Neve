/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Allegato;
import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.services.IstruttoriaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class IstruttoriaController extends BaseController {
    private final static String STORAGE_PATH = "c:/dev/uploads/";
    @Inject
    IstruttoriaService is;
    private Istruttoria istruttoria;
    private Integer idIstruttoria;
    private UploadedFile documento;
    private UploadedFiles allegati;
    private List<Statolavori> statilavoro;    
    private List<Esito> esiti;
    private List<Comune> comuni;
    private List<String> filesToBeRemoved;

    public String initialize() {
        filesToBeRemoved = new ArrayList<>();
        
        if (idIstruttoria != null) {
            istruttoria = is.getIstruttoria(idIstruttoria);
            Utente utente = getUtente();
            if(!(istruttoria.getProprietario().getUsername().equals(utente.getUsername()) || utente.getAdmin()) && !utente.getAbilitato()) {
                return redirect("unauth");
            }
        } else {
            istruttoria = new Istruttoria();
            istruttoria.setComune(new Comune());
            istruttoria.setStato(new Statolavori());
            istruttoria.setEsito(new Esito());
            istruttoria.setAllegatoList(new ArrayList<>());
        }
        
        statilavoro = is.getStatiLavoro();
        esiti = is.getEsiti();
        comuni = is.getComuni(IstruttoriaService.SOLO_ABILITATI);     
        
        return SAME_VIEW;
    }

    public Istruttoria getIstruttoria() {
        return istruttoria;
    }

    public void setIstruttoria(Istruttoria istruttoria) {
        this.istruttoria = istruttoria;
    }

    public Integer getIdIstruttoria() {
        return idIstruttoria;
    }

    public void setIdIstruttoria(Integer idIstruttoria) {
        this.idIstruttoria = idIstruttoria;
    }

    public UploadedFile getDocumento() {
        return documento;
    }

    public void setDocumento(UploadedFile documento) {
        this.documento = documento;
    }

    public UploadedFiles getAllegati() {
        return allegati;
    }

    public void setAllegati(UploadedFiles allegati) {
        this.allegati = allegati;
    }
    
    public List<Statolavori> getStatiLavoro() {
        return statilavoro;
    }

    public List<Esito> getEsiti() {
        return esiti;
    }

    public List<Comune> getComuni() {
        return comuni;
    }
            
    public String salva() {
        if (!is.isPraticaValida(istruttoria.getIdpratica(), istruttoria.getId())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", "Id pratica utilizzato.");
            FacesContext.getCurrentInstance().addMessage("pratica", message);
            return SAME_VIEW;
        }
        
        // Modifica, controlla documento
        if(istruttoria.getId()==null) {                            
            if (documento == null || documento.getFileName() == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Documento obbligatorio");
                FacesContext.getCurrentInstance().addMessage("documento", message);
                return SAME_VIEW;
            }
            
            if(salvaDocumentoAllegati()) {           
                istruttoria.setComune(is.getComune(istruttoria.getComune().getComune()));
                istruttoria.setEsito(is.getEsito(istruttoria.getEsito().getEsito()));
                istruttoria.setStato(is.getStatoLavori(istruttoria.getStato().getStato()));
                BigDecimal _totale = new BigDecimal(0);
                _totale = _totale.add(istruttoria.getImportolavori());
                _totale = _totale.add(istruttoria.getIvalavori());
                _totale = _totale.add(istruttoria.getSpesetecniche());
                _totale = _totale.add(istruttoria.getStperizia());
                _totale = _totale.add(istruttoria.getIvastperizia());
                istruttoria.setTotale(_totale);
                Utente u = (Utente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
                istruttoria.setProprietario(u); 
                
                is.save(istruttoria);
            }            
        }
        else {
            // modifica
            
            // rimosso documento e nessuno aggiunto?
            if(istruttoria.getDocumento()==null && documento.getFileName()==null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Documento obbligatorio");
                FacesContext.getCurrentInstance().addMessage("documento", message);
                return SAME_VIEW;
            }
            
            if(salvaDocumentoAllegati()) {
                istruttoria.setComune(is.getComune(istruttoria.getComune().getComune()));
                istruttoria.setEsito(is.getEsito(istruttoria.getEsito().getEsito()));
                istruttoria.setStato(is.getStatoLavori(istruttoria.getStato().getStato()));
                BigDecimal _totale = new BigDecimal(0);
                _totale = _totale.add(istruttoria.getImportolavori());
                _totale = _totale.add(istruttoria.getIvalavori());
                _totale = _totale.add(istruttoria.getSpesetecniche());
                _totale = _totale.add(istruttoria.getStperizia());
                _totale = _totale.add(istruttoria.getIvastperizia());
                istruttoria.setTotale(_totale);
                
                is.save(istruttoria);
                
                rimuoviFileEliminati();
            }
        }
       
        return redirect("istruttorie");
    }

    private boolean salvaDocumentoAllegati() {
        try {
            if(documento.getFileName()!=null) {
                Files.createDirectories(Paths.get(STORAGE_PATH + istruttoria.getIdpratica() + "/"));
                Files.write(Paths.get(STORAGE_PATH + istruttoria.getIdpratica() + "/" + documento.getFileName()), documento.getContent());
                documento.delete();
                istruttoria.setDocumento(documento.getFileName());
            }

            if (allegati.getSize() > 0) {
                Files.createDirectories(Paths.get(STORAGE_PATH + istruttoria.getIdpratica() + "/att/"));

                for (UploadedFile all : allegati.getFiles()) {
                    Files.write(Paths.get(STORAGE_PATH + istruttoria.getIdpratica() + "/att/" + all.getFileName()), all.getContent());
                    all.delete();

                    Allegato a = new Allegato();
                    a.setIstruttoria(istruttoria);
                    a.setAllegato(all.getFileName());
                    istruttoria.getAllegatoList().add(a);
                }
            }                                                          
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IstruttorieController.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    public void validaIdPratica(AjaxBehaviorEvent event) {
        if (!is.isPraticaValida(istruttoria.getIdpratica(), istruttoria.getId())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", "Id pratica utilizzato.");
            FacesContext.getCurrentInstance().addMessage("pratica", message);

            UIInput pratica = (UIInput) event.getComponent();
            pratica.setValid(false);
        }
    }
    
    public void rimuoviAllegato(Allegato allegato) {
        istruttoria.getAllegatoList().remove(allegato);
        filesToBeRemoved.add("att/"+allegato.getAllegato());
    }
    
    public void rimuoviDocumento() {
        filesToBeRemoved.add(istruttoria.getDocumento());
        istruttoria.setDocumento(null);
    }
    
    public String annulla() {
        return redirect("istruttorie");
    }
    
    private void rimuoviFileEliminati() {
        filesToBeRemoved.forEach(f -> {
            try {
                Files.deleteIfExists(Paths.get(STORAGE_PATH + istruttoria.getIdpratica()+ "/" + f));
            }
            catch(IOException ex) {
                Logger.getLogger(IstruttorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
