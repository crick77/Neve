/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.converter;

import it.usr.web.neve.domain.Stato;
import it.usr.web.neve.service.OOPPService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author riccardo.iovenitti
 */
@FacesConverter(value = "statoConverter")
public class StatoConverter implements Converter<Stato> {
    @Inject
    OOPPService os;

    @Override
    public Stato getAsObject(FacesContext fc, UIComponent uic, String string) {
        return os.getStato(Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Stato s) {        
        return s.getId().toString();        
    }    
}
