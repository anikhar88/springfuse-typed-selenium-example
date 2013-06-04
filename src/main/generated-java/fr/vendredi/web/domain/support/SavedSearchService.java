/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/SavedSearchService.p.vm.java
 */
package fr.vendredi.web.domain.support;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.transaction.annotation.Transactional;
import fr.vendredi.context.UserContext;
import fr.vendredi.dao.support.SearchParameters;
import fr.vendredi.domain.Account;
import fr.vendredi.domain.SavedSearch;
import fr.vendredi.repository.AccountRepository;
import fr.vendredi.repository.SavedSearchRepository;
import fr.vendredi.web.domain.support.GenericSearchForm;
import fr.vendredi.web.util.MessageUtil;

/**
 * Store/Load Search Form Content to db.
 */
@Named
@Singleton
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SavedSearchService {
    @Inject
    private SavedSearchRepository savedSearchRepository;
    @Inject
    private MessageUtil messageUtil;
    @Inject
    private AccountRepository accountRepository;

    public void reload(GenericSearchForm searchForm) {
        if (StringUtils.isNotBlank(searchForm.getSearchFormName())) {
            searchForm.resetWithOther(load(searchForm));
        }
    }

    public void onChange(SelectEvent selectEvent) {
        GenericSearchForm searchForm = (GenericSearchForm) selectEvent.getComponent().getAttributes().get("searchForm");
        searchForm.setSearchFormName((String) selectEvent.getObject()); // set the new value
        reload(searchForm);
    }

    @Transactional
    public void save(GenericSearchForm searchFrom) {
        SavedSearch savedSearch = savedSearchRepository.findUniqueOrNone(example(searchFrom));
        if (savedSearch == null) {
            savedSearch = example(searchFrom);
            savedSearch.setAccount(accountRepository.get(savedSearch.getAccount()));
        }

        savedSearch.setFormContent(toByteArray(searchFrom));
        savedSearchRepository.save(savedSearch);
        messageUtil.info("saved_search_saved", savedSearch.getName());
    }

    protected <F extends GenericSearchForm, T> F load(F searchFrom) {
        SavedSearch savedSearch = savedSearchRepository.findUnique(example(searchFrom));
        messageUtil.info("saved_search_loaded", savedSearch.getName());
        return fromByteArray(savedSearch.getFormContent());
    }

    private SavedSearch example(GenericSearchForm searchFrom) {
        SavedSearch savedSearch = new SavedSearch();
        savedSearch.setName(searchFrom.getSearchFormName());
        savedSearch.setFormClassname(searchFrom.getClass().getSimpleName());
        Account currentAccount = new Account();
        currentAccount.setId(String.valueOf(UserContext.getId()));
        savedSearch.setAccount(currentAccount);
        return savedSearch;
    }

    public <F extends GenericSearchForm> Finder finderFor(F searchFrom) {
        // we use a Finder in order to have the required List<String> find(String) method
        return new Finder(savedSearchRepository, searchFrom);
    }

    public class Finder {
        private SavedSearchRepository savedSearchRepository;
        private GenericSearchForm searchFrom;

        public Finder(SavedSearchRepository savedSearchRepository, GenericSearchForm searchFrom) {
            this.savedSearchRepository = savedSearchRepository;
            this.searchFrom = searchFrom;
        }

        public List<String> find(String name) {
            List<String> results = newArrayList();
            for (SavedSearch savedSearch : savedSearchRepository.find(example(searchFrom), new SearchParameters().anywhere())) {
                results.add(savedSearch.getName());
            }
            return results;
        }
    }

    private byte[] toByteArray(GenericSearchForm form) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(form);
            oos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw propagate(e);
        } finally {
            closeQuietly(oos);
            closeQuietly(baos);
        }
    }

    private <F> F fromByteArray(byte[] bytes) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (F) ois.readObject();
        } catch (Exception e) {
            throw propagate(e);
        } finally {
            closeQuietly(ois);
        }
    }
}
