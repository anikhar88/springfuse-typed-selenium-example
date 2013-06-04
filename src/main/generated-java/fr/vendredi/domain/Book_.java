/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/main/java/project/domain/EntityMeta_.e.vm.java
 */
package fr.vendredi.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import fr.vendredi.domain.Account;

@StaticMetamodel(Book.class)
public abstract class Book_ {

    // Raw attributes
    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, String> bookTitle;
    public static volatile SingularAttribute<Book, Integer> numberOfPages;
    public static volatile SingularAttribute<Book, Integer> version;

    // Many to one
    public static volatile SingularAttribute<Book, Account> owner;
}