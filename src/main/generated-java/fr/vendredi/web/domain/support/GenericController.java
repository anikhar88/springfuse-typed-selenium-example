/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/support/GenericController.p.vm.java
 */
package fr.vendredi.web.domain.support;

import static fr.vendredi.web.conversation.ConversationHolder.getCurrentConversation;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.WordUtils;

import fr.vendredi.dao.support.JpaUniqueUtil;
import fr.vendredi.dao.support.SearchParameters;
import fr.vendredi.domain.Identifiable;
import fr.vendredi.printer.TypeAwarePrinter;
import fr.vendredi.repository.support.GenericRepository;
import fr.vendredi.web.util.MessageUtil;
import fr.vendredi.util.ResourcesUtil;
import fr.vendredi.web.conversation.ConversationCallBack;
import fr.vendredi.web.conversation.ConversationContext;
import fr.vendredi.web.conversation.ConversationManager;
import fr.vendredi.web.permission.support.GenericPermission;
import fr.vendredi.context.UserContext;

/**
 * Base controller for JPA entities providing helper methods to:
 * <ul>
 *  <li>start conversations</li>
 *  <li>create conversation context</li>
 *  <li>support autoComplete component</li>
 *  <li>perform actions</li>
 *  <li>support excel export</li>
 * </ul>
 */
public abstract class GenericController<E extends Identifiable<PK>, PK extends Serializable> {
    private static final String PERMISSION_DENIED = "/error/accessdenied";
    private String selectUri;
    private String editUri;

    @Inject
    protected ConversationManager conversationManager;
    @Inject
    protected JpaUniqueUtil jpaUniqueUtil;
    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TypeAwarePrinter printer;
    protected GenericRepository<E, PK> repository;
    protected GenericPermission<E> permission;

    public GenericController(GenericRepository<E, PK> repository, GenericPermission<E> permission, String selectUri, String editUri) {
        this.repository = checkNotNull(repository);
        this.permission = checkNotNull(permission);
        this.selectUri = checkNotNull(selectUri);
        this.editUri = checkNotNull(editUri);
    }

    public GenericRepository<E, PK> getRepository() {
        return repository;
    }

    public GenericPermission<E> getPermission() {
        return permission;
    }

    public MessageUtil getMessageUtil() {
        return messageUtil;
    }

    public String getPermissionDenied() {
        return PERMISSION_DENIED;
    }

    public String getSelectUri() {
        return selectUri;
    }

    public String getEditUri() {
        return editUri;
    }

    // ----------------------------------------
    // START CONVERSATION PROGRAMATICALY
    // ----------------------------------------

    /**
     * Start a new {@link Conversation} that allows the user to search an existing entity.
     * This method can be invoked from an h:commandLink's action attribute.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginSearch() {
        if (!permission.canSearch()) {
            return getPermissionDenied();
        }
        return beginConversation(newSearchContext(getSearchLabelKey()));
    }

    /**
     * Start a new {@link Conversation} that allows the user to create a new entity.
     * This method can be invoked from an h:commandLink's action attribute.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginCreate() {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        return beginConversation(newEditContext(getCreateLabelKey(), repository.getNewWithDefaults()));
    }

    /**
     * Start a new {@link Conversation} using the passed ctx as the first conversation context.
     * @return the implicit first view for the newly created conversation.
     */
    public String beginConversation(ConversationContext<E> ctx) {
        return conversationManager.beginConversation(ctx).nextView();
    }

    // ----------------------------------------
    // AUTO COMPLETE SUPPORT  
    // ----------------------------------------

    /**
     * Auto-complete support. This method is used by primefaces autoComplete component.
     */
    public List<E> complete(String value) {
        return repository.find(completeSearchParameters(value));
    }

    public List<E> completeFullText(String value) {
        return repository.find(completeFullTextSearchParameters(value));
    }

    /**
     * A simple auto-complete that returns exactly the input. It is used in search forms with PropertySelector. 
     */
    public List<String> completeSame(String value) {
        return newArrayList(value);
    }

    public List<String> completePropertyFullText(String term) throws Exception {
        String property = parameter("property", String.class);
        Integer maxResults = parameter("maxResults", Integer.class);
        Set<String> ret = newHashSet(term);
        for (E object : repository.find(new SearchParameters().termOn(term, property).orderBy(property).maxResults(maxResults - 1))) {
            ret.add((String) PropertyUtils.getProperty(object, property));
        }
        return newArrayList(ret);
    }

