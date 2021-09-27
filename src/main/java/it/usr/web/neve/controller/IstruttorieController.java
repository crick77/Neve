/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import it.usr.web.neve.domain.Comune;
import it.usr.web.neve.domain.Esito;
import it.usr.web.neve.domain.Istruttoria;
import it.usr.web.neve.domain.Statolavori;
import it.usr.web.neve.service.IstruttoriaService;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

/**
 * 
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class IstruttorieController extends BaseController {
    @Inject
    IstruttoriaService is;
    private List<Istruttoria> istruttorie;
    private List<Istruttoria> filteredIstruttorie; 
    private List<Statolavori> statilavoro;    
    private List<Esito> esiti;
    private List<Comune> comuni;
    private BigDecimal totale;
    private PDFOptions pdfOpt;

    public void initialize() {
        istruttorie = is.getIstruttorie();
        filteredIstruttorie = istruttorie;
        statilavoro = is.getStatiLavoro();
        esiti = is.getEsiti();
        comuni = is.getComuni(IstruttoriaService.ABILITATI_E_DISABILITATI);
                
        ricalcolaTotale();
        
        pdfOpt = new PDFOptions();
        pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
        pdfOpt.setCellFontSize("8");
    }

    public List<Istruttoria> getIstruttorie() {
        return istruttorie;
    }
   
    public BigDecimal getTotale() {
        return totale;
    }
              
    public List<Istruttoria> getFilteredIstruttorie() {
        return filteredIstruttorie;
    }

    public void setFilteredIstruttorie(List<Istruttoria> filteredIstruttorie) {
        this.filteredIstruttorie = filteredIstruttorie;
          
        ricalcolaTotale();
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

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }
                 
    public String editPratica(int idPratica) {
        return redirect(viewParam("istruttoria", "id", idPratica));        
    }     
    
    public void elimina(Istruttoria istruttoria) {
        is.elminia(istruttoria);
        istruttorie = is.getIstruttorie();
        filteredIstruttorie = istruttorie;
    }
    
    private void ricalcolaTotale() {
        totale = new BigDecimal(0);
        filteredIstruttorie.forEach(i -> {
            totale = totale.add(i.getTotale());
        });
    }
    
    public String getOggi() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }
    
    public void postProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document)document;        
	pdf.add(new Paragraph(filteredIstruttorie.size()+" pratiche per un totale di "+decimalFormat(totale, "#,##0.00")+" €."));
    }
}
 