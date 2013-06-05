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
import fr.vendredi.domain.Account;
import fr.vendredi.domain.Address;
import fr.vendredi.domain.Book;
import fr.vendredi.domain.Document;
import fr.vendredi.domain.Role;
import fr.vendredi.repository.AccountRepository;
import fr.vendredi.web.domain.AccountController;
import fr.vendredi.web.domain.AccountGraphLoader;
import fr.vendredi.web.domain.AddressController;
import fr.vendredi.web.domain.BookController;
import fr.vendredi.web.domain.DocumentController;
import fr.vendredi.web.domain.RoleController;
import fr.vendredi.web.domain.support.GenericEditForm;
import fr.vendredi.web.domain.support.GenericToManyAssociation;
import fr.vendredi.web.domain.support.GenericToOneAssociation;
import fr.vendredi.web.faces.ConversationContextScoped;
import fr.vendredi.web.util.TabBean;

/**
 * View Helper/Controller to edit {@link Account}.
 */
@Named
public class AccountEditForm extends GenericEditForm<Account, String> {
    @Inject
    protected AccountController accountController;
    @Inject
    protected AddressController addressController;
    protected GenericToOneAssociation<Address, Integer> homeAddress;
    @Inject
    protected BookController bookController;
    protected GenericToManyAssociation<Book, Integer> coolBooks;
    @Inject
    protected DocumentController documentController;
    protected GenericToManyAssociation<Document, String> edocs;
    @Inject
    protected RoleController roleController;
    protected GenericToManyAssociation<Role, Integer> securityRoles;
    protected TabBean tabBean = new TabBean();

    @Inject
    public AccountEditForm(AccountRepository accountRepository, AccountGraphLoader accountGraphLoader) {
        super(accountRepository, accountGraphLoader);
    }

    /**
     * View helper to store the x-to-many associations tab's index. 
     */
    @Override
    public TabBean getTabBean() {
        return tabBean;
    }

    /**
     * The entity to edit/view.
     */
    public Account getAccount() {
        return getEntity();
    }

    public String print() {
        return accountController.print(getAccount());
    }

    @PostConstruct
    void setupHomeAddressesActions() {
        homeAddress = new GenericToOneAssociation<Address, Integer>("account_homeAddress", addressController) {
            @Override
            protected Address get() {
                return getAccount().getHomeAddress();
            }

            @Override
            protected void set(Address address) {
                getAccount().setHomeAddress(address);
            }
        };
    }

    public GenericToOneAssociation<Address, Integer> getHomeAddress() {
        return homeAddress;
    }

    @PostConstruct
    void setupCoolBooksActions() {
        coolBooks = new GenericToManyAssociation<Book, Integer>(getAccount().getCoolBooks(), "account_coolBooks", bookController) {
            @Override
            protected void remove(Book book) {
                getAccount().removeBook(book);
            }

            @Override
            protected void add(Book book) {
                getAccount().addBook(book);
            }

            @Override
            protected void onCreate(Book book) {
                book.setOwner(getAccount()); // for display
            }
        };
    }

    public GenericToManyAssociation<Book, Integer> getCoolBooks() {
        return coolBooks;
    }

    @PostConstruct
    void setupEdocsActions() {
        edocs = new GenericToManyAssociation<Document, String>(getAccount().getEdocs(), "account_edocs", documentController) {
            @Override
            protected void remove(Document document) {
                getAccount().removeEdoc(document);
            }

            @Override
            protected void add(Document document) {
                getAccount().addEdoc(document);
            }

            @Override
            protected void onCreate(Document edoc) {
                edoc.setOwner(getAccount()); // for display
            }
        };
    }

    public GenericToManyAssociation<Document, String> getEdocs() {
        return edocs;
    }

    @PostConstruct
    void setupSecurityRolesActions() {
        securityRoles = new GenericToManyAssociation<Role, Integer>(getAccount().getSecurityRoles(), "account_securityRoles", roleController) {
            @Override
            protected void remove(Role role) {
                getAccount().removeSecurityRole(role);
            }

            @Override
            protected void add(Role role) {
                // add the object only to the securityRole side of the relation 
                getAccount().getSecurityRoles().add(role);
            }
        };
    }

    public GenericToManyAssociation<Role, Integer> getSecurityRoles() {
        return securityRoles;
    }
}