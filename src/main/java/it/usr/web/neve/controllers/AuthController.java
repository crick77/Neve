/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.services.AuthService;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class AuthController implements Serializable {
    @Inject
    private AuthService as;
    private String username;
    private String password;
    private String message;
    
    public String doLogin() {
        Utente u = as.authenticate(username, password);
        if(u!=null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", u);
            return "/secure/istruttorie?faces-redirect=true";
        }
        else {
            message = "Wrong credentials";
            return null;
        }
    }
    
    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }        

    public String getMessage() {
        return message;
    }        
}
