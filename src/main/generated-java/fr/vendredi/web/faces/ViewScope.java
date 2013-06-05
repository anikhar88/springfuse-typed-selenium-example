/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/faces/ViewScope.p.vm.java
 */
package fr.vendredi.web.faces;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ViewScope implements Scope {
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> viewMap = getViewMap();

        if (viewMap == null) {
            return objectFactory.getObject();
        } else if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);
            return object;
        }
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> viewMap = getViewMap();
        if (viewMap == null) {
            return null;
        }
        return viewMap.remove(name);
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    private Map<String, Object> getViewMap() {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        if (currentInstance != null && currentInstance.getViewRoot() != null) {
            return currentInstance.getViewRoot().getViewMap();
        }
        return null;
    }

}