    public List<String> completeProperty(String value) throws Exception {
        String property = parameter("property", String.class);
        Integer maxResults = parameter("maxResults", Integer.class);
        E template = repository.getNew();
        PropertyUtils.setProperty(template, property, value);
        Set<String> ret = newHashSet(value);
        for (E object : repository.find(template, new SearchParameters().anywhere().orderBy(property).maxResults(maxResults - 1))) {
            ret.add((String) PropertyUtils.getProperty(object, property));
        }
        return newArrayList(ret);
    }

    @SuppressWarnings("unchecked")
    private <T> T parameter(String propertyName, Class<T> expectedType) {
        return (T) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get(propertyName);
    }

    protected SearchParameters completeSearchParameters(String value) {
        return searchParameters().searchPattern(value);
    }

    protected SearchParameters searchParameters() {
        return defaultOrder(new SearchParameters().anywhere());
    }

    protected SearchParameters completeFullTextSearchParameters(String value) {
        return searchParameters().term(value);
    }

    protected SearchParameters defaultOrder(SearchParameters searchParameters) {
        return searchParameters;
    }

    /**
     * Decision helper used when handling ajax autoComplete event and regular page postback.
     */
    public boolean shouldReplace(E currentEntity, E selectedEntity) {
        if (currentEntity == selectedEntity) {
            return false;
        }

        if (currentEntity != null && selectedEntity != null && currentEntity.isIdSet() && selectedEntity.isIdSet()) {
            if (selectedEntity.getId().equals(currentEntity.getId())) {
                Comparable<Object> currentVersion = repository.getVersion(currentEntity);
                if (currentVersion == null) {
                    // assume no version at all is available
                    // let's stick with current entity.
                    return false;
                }
                Comparable<Object> selectedVersion = repository.getVersion(selectedEntity);
                if (currentVersion.compareTo(selectedVersion) == 0) {
                    // currentEntity could have been edited and not yet saved, we keep it.
                    return false;
                } else {
                    // we could have an optimistic locking exception at save time
                    // TODO: what should we do here?
                    return false;
                }
            }
        }
        return true;
    }

    // ----------------------------------------
    // CREATE IMPLICIT EDIT VIEW
    // ----------------------------------------

