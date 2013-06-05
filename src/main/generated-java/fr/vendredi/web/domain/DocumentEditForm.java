/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-jsf2-spring-conversation:src/main/java/domain/EditForm.e.vm.java
 */
package fr.vendredi.web.domain;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import fr.vendredi.domain.Account;
import fr.vendredi.domain.Document;
import fr.vendredi.repository.DocumentRepository;
import fr.vendredi.web.domain.AccountController;
import fr.vendredi.web.domain.DocumentController;
import fr.vendredi.web.domain.DocumentGraphLoader;
import fr.vendredi.web.domain.support.GenericEditForm;
import fr.vendredi.web.domain.support.GenericToOneAssociation;
import fr.vendredi.web.faces.ConversationContextScoped;

/**
 * View Helper/Controller to edit {@link Document}.
 */
@Named
@ConversationContextScoped
public class DocumentEditForm extends GenericEditForm<Document, String> {
    @Inject
    protected DocumentController documentController;
    @Inject
    protected AccountController accountController;
    protected GenericToOneAssociation<Account, String> owner;

    @Inject
    public DocumentEditForm(DocumentRepository documentRepository, DocumentGraphLoader documentGraphLoader) {
        super(documentRepository, documentGraphLoader);
    }

    /**
     * The entity to edit/view.
     */
    public Document getDocument() {
        return getEntity();
    }

    public String print() {
        return documentController.print(getDocument());
    }

    @PostConstruct
    void setupOwnersActions() {
        owner = new GenericToOneAssociation<Account, String>("document_owner", accountController) {
            @Override
            protected Account get() {
                return getDocument().getOwner();
            }

            @Override
            protected void set(Account account) {
                getDocument().setOwner(account);
            }

            @NotNull
            @Override
            public Account getSelected() {
                return super.getSelected();
            }
        };
    }

    public GenericToOneAssociation<Account, String> getOwner() {
        return owner;
    }
}