package net.kaleidos.grails.plugins

import net.kaleidos.grails.plugins.i18n.I18nProcessor
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification
import test.Author
import test.Book

class I18nProcessorSpec extends Specification {

    void 'check that the getters are added'() {
        given: 'a I18nProcessor'
            def grailsApplication = Stub(GrailsApplication)
            grailsApplication.metaClass.getDomainClasses = { ->
                [new DefaultGrailsDomainClass(Book), new DefaultGrailsDomainClass(Author)]
            }
            def processor = new I18nProcessor(grailsApplication: grailsApplication)

        when: 'the internationalized properties are processed'
            processor.internationalizeDomain()

        then: 'the i18n properties are added'
            def book = new Book()
            book.respondsTo("getI18nTitle")
            !book.respondsTo("getI18nDescription")

        and: 'the i18n properties are not added'
            def author = new Author()
            !author.respondsTo("getI18nCountry")
    }
}