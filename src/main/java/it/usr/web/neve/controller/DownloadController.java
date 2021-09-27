/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Allegato;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.service.IstruttoriaService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class DownloadController {
    @Inject
    IstruttoriaService is;
    StreamedContent file;
    Map<String, String> mime = new HashMap<>();
    @Resource(lookup="neve/uploadBasePath")
    String basePath;
    
    public DownloadController() {        
        mime.put("doc", "application/msword");
        mime.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mime.put("pdf", "application/pdf");
    }
    
    @PostConstruct
    public void setup() {
        if(basePath==null || basePath.trim().length()==0) throw new IllegalArgumentException("uploadBasePath not set.");
        basePath = basePath.trim();
        if(!basePath.endsWith("/")) basePath+="/";
    }
    
    public void prepare(Istruttoria i) {        
        try {    
            Path p = Paths.get(basePath+i.getIdpratica()+"/"+i.getDocumento());
            InputStream _is = Files.newInputStream(p);
            file = DefaultStreamedContent.builder()
                .name(i.getDocumento())
                .contentType(getMime(getFileExtension(p.toString())))
                .stream(() -> _is)
                .build();   
        }
        catch(IOException e) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Impossibile scaricare il documento.");
             FacesContext.getCurrentInstance().addMessage(null, message);
        }                         
    }
    
    public void prepare(Allegato a) {        
        try {    
            Path p = Paths.get(basePath+a.getIstruttoria().getIdpratica()+"/att/"+a.getAllegato());
            
            InputStream _is = Files.newInputStream(p);
            file = DefaultStreamedContent.builder()
                .name(a.getAllegato())
                .contentType(getMime(getFileExtension(p.toString())))
                .stream(() -> _is)
                .build();   
        }
        catch(IOException e) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Impossibile scaricare il documento.");
             FacesContext.getCurrentInstance().addMessage(null, message);
        }                         
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }       
    
    private String getMime(String extension) {
        String out = mime.get(String.valueOf(extension).toLowerCase());
        return (out!=null) ? out : "application/octet-stream";
    }
    
    private String getFileExtension(String fileName) {
        fileName = (fileName!=null) ? fileName : "";
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        return (ext!=null) ? ext.trim() : "";
    }
}
