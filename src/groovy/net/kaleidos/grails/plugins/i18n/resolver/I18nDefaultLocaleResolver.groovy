package net.kaleidos.grails.plugins.i18n.resolver

import net.kaleidos.grails.plugins.i18n.I18nLocaleResolver

class I18nDefaultLocaleResolver implements I18nLocaleResolver {

    @Override
    Locale getLocale() {
        return Locale.default
    }
}