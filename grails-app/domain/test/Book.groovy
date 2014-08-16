package test

import net.kaleidos.grails.plugins.i18n.International
import net.kaleidos.hibernate.usertype.HstoreMapType

@International
class Book {

    static i18n = ['title']

    Map title
    Map description

    static constraints = {
    }

    static mapping = {
        title type: HstoreMapType
        description type: HstoreMapType
    }
}