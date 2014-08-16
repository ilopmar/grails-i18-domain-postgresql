package net.kaleidos.grails.plugins.i18n

import javax.annotation.PostConstruct

class I18nProcessor {

    def i18nLocaleResolver
    def grailsApplication

    @PostConstruct
    void internationalizeDomain() {
        grailsApplication
            .domainClasses
            .findAll { cl -> cl.clazz.getAnnotation(International) }*.clazz
            .each { cl -> addI18nGetter(cl) }
    }

    private void addI18nGetter(Class clazz) {
        clazz.i18n.each { prop ->
            println "[International class] ${clazz} - ${prop}"

            clazz.metaClass."getI18n${prop.capitalize()}" = { ->
                delegate."$prop"[i18nLocaleResolver.locale.toString()]
            }
        }
    }
}