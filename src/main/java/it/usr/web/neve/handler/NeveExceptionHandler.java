/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.handler;

import it.usr.web.neve.producer.NeveLogger;
import java.util.Arrays;
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
        logger.error("Errore ''{0}'' pagina ''{1}'' eccezione ''{2}'' parametri ''{3}''.", new Object[]{message, location, exception, Arrays.toString(parameters)});
    }        

    @Override
    protected void logException(FacesContext context, Throwable exception, String location, LogReason reason) {
        logger.error("Errore pagina ''{0}'' eccezione ''{1}'' motivo ''{2}''.", new Object[]{location, exception, reason.getMessage()});
    }
    
    
}
 