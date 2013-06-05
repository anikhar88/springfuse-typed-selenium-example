/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/converter/JsfConverter.e.vm.java
 */
package fr.vendredi.web.converter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import fr.vendredi.domain.SavedSearch;
import fr.vendredi.repository.SavedSearchRepository;
import fr.vendredi.web.converter.GenericJsfConverter;

/**
 * JSF converter for {@link SavedSearch}.
 * @see GenericJsfConverter
 */
@Named
@Singleton
public class SavedSearchJsfConverter extends GenericJsfConverter<SavedSearch, Integer> {
    @Inject
    public SavedSearchJsfConverter(SavedSearchRepository savedSearchRepository) {
        super(savedSearchRepository, SavedSearch.class, Integer.class);
    }
}