    /**
     * Helper to create a new {@link ConversationContext} to view the passed entity and set it as the current conversation's next context.  
     * The vars <code>sub</code> <code>readonly</code> are set to true.
     * The permission {@link GenericPermission#canView()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to view.
     * @return the implicit view to access this context.
     */
    public String editSubReadOnlyView(String labelKey, E e) {
        if (!permission.canView(e)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e).sub().readonly();
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Helper to create a new {@link ConversationContext} to edit the passed entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * The permission {@link GenericPermission#canEdit()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to edit.
     * @return the implicit view to access this context.
     */
    public String editSubView(String labelKey, E e, ConversationCallBack<E> editCallBack) {
        if (!permission.canEdit(e)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e, editCallBack).sub();
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Helper to create a new {@link ConversationContext} to create a new entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @return the implicit view to access this context.
     */
    public String createSubView(String labelKey, ConversationCallBack<E> createCallBack) {
        return createSubView(labelKey, repository.getNewWithDefaults(), createCallBack);
    }

    /**
     * Helper to create a new {@link ConversationContext} to edit the passed new entity and set it as the current conversation's next context.  
     * The var <code>sub</code> is set to true.
     * The permission {@link GenericPermission#canCreate()} is checked.
     * 
     * @param labelKey label key for breadCrumb and conversation menu.
     * @param e the entity to edit.
     * @return the implicit view to access this context.
     */
    public String createSubView(String labelKey, E e, ConversationCallBack<E> createCallBack) {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newEditContext(labelKey, e, createCallBack).sub();
        return getCurrentConversation().nextContext(ctx).view();
    }

    // ----------------------------------------
    // CREATE IMPLICIT SELECT VIEW
    // ----------------------------------------

    public String selectSubView(String labelKey, ConversationCallBack<E> selectCallBack) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newSearchContext(labelKey, selectCallBack).sub();
        return getCurrentConversation().nextContext(ctx).view();
    }

    public String multiSelectSubView(String labelKey, ConversationCallBack<E> selectCallBack) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = newSearchContext(labelKey, selectCallBack).sub();
        ctx.setVar("multiCheckboxSelection", true);
        return getCurrentConversation().nextContext(ctx).view();
    }

    // ----------------------------------------
    // CREATE EDIT CONVERSATION CONTEXT
    // ----------------------------------------

    /**
     * Helper to construct a new {@link ConversationContext} to edit an entity.
     * @param e the entity to edit.
     */
    public ConversationContext<E> newEditContext(E e) {
        return new ConversationContext<E>().entity(e).isNewEntity(!e.isIdSet()).viewUri(getEditUri());
    }

    public ConversationContext<E> newEditContext(String labelKey, E e) {
        return newEditContext(e).labelKey(labelKey);
    }

    public ConversationContext<E> newEditContext(String labelKey, E e, ConversationCallBack<E> callBack) {
        return newEditContext(labelKey, e).callBack(callBack);
    }

    // ----------------------------------------
    // CREATE SEARCH CONVERSATION CONTEXT
    // ----------------------------------------

    /**
     * Helper to construct a new {@link ConversationContext} for search/selection.
     */
    public ConversationContext<E> newSearchContext() {
        return new ConversationContext<E>(getSelectUri());
    }

    public ConversationContext<E> newSearchContext(String labelKey) {
        return newSearchContext().labelKey(labelKey);
    }

    public ConversationContext<E> newSearchContext(String labelKey, ConversationCallBack<E> callBack) {
        return newSearchContext(labelKey).callBack(callBack);
    }

    // ----------------------------------------
    // ACTIONS INVOKED FORM THE VIEW
    // ----------------------------------------

    public ConversationContext<E> getSelectedContext(E selected) {
        return newEditContext(getEditUri(), selected);
    }

    /**
     * Action to create a new entity.
     */
    public String create() {
        if (!permission.canCreate()) {
            return getPermissionDenied();
        }
        E newEntity = repository.getNewWithDefaults();
        ConversationContext<E> ctx = getSelectedContext(newEntity).labelKey(getCreateLabelKey());
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Support for {@link GenericLazyDataModel.#edit()} and {@link GenericLazyDataModel#onRowSelect(org.primefaces.event.SelectEvent)} methods 
     */
    public String edit(E entity) {
        if (!permission.canEdit(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).labelKey(getEditLabelKey(), printer.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Support for the {@link GenericLazyDataModel.#view()} method
     */
    public String view(E entity) {
        if (!permission.canView(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).sub().readonly().labelKey(getViewLabelKey(), printer.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    /**
     * Return the print friendly view for the passed entity. I can be invoked directly from the view.
     */
    public String print(E entity) {
        if (!permission.canView(entity)) {
            return getPermissionDenied();
        }
        ConversationContext<E> ctx = getSelectedContext(entity).readonly().print().labelKey(getViewLabelKey(), printer.print(entity));
        return getCurrentConversation().nextContext(ctx).view();
    }

    protected String select(E entity) {
        if (!permission.canSelect()) {
            return getPermissionDenied();
        }
        return getCurrentConversation() //
                .<ConversationContext<E>> getCurrentContext() //
                .getCallBack() //
                .selected(entity);
    }

    protected String getSearchLabelKey() {
        return getLabelName() + "_search";
    }

    protected String getCreateLabelKey() {
        return getLabelName() + "_create";
    }

    protected String getEditLabelKey() {
        return getLabelName() + "_edit";
    }

    protected String getViewLabelKey() {
        return getLabelName() + "_view";
    }

    protected String getLabelName() {
        return WordUtils.uncapitalize(getEntityName());
    }

    private String getEntityName() {
        return repository.getType().getSimpleName();
    }

    // ----------------------------------------
    // EXCEL RELATED
    // ----------------------------------------

    @Inject
    private ExcelExportService excelExportService;

    @Inject
    private ExcelExportSupport excelExportSupport;

    @Inject
    private ResourcesUtil resourcesUtil;

    public void onExcel(SearchParameters searchParameters) throws IOException {
        Map<String, Object> model = newHashMap();
        model.put("msg", resourcesUtil);
        model.put("search_date", excelExportSupport.dateToString(new Date()));
        model.put("search_by", UserContext.getUsername());
        model.put("searchParameters", searchParameters);
        model.put("printer", printer);
        excelObjects(model, searchParameters);
        excelExportService.export("excel/" + getEntityName().toLowerCase() + ".xlsx", model, getExcelOutputname());
    }

    protected String getExcelOutputname() {
        return getEntityName() + "-" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new Date()) + ".xlsx";
    }

    protected void excelObjects(Map<String, Object> model, SearchParameters searchParameters) {
        excelExportSupport.convertSearchParametersToMap(model, searchParameters);

        int count = repository.findCount(searchParameters);
        model.put("search_nb_results", count);
        if (count > 65535) {
            searchParameters.maxResults(65535);
        }
        model.put(WordUtils.uncapitalize(getEntityName()), repository.find(searchParameters));
    }
}