package fr.vendredi.web.filter;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

public class LocaleResolverRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(LocaleResolverRequestFilter.class);

    public static final String LOCALE_PARAMETER = "locale";

    private LocaleResolver localeResolver;

    @Override
    protected void initFilterBean() throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        localeResolver = context.getBean(LocaleResolver.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        forceLocaleFromParameter(request, response);
        try {
            LocaleContextHolder.setLocale(localeResolver.resolveLocale(request));
            filterChain.doFilter(request, response);
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }

    private void forceLocaleFromParameter(HttpServletRequest request, HttpServletResponse response) {
        String localeParameter = null;
        try {
            localeParameter = request.getParameter(LOCALE_PARAMETER);
            if (isNotBlank(localeParameter)) {
                Locale locale = LocaleUtils.toLocale(localeParameter);
                log.info("forcing locale to {}", locale.getLanguage());
                localeResolver.setLocale(request, response, locale);
            }
        } catch (IllegalArgumentException e) {
            log.error("Locale " + localeParameter + " is not valid");
        }
    }
}
