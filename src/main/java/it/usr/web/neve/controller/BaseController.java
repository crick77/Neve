/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.controller;

import it.usr.web.neve.domain.Utente;
import it.usr.web.neve.producer.NeveLogger;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
public abstract class BaseController implements Serializable {
    public final static long serialVersionUID = 1L;
    public final static String SAME_VIEW = null;
    public final static String CURRENCY_PATTERN = "#,##0.00 €";
    @Inject
    @NeveLogger
    Logger baseLogger;

    public Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public Utente getUtente() {
        return (Utente) getSessionMap().get(getSessionClassName(Utente.class));
    }

    public String redirect(String path) {
        if (path == null) {
            return null;
        }

        if (!path.contains("?")) {
            return path + "?faces-redirect=true";
        }

        if (!path.contains("faces-redirect=true")) {
            return path + "&faces-redirect=true";
        }

        return path;
    }

    public String viewParam(String path, String paramName, Object paramValue, Object... params) {
        if (path == null) {
            return path;
        }
        if (paramName == null) {
            throw new IllegalArgumentException("paramName null.");
        }

        path += !path.contains("?") ? "?" : "&";
        if (!path.contains("includeViewParams")) {
            path += "includeViewParams=true&";
        }

        path += paramName + "=" + String.valueOf(paramValue);

        if (params.length > 0) {
            if (params.length % 2 != 0) {
                throw new IllegalArgumentException("optional params are odd");
            }
            for (int i = 0; i < params.length; i = +2) {
                path += "&" + params[i] + "=" + params[i + 1];
            }
        }

        return path;
    }

    public String decimalFormat(Object data, String format) {
        try {
            return new DecimalFormat(format).format(data);
        }
        catch(RuntimeException e) {
            String dtype = (data!=null) ? data.getClass().toString() : "null-reference";
            baseLogger.error("Errore conversione [{}] tipo [[{}] in formato [{}]. Eccezione [{}]", data, dtype, format, e);
            throw e;
        }
    }

    public static String getSessionClassName(Class c) {
        if (c == null) {
            throw new IllegalArgumentException("class object cannot be null");
        }

        String cName = c.getSimpleName();
        return cName.substring(0, 1).toLowerCase() + cName.substring(1);
    }

    public void putIntoSession(Object o) {
        if (o == null) {
            return;
        }

        getSessionMap().put(getSessionClassName(o.getClass()), o);
    }

    public void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public Throwable unrollException(Throwable exception,
            Class<? extends Throwable> expected) {

        while (exception != null && exception != exception.getCause()) {
            if (expected.isInstance(exception)) {
                return exception;
            }
            exception = exception.getCause();
        }
        return null;
    }
    
    public String sanitizePath(String s) {
        return (s!=null) ? s.replaceAll("[\\/|\\\\|\\?|\\<|\\>|\\*|\\:|\\|]+", "_") : null;
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        addMessage(null, severity, summary, detail);
    }

    public void addMessage(String id, FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(id, new FacesMessage(severity, summary, detail));
    }
}
