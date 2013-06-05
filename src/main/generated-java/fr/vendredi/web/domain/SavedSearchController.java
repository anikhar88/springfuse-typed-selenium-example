/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/Controller.e.vm.java
 */
package fr.vendredi.web.domain;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import fr.vendredi.domain.SavedSearch;
import fr.vendredi.repository.SavedSearchRepository;
import fr.vendredi.web.domain.support.GenericController;
import fr.vendredi.web.permission.SavedSearchPermission;

/**
 * Stateless controller for {@link SavedSearch} conversation start. 
 */
@Named
@Singleton
public class SavedSearchController extends GenericController<SavedSearch, Integer> {
    public static final String SAVEDSEARCH_EDIT_URI = "/domain/savedSearchEdit.faces";
    public static final String SAVEDSEARCH_SELECT_URI = "/domain/savedSearchSelect.faces";

    @Inject
    public SavedSearchController(SavedSearchRepository savedSearchRepository, SavedSearchPermission savedSearchPermission) {
        super(savedSearchRepository, savedSearchPermission, SAVEDSEARCH_SELECT_URI, SAVEDSEARCH_EDIT_URI);
    }

    public SavedSearchFileUpload getSavedSearchFileUpload(SavedSearch savedSearch) {
        return new SavedSearchFileUpload(savedSearch);
    }
}