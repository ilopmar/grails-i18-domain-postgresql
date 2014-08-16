package net.kaleidos.grails.plugins.resolver

import net.kaleidos.grails.plugins.i18n.resolver.I18nDefaultLocaleResolver
import spock.lang.Specification

class I18nDefaultLocaleResolverSpec extends Specification {

    void 'check the default locale'() {
        given: 'an i18n default locale resolver'
            def i18nDefaultLocaleResolver = new I18nDefaultLocaleResolver()

        expect: 'the locale is the default'
            i18nDefaultLocaleResolver.locale == Locale.default
    }

    void 'set a new default locale and check it'() {
        given: 'a new default locale'
            Locale.setDefault(new Locale("es"))

        and: 'the default i18n resolver'
            def i18nDefaultLocaleResolver = new I18nDefaultLocaleResolver()

        expect: 'the locale is the new default'
            i18nDefaultLocaleResolver.locale == new Locale("es")
    }
}