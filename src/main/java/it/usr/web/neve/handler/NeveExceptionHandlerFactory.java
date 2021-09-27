/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.neve.handler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author riccardo.iovenitti
 */
public class NeveExceptionHandlerFactory extends ExceptionHandlerFactory {
    private final ExceptionHandlerFactory parent;

    public NeveExceptionHandlerFactory(ExceptionHandlerFactory wrapped) {
        this.parent = wrapped;
    }
        
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new NeveExceptionHandler(parent.getExceptionHandler());
    }    
}
