/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/test/java/service/RepositoryTest.e.vm.java
 */
package fr.vendredi.repository;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.NonUniqueResultException;
import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.vendredi.domain.Account;
import fr.vendredi.repository.AccountRepository;
import fr.vendredi.dao.AccountDao;
import fr.vendredi.dao.support.SearchParameters;

/**
 * Unit test on AccountRepository
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountRepository accountRepository;

    @Test
    public void findUniqueOrNoneReturnsNull() {
        when(accountDao.findUniqueOrNone(any(Account.class), any(SearchParameters.class))).thenReturn(null);

        Account result = accountRepository.findUniqueOrNone(new Account());

        assertThat(result).isNull();
        verify(accountDao, times(1)).findUniqueOrNone(any(Account.class), any(SearchParameters.class));
    }

    @Test
    public void findUniqueOrNoneReturnsSingleValue() {
        Account unique = new Account();

        when(accountDao.findUniqueOrNone(any(Account.class), any(SearchParameters.class))).thenReturn(unique);

        Account result = accountRepository.findUniqueOrNone(new Account());

        assertThat(result).isNotNull().isSameAs(unique);
        verify(accountDao, times(1)).findUniqueOrNone(any(Account.class), any(SearchParameters.class));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NonUniqueResultException.class)
    public void findUniqueOrNoneThrowsExceptionWhenNonUniqueResults() {
        when(accountDao.findUniqueOrNone(any(Account.class), any(SearchParameters.class))).thenThrow(NonUniqueResultException.class);

        accountRepository.findUniqueOrNone(new Account());
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NoResultException.class)
    public void findUniqueThrowsExceptionWhenNoResult() {
        when(accountDao.findUnique(any(Account.class), any(SearchParameters.class))).thenThrow(NoResultException.class);

        accountRepository.findUnique(new Account());
    }

    @Test
    public void findUnique() {
        Account unique = new Account();

        when(accountDao.findUnique(any(Account.class), any(SearchParameters.class))).thenReturn(unique);

        Account result = accountRepository.findUnique(new Account());

        assertThat(result).isNotNull();
        verify(accountDao, times(1)).findUnique(any(Account.class), any(SearchParameters.class));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NonUniqueResultException.class)
    public void findUniqueThrowsExeptionWhenNonUniqueResult() {
        when(accountDao.findUnique(any(Account.class), any(SearchParameters.class))).thenThrow(NonUniqueResultException.class);

        accountRepository.findUnique(new Account());
    }
}