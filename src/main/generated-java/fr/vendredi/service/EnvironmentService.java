/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-backend-jpa:src/main/java/project/service/EnvironmentService.p.vm.java
 */
package fr.vendredi.service;

import static fr.vendredi.service.EnvironmentService.Environment.Development;
import static fr.vendredi.service.EnvironmentService.Environment.Integration;
import static fr.vendredi.service.EnvironmentService.Environment.Production;
import static fr.vendredi.service.EnvironmentService.Environment.toEnvironment;
import static org.apache.commons.lang.StringUtils.trimToEmpty;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

@Named
public class EnvironmentService {
    @Value("${env_name:development}")
    private String environmentName;

    public enum Environment {
        Development, Integration, Production;
        boolean is(String value) {
            return name().equalsIgnoreCase(trimToEmpty(value));
        }

        public static Environment toEnvironment(String value) {
            for (Environment environment : values()) {
                if (environment.is(value)) {
                    return environment;
                }
            }
            return Development;
        }
    }

    public boolean isDevelopment() {
        return Development.is(environmentName);
    }

    public boolean isIntegration() {
        return Integration.is(environmentName);
    }

    public boolean isProduction() {
        return Production.is(environmentName);
    }

    public Environment getEnvironment() {
        return toEnvironment(environmentName);
    }
}
