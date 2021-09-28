/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.filter;

import it.usr.web.neve.producer.NeveLogger;
import java.io.IOException;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omnifaces.filter.FacesExceptionFilter;
import org.omnifaces.util.Servlets;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@WebFilter(filterName="logFilter")
public class LogFilter extends FacesExceptionFilter  {
    @Inject
    @NeveLogger
    private Logger logger;
    
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, HttpSession session, FilterChain chain) throws ServletException, IOException {
        try {
            super.doFilter(request, response, session, chain);
        }
        catch (Exception e) {
            String uuid = UUID.randomUUID().toString();
            String ip = Servlets.getRemoteAddr(request);
            request.setAttribute("UUID", uuid);
            logger.error("Errore codice [{}] per l'ip [{}]: ", uuid, ip, e);
            
            request.getRequestDispatcher("WEB-INF/errorpages/500.xhtml").forward(request, response);
        }
    }    
}
