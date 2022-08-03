/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Oopp;
import it.usr.web.neve.domain.Stato;
import it.usr.web.neve.service.OOPPService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named(value = "OPController")
@ViewScoped
public class OPController extends BaseController {
    @Inject
    OOPPService os;
    Oopp op;
    Integer id;
    List<String> enti;
    List<String> attuatori;
    List<Stato> stati;
    boolean conflitto;
    
    public void initialize() {   
        if(id!=null) {
            op = os.getOp(id);
        }
        else {
            op = new Oopp();
            op.setPriorita(5);
            op.setImportoAssegnato(BigDecimal.ZERO);
        }
        
        enti = os.getEnti();
        attuatori = os.getSoggettiAttuatori();
        stati = os.getStati();
        conflitto = false;
    }

    public Oopp getOp() {
        return op;
    }

    public void setOp(Oopp op) {
        this.op = op;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Stato> getStati() {
        return stati;
    }

    public boolean isConflitto() {
        return conflitto;
    }
                 
    public List<String> suggerisciEnte(String query) {
        final String ql = query.toLowerCase();                
        return enti.stream().filter(e -> e.toLowerCase().contains(ql)).collect(Collectors.toList());
    }
    
    public List<String> suggerisciAttuatore(String query) {
        final String ql = query.toLowerCase();                
        return attuatori.stream().filter(e -> e.toLowerCase().contains(ql)).collect(Collectors.toList());
    }
    
    public String salva() {
        os.salva(op);        
        
        return annulla();
    }
    
    public String annulla() {
        return redirect("oopp");
    }
    
    //<p:ajax event="blur" process="@this" listener="#{OPController.controllaIdPratica()}" update="messages,ok"/>
    public boolean controllaIdPratica() { 
        conflitto = false;
        Oopp _o = os.findByIdPratica(op.getIdPratica());
        // nuova pratica, verifica che non vi sia già un idpratica presente
        if(id==null) {
            if(_o!=null && _o.getIdPratica().equalsIgnoreCase(op.getIdPratica())) {
                addMessage("idpratica", FacesMessage.SEVERITY_WARN, "Pratica duplicata!", "Esiste già una pratica con questo id.");            
                conflitto = true;                
            }
        }
        else { // pratica in modifica, non deve corrispondere nemmeno il record
            if(_o!=null && _o.getIdPratica().equalsIgnoreCase(op.getIdPratica()) && !_o.getId().equals(op.getId())) {
                addMessage("idpratica", FacesMessage.SEVERITY_WARN, "Pratica duplicata!", "Esiste già una pratica con questo id.");            
                conflitto = true;                
            }
        }
                 
        return !conflitto;
    }
    
    //<p:ajax event="blur" process="@this" listener="#{OPController.controllaCup()}" update="messages,ok"/>
    public boolean controllaCup() {
        conflitto = false;        
        Oopp _o = os.findByCUP(op.getCup());
        if(_o!=null && _o.getCup().equalsIgnoreCase(op.getCup())) {
            addMessage("cup", FacesMessage.SEVERITY_WARN, "CUP duplicato!", "Esiste già una pratica con questo CUP.");            
            conflitto = true;
        }
                
        return !conflitto;
    }
}
