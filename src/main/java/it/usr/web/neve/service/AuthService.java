/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.service;

import it.usr.web.neve.domain.Utente;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@Named
public class AuthService {
    public final static String SERVER_NAME = "aqadmc04"; //aqadmc05
    public final static String DOMAIN_NAME = "abruzzo.loc";
    @PersistenceContext
    EntityManager em;
    
    public Utente authenticate(String username, String password) {
        try {
            LdapContext ctx = ActiveDirectory.getConnection(username, password, DOMAIN_NAME, SERVER_NAME);
            ctx.close();
            
            Utente u = em.find(Utente.class, username);
            return u.getAbilitato() ? u : null;
        }
        catch(NamingException ne) {
            return null;
        }
    }
}
