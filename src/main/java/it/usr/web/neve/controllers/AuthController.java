/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controllers;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.services.AuthService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class AuthController extends BaseController {
    @Inject
    private AuthService as;
    private String username;
    private String password;
    private String message;
    
    public String doLogin() {
        Utente u = as.authenticate(username, password);
        if(u!=null) {
            putIntoSession(u);            
            return redirect("/secure/istruttorie");
        }
        else {
            message = "Wrong credentials";
            return SAME_VIEW;
        }
    }
    
    public String doLogout() {
        invalidateSession();
        return redirect("/index");
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
