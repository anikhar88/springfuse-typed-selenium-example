/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/main/java/project/printer/Printer.e.vm.java
 */
package fr.vendredi.printer;

import java.util.Locale;
import javax.inject.Named;
import javax.inject.Singleton;
import fr.vendredi.domain.Account;
import fr.vendredi.printer.DiscoverablePrinter;

/**
 * {@link org.springframework.format.Printer} for {@link Account} 
 *
 * @see org.springframework.format.Printer
 * @see DiscoverablePrinter
 * @see TypeAwarePrinter
 */
@Named
@Singleton
public class AccountPrinter extends DiscoverablePrinter<Account> {
    public AccountPrinter() {
        super(Account.class);
    }

    @Override
    public String print(Account account, Locale locale) {
        if (account == null) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        appendIfNotEmpty(ret, account.getUsername());
        appendIfNotEmpty(ret, account.getFirstName());
        appendIfNotEmpty(ret, account.getLastName());
        return ret.toString();
    }
}