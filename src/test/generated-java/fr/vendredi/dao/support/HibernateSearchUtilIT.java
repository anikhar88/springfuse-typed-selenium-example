/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/test/java/dao/support/HibernateSearchUtilIT.p.vm.java
 */
package fr.vendredi.dao.support;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.vendredi.domain.Account;
import fr.vendredi.domain.Account_;
import fr.vendredi.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-test.xml" })
@Transactional
public class HibernateSearchUtilIT {

    @Inject
    private AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void buildExample() {
        accountRepository.save(jeanLouis());
        accountRepository.save(jeanMichel());
        entityManager.flush();
        getFullTextEntityManager(entityManager).flushToIndexes();
    }

    private Account jeanMichel() {
        return accountRepository.getNew() //
                .email("jmdexample@example.com") //
                .firstName("Jean-Michel") //
                .lastName("EXAMPLE") //
                .username("jmexample") //
                .password("xxx") //
                .isEnabled(true);
    }

    private Account jeanLouis() {
        return accountRepository.getNew() //
                .email("jlsample@example.com") //
                .firstName("Jean-Louis") //
                .lastName("SAMPLE") //
                .username("jlsample") //
                .password("xxx") //
                .isEnabled(true);
    }

    @Test
    public void term() {
        SearchParameters sp = new SearchParameters().term("jeaan");
        assertThat(accountRepository.find(sp)).isNotNull().hasSize(2);
    }

    @Test
    public void termOn() {
        SearchParameters sp = new SearchParameters().termOn("louhis", Account_.firstName);
        assertThat(accountRepository.find(sp)).isNotNull().hasSize(1);
    }

    @Test
    public void termOnNoMatch() {
        SearchParameters sp = new SearchParameters().termOn("louhis", Account_.email);
        assertThat(accountRepository.find(sp)).isNotNull().isEmpty();
    }

    @Test
    public void termOnAll() {
        SearchParameters sp = new SearchParameters().termOnAll("sammple", Account_.email, Account_.lastName);
        assertThat(accountRepository.find(sp)).isNotNull().hasSize(1);
    }

    @Test
    public void termOnAllNoMatch() {
        SearchParameters sp = new SearchParameters().termOnAll("sammple", Account_.email, Account_.firstName);
        assertThat(accountRepository.find(sp)).isNotNull().isEmpty();
    }

    @Test
    public void termOnAny() {
        SearchParameters sp = new SearchParameters().termOnAny("louhis", Account_.firstName, Account_.lastName);
        assertThat(accountRepository.find(sp)).isNotNull().hasSize(1);
    }

    @Test
    public void termOnAnyNoMatch() {
        SearchParameters sp = new SearchParameters().termOnAny("louhis", Account_.lastName, Account_.email);
        assertThat(accountRepository.find(sp)).isNotNull().isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchOnUninedexedAttribute() {
        accountRepository.find(new SearchParameters().termOnAny("louhis", Account_.isEnabled));
    }
}