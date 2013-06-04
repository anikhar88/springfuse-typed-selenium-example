/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/test/java/domain/ModelTest.e.vm.java
 */
package fr.vendredi.domain;

import java.io.*;
import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

import fr.vendredi.util.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.vendredi.domain.Address;
import fr.vendredi.domain.Book;
import fr.vendredi.domain.Civility;
import fr.vendredi.domain.Document;
import fr.vendredi.domain.Role;

import fr.vendredi.domain.Civility;

/**
 * Basic tests for Account
 */
@SuppressWarnings("unused")
public class AccountTest {

    // test unique primary key
    @Test
    public void newInstanceHasNoPrimaryKey() {
        Account model = new Account();
        assertThat(model.isIdSet()).isFalse();
    }

    @Test
    public void isIdSetReturnsTrue() {
        Account model = new Account();
        model.setId(ValueGenerator.getUniqueString(36));
        assertThat(model.getId()).isNotNull();
        assertThat(model.isIdSet()).isTrue();
    }

    //-------------------------------------------------------------
    // Many to One:  Account.homeAddress ==> Address.id
    //-------------------------------------------------------------

    @Test
    public void manyToOne_setHomeAddress() {
        Account many = new Account();

        // init
        Address one = new Address();
        one.setId(ValueGenerator.getUniqueInteger());
        many.setHomeAddress(one);

        // make sure it is propagated properly
        assertThat(many.getHomeAddress()).isEqualTo(one);

        // now set it to back to null
        many.setHomeAddress(null);

        // make sure null is propagated properly
        assertThat(many.getHomeAddress()).isNull();
    }

    //-------------------------------------------------------------
    // One to Many: SimpleOneToMany ACCOUNT.ID ==> BOOK.ACCOUNT_ID
    //-------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // book.book <-- account.owners
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void oneToMany_addBook() {
        Account one = new Account();
        Book many = new Book();

        // init
        one.addBook(many);

        // make sure it is propagated
        assertThat(one.getCoolBooks()).contains(many);
        assertThat(one).isEqualTo(many.getOwner());

        // now set it to null
        one.removeBook(many);

        // make sure null is propagated
        assertThat(one.getCoolBooks().contains(many)).isFalse();
        assertThat(many.getOwner()).isNull();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // document.edoc <-- account.owners
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void oneToMany_addEdoc() {
        Account one = new Account();
        Document many = new Document();

        // init
        one.addEdoc(many);

        // make sure it is propagated
        assertThat(one.getEdocs()).contains(many);
        assertThat(one).isEqualTo(many.getOwner());

        // now set it to null
        one.removeEdoc(many);

        // make sure null is propagated
        assertThat(one.getEdocs().contains(many)).isFalse();
        assertThat(many.getOwner()).isNull();
    }

    //-------------------------------------------------------------
    // Pure Many to Many
    //-------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void manyToMany_addSecurityRole() {
        Account many1 = new Account();
        Role many2 = new Role();

        // add it
        many1.addSecurityRole(many2);

        // check it is propagated
        assertThat(many1.getSecurityRoles()).contains(many2);
        // now let's remove it
        many1.removeSecurityRole(many2);

        // check it is propagated
        assertThat(many1.getSecurityRoles().contains(many2)).isFalse();
    }

    @Test
    public void equalsUsingBusinessKey() {
        Account model1 = new Account();
        Account model2 = new Account();
        String username = ValueGenerator.getUniqueString(100);
        model1.setUsername(username);
        model2.setUsername(username);
        assertThat(model1).isEqualTo(model2);
        assertThat(model2).isEqualTo(model1);
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode());
    }
}