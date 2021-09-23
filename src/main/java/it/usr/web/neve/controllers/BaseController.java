/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Utente;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author riccardo.iovenitti
 */
public abstract class BaseController implements Serializable { 
    public final static long serialVersionUID = 1L;
    public final static String SAME_VIEW = null;
    
    public Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
    
    public Utente getUtente() {               
        return (Utente)getSessionMap().get(getSessionClassName(Utente.class));
    }
    
    public String redirect(String path) {
        if(path==null) return null;
        
        if(!path.contains("?")) {
            return path+"?faces-redirect=true";
        }
        
        if(!path.contains("faces-redirect=true")) {
            return path+"&faces-redirect=true";
        }
        
        return path;
    }
    
    public String viewParam(String path, String paramName, Object paramValue, Object... params) {
        if(path==null) return path;
        if(paramName==null) throw new IllegalArgumentException("paramName null.");
        
        path+=!path.contains("?") ? "?" : "&";
        if(!path.contains("includeViewParams")) path+="includeViewParams=true&";
        
        path+=paramName+"="+String.valueOf(paramValue);
        
        if(params.length>0) {
            if(params.length%2!=0) throw new IllegalArgumentException("optional params are odd");            
            for(int i=0;i<params.length;i=+2) {
                path+="&"+params[i]+"="+params[i+1];
            }
        }
        
        return path;
    }
    
    public String decimalFormat(Object data, String format) {
        return new DecimalFormat(format).format(data);
    }
    
    public static String getSessionClassName(Class c) {
        if(c==null) throw new IllegalArgumentException("class object cannot be null");
         
        String cName = c.getSimpleName();
        return cName.substring(0, 1).toLowerCase()+cName.substring(1);
    }
    
    public void putIntoSession(Object o) {
        if(o==null) return;
        
        getSessionMap().put(getSessionClassName(o.getClass()), o);                
    }
    
    public void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
