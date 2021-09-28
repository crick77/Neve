/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.handler;

import it.usr.web.neve.producer.NeveLogger;
import java.util.Arrays;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
public class NeveExceptionHandler extends FullAjaxExceptionHandler {
    private final ExceptionHandler wrapper;
    @Inject
    @NeveLogger
    Logger logger;
    
    public NeveExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
        this.wrapper = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapper;
    }
        
    @Override
    protected void logException(FacesContext context, Throwable exception, String location, String message, Object... parameters) {
        if (exception instanceof ViewExpiredException) {
            // With exception==null, no trace will be logged.
            super.logException(context, null, location, message, parameters);
        }
        else {
            logger.error("Errore '{}' pagina '{}' eccezione '{}' parametri '{}'.", new Object[]{message, location, exception, Arrays.toString(parameters)});
        }        
    }        
}
 