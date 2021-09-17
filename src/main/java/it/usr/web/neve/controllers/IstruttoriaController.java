/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Allegato;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.services.IstruttoriaService;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.faces.view.facelets.Metadata;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class IstruttoriaController implements Serializable {
    @Inject
    IstruttoriaService is;
    private List<Istruttoria> istruttorie;
    private List<Istruttoria> filteredIstruttorie;
    private List<Statolavori> statilavoro;
    private Istruttoria istruttoria;
    private List<Esito> esiti;
    private BigDecimal totale;
    private UploadedFile documento;    
    private UploadedFiles allegati;

    public void initialize() {
        istruttorie = is.getIstruttorie();
        filteredIstruttorie = istruttorie;
        statilavoro = is.getStatiLavoro();
        esiti = is.getEsiti();
        istruttoria = new Istruttoria();
        istruttoria.setStato(new Statolavori());
        istruttoria.setEsito(new Esito());
        istruttoria.setAllegatoList(new ArrayList<>());

        totale = new BigDecimal(0);
        istruttorie.forEach(i -> {
            totale = totale.add(i.getTotale());
        });
    }

    public List<Istruttoria> getIstruttorie() {
        return istruttorie;
    }

    public List<Statolavori> getStatiLavoro() {
        return statilavoro;
    }

    public List<Esito> getEsiti() {
        return esiti;
    }

    public BigDecimal getTotale() {
        return totale;
    }
    
    public String format(Object data, String format) {
        return new DecimalFormat(format).format(data);
    }
      
    public List<Istruttoria> getFilteredIstruttorie() {
        return filteredIstruttorie;
    }

    public void setFilteredIstruttorie(List<Istruttoria> filteredIstruttorie) {
        this.filteredIstruttorie = filteredIstruttorie;
        
        totale = new BigDecimal(0);
        filteredIstruttorie.forEach(i -> {
            totale = totale.add(i.getTotale());
        });
    }        

    public Istruttoria getIstruttoria() {
        return istruttoria;
    }

    public void setIstruttoria(Istruttoria istruttoria) {
        this.istruttoria = istruttoria;
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
             
    public String salva() {
        if(documento==null || documento.getFileName()==null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Documento obbligatorio");
            FacesContext.getCurrentInstance().addMessage("documento", message);
            return null;
        }
                
        if(!is.isPraticaValida(istruttoria.getIdpratica())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", "Id pratica utilizzato.");
            FacesContext.getCurrentInstance().addMessage("pratica", message);
            return null;
        }
        
        try {
            Files.createDirectories(Paths.get("c:/dev/uploads/"+istruttoria.getIdpratica()+"/"));
            Files.write(Paths.get("c:/dev/uploads/"+istruttoria.getIdpratica()+"/"+documento.getFileName()), documento.getContent());
            documento.delete();
            
            if(allegati.getSize()>0) {                
                Files.createDirectories(Paths.get("c:/dev/uploads/"+istruttoria.getIdpratica()+"/att/")); 
            
                for(UploadedFile all : allegati.getFiles()) {                
                    Files.write(Paths.get("c:/dev/uploads/"+istruttoria.getIdpratica()+"/att/"+all.getFileName()), all.getContent());

                    all.delete();
                    
                    Allegato a = new Allegato();
                    a.setIstruttoria(istruttoria);
                    a.setAllegato(all.getFileName());
                    istruttoria.getAllegatoList().add(a);
                }
            }
            
            istruttoria.setEsito(is.getEsito(istruttoria.getEsito().getEsito()));
            istruttoria.setStato(is.getStatoLavori(istruttoria.getStato().getStato()));
            BigDecimal _totale = new BigDecimal(0);
            _totale = _totale.add(istruttoria.getImportolavori());
            _totale = _totale.add(istruttoria.getIvalavori());
            _totale = _totale.add(istruttoria.getSpesetecniche());
            _totale = _totale.add(istruttoria.getStperizia());
            _totale = _totale.add(istruttoria.getIvastperizia());
            istruttoria.setTotale(_totale);
            Utente u = (Utente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");            
            istruttoria.setProprietario(u);
            istruttoria.setVersion(1);
            istruttoria.setDocumento(documento.getFileName());
            
            is.save(istruttoria);
        } catch (IOException ex) {
            Logger.getLogger(IstruttoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "istruttorie?faces-redirect=true";
    }   
    
    public void validaIdPratica(AjaxBehaviorEvent event) {
        if(!is.isPraticaValida(istruttoria.getIdpratica())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attenzione", "Id pratica utilizzato.");
            FacesContext.getCurrentInstance().addMessage("pratica", message);
            
            UIInput pratica = (UIInput)event.getComponent();
            pratica.setValid(false);            
        }
    }      
    
    public void loadAllegati(Istruttoria i) {
        Istruttoria _i = is.getIstruttorieAllegati(i);
        i.setAllegatoList(_i.getAllegatoList());
    }
}
 