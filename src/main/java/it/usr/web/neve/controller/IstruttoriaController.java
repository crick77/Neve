/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Allegato;
import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.producer.NeveLogger;
import it.usr.web.neve.service.IstruttoriaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class IstruttoriaController extends BaseController {
    @Resource(lookup = "neve/uploadBasePath")
    private String basePath;
    @Inject
    IstruttoriaService is;
    @Inject
    @NeveLogger
    Logger logger;
    private Istruttoria istruttoria;
    private Integer idIstruttoria;
    private UploadedFile documento;
    private UploadedFiles allegati;
    private List<Statolavori> statilavoro;
    private List<Esito> esiti;
    private List<Comune> comuni;
    private List<String> filesToBeRemoved;
    private String oldIdPratica;

    @PostConstruct
    public void setup() {
        if (basePath == null || basePath.trim().length() == 0) {
            throw new IllegalArgumentException("uploadBasePath not set.");
        }
        basePath = basePath.trim();
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
                
        logger.info("Cartella di upload/download impostata su [{}].", basePath);
    }

    public String initialize() {
        filesToBeRemoved = new ArrayList<>();

        if (idIstruttoria != null) {
            istruttoria = is.getIstruttoria(idIstruttoria);
            Utente utente = getUtente();
            if (!(istruttoria.getProprietario().getUsername().equals(utente.getUsername()) || utente.getAdmin()) && !utente.getAbilitato()) {
                return redirect("unauth");
            }
            oldIdPratica = istruttoria.getIdpratica();
        } else {
            istruttoria = new Istruttoria();
            istruttoria.setComune(new Comune());
            istruttoria.setStato(new Statolavori());
            istruttoria.setEsito(new Esito());
            istruttoria.setAllegatoList(new ArrayList<>());
            
            oldIdPratica = null;
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

        // Nuova
        if (istruttoria.getId() == null) {
            //if (documento == null || documento.getFileName() == null) {
            //    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Documento obbligatorio");
            //    FacesContext.getCurrentInstance().addMessage("documento", message);
            //    return SAME_VIEW;
            //}

            if (salvaDocumentoAllegati()) {
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
                Utente u = getUtente();
                istruttoria.setProprietario(u);

                is.salva(istruttoria);
            }
        } else {  
            // modifica
            // Istruttoria di proprietà o amministratore
            Utente u = getUtente();
            if(!u.getUsername().equalsIgnoreCase(istruttoria.getProprietario().getUsername()) && !u.getAdmin()) {
                return "unauth";
            }
        
            // rimosso documento e nessuno aggiunto?
            //if (istruttoria.getDocumento() == null && documento.getFileName() == null) {
            //    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Documento obbligatorio");
            //    FacesContext.getCurrentInstance().addMessage("documento", message);
            //    return SAME_VIEW;
            //} 
            
            if (salvaDocumentoAllegati()) {
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

                try {
                    is.salva(istruttoria);
                } catch (EJBException ee) {
                    OptimisticLockException e = (OptimisticLockException) unrollException(ee, OptimisticLockException.class);
                    if (e != null) {
                        logger.error("Impossibile aggiornare l'istruttoria id {} per una eccezione di lock ottimistico.", istruttoria.getId());
                        return "ole";
                    }

                    logger.error("Impossibile aggiornare l'istruttoria id {} per una eccezione {}", istruttoria.getId(), ee);
                    throw ee;
                }

                rimuoviFileEliminati();
            }
        }

        return redirect("istruttorie");
    }

    private boolean salvaDocumentoAllegati() {
        try {
            // L'id istruttoria è cambiato? Rinomina la cartella prima di fare il resto
            if(!istruttoria.getIdpratica().equalsIgnoreCase(oldIdPratica) && oldIdPratica!=null) {
                Path pSrc = Paths.get(basePath + sanitizePath(oldIdPratica) + "/");
                Path pDest = Paths.get(basePath + sanitizePath(istruttoria.getIdpratica()) + "/");
                Files.move(pSrc, pDest, StandardCopyOption.ATOMIC_MOVE);
            }
            
            if (documento.getFileName() != null) {
                Files.createDirectories(Paths.get(basePath + sanitizePath(istruttoria.getIdpratica()) + "/"));
                Files.write(Paths.get(basePath + sanitizePath(istruttoria.getIdpratica()) + "/" + documento.getFileName()), documento.getContent());
                documento.delete();
                istruttoria.setDocumento(documento.getFileName());
            }

            if (allegati.getSize() > 0) {
                Files.createDirectories(Paths.get(basePath + sanitizePath(istruttoria.getIdpratica()) + "/att/"));

                for (UploadedFile all : allegati.getFiles()) {
                    Files.write(Paths.get(basePath + sanitizePath(istruttoria.getIdpratica()) + "/att/" + all.getFileName()), all.getContent());
                    all.delete();

                    Allegato a = new Allegato();
                    a.setIstruttoria(istruttoria);
                    a.setAllegato(all.getFileName());
                    istruttoria.getAllegatoList().add(a);
                }
            }

            return true;
        } catch (IOException ex) {
            logger.error("Impossibile caricare il documento/allegati alla pratica {} a causa di ", istruttoria.getId(), ex);

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
        filesToBeRemoved.add("att/" + allegato.getAllegato());
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
                Files.deleteIfExists(Paths.get(basePath + istruttoria.getIdpratica() + "/" + f));
            } catch (IOException ex) {
                logger.error("Impossibile rimuovere il file [{}] dell'istruttoria con id {} a causa di {}", f, istruttoria.getId(), ex);
            }
        });
    }
}
