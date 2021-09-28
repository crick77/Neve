/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.producer.NeveLogger;
import it.usr.web.neve.service.AuthService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class AuthController extends BaseController {
    @Inject
    private AuthService as;
    @Inject
    @NeveLogger
    Logger logger;
    private String username;
    private String password;
    private String message;
    
    public String doLogin() {
        Utente u = as.authenticate(username, password);
        if(u!=null) {
            putIntoSession(u);            
            logger.debug("L'utente [{}] ha effettuato l'accesso.", username);
            return redirect("/secure/istruttorie");
        }
        else {
            logger.debug("L'utente [{}] non esiste o la password è errata.", username);
            message = "Username e/o password errati.";
            return SAME_VIEW;
        }
    }
    
    public String doLogout() {
        invalidateSession();
        logger.debug("L'utente [{}] ha effettuato la disconnessione.", username);
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
