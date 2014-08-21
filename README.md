Grails I18N Domain Postgresql
=============================

This is a Grails plugin to internationalize the domain classes using a [Postgresql Hstore](http://www.postgresql.org/docs/9.3/static/hstore.html) type for storing all the values in one column instead of using one column for every language.
The plugin depends on [Grails Postgresql Extensions](http://grails.org/plugin/postgresql-extensions) plugins to use Hstore fields.

Using Groovy Metaprogramming the plugin adds getters to the internationalized domain classes to access the properties in the desired language. For example, if the domain class has a property called `title` configured as internationalizable, the plugin will add the method `getI18nTitle()` to the domain class.

The idea behind the plugin is to store all the translations in the same column. So, for example, if you have a _Book_ domain class you can store the _title_ and the _description_ internationalized. The key in the hstore field will be the language and the value will be the translation in the language.


```
mydb=# select * from book;
 id | version |                      title                                                                |                           description
----+---------+-------------------------------------------------------------------------------------------+----------------------------------------------------------------
  1 |       0 | "en"=>"Game of Thrones", "es"=>"Juego de Tronos"                                          | "en"=>"The book is about...", "es"=>"Este libro va..."
  2 |       0 | "en"=>"The Hitchhiker's Guide to the Galaxy", "es"=>"La Guía del Autoestopista Galáctico" | "en"=>"The meaning of life...", "es"=>"El sentido de la vida..."
```


* [Installation](#installation)
* [Configuration](#configuration)
* [Usage](#usage)
* [Author](#author)
* [Release notes](#release-notes)



Installation
------------

In `BuildConfig.groovy` add the following to install the plugin:

```groovy
plugins {
    ...
    compile ':i18n-domain-postgresql:<version>', {
        excludes "hibernate"
    }
    ...
}
```

You also need to install [Grails Postgresql Extensions](http://grails.org/plugin/postgresql-extensions) plugin. Please check the [Installation documentation](https://github.com/kaleidos/grails-postgresql-extensions#installation) for more information.



Configuration
-------------

To internationalize the fields `title` and `description` of a domain class `Book` you have to:

1. Mark the domain class you want to internationalize as `@International`.
2. Add a static `i18n` list with all the properties you want to internationalize.
3. Define the i18n properties as `Map` and map them using `HstoreMapType` hibernate user type. Please check [Grails Postgresql Extensions Hstore documentation](https://github.com/kaleidos/grails-postgresql-extensions#hstore) for more information.

```groovy
import net.kaleidos.grails.plugins.i18n.International
import net.kaleidos.hibernate.usertype.HstoreMapType

@International
class Book {

    static i18n = ['title', 'description']

    Map title
    Map description

    static mapping = {
        title type: HstoreMapType
        description type: HstoreMapType
    }
}
```

Now you have to create a custom i18n resolver to decide which language return. You have to create a class in `src/groovy` that implements the interface `I18nLocaleResolver` and define a bean with the name `i18Processor` with that implementation.

The plugin provides a default implementation in the class `I18nDefaultLocaleResolver` that returns the default locale of the computer.

For example, imagine that you're using _Spring Security_ and in your `User` domain class you have an attribute `Locale` with the desired language of the user.

```groovy
// File: src/groovy/com/yourcompany/SpringSecurityI18nLocaleResolver
package com.yourcompany

import net.kaleidos.grails.plugins.i18n.I18nLocaleResolver

class SpringSecurityI18nLocaleResolver implements I18nLocaleResolver {

    def springSecurityService

    @Override
    Locale getLocale() {
        return springSecurityService.currentUser?.locale ?: Locale.default
    }
}
```


```groovy
// File: grails-app/conf/spring/resources.groovy
beans = {
    ...
    i18nLocaleResolver(com.yourcompany.SpringSecurityI18nLocaleResolver) {
        springSecurityService = ref('springSecurityService')
    }
    ...
}
```


Usage
-----

With the plugin configured as in the previous section, you only have to use it :-)

```groovy
def book = new Book(title: [es: 'Juego de tronos', en: 'Game of Thrones'], description: [es: '...', en: '...']).save()

// If the user locale is english
println book.i18nTitle // it'll print "Game of Thrones"
println book.getI18nTitle() // the same

// You can always access the map
println book.title.en
println book.title['en']
```


Author
------

You can send any questions to:

- Iván López: lopez.ivan@gmail.com ([@ilopmar](https://twitter.com/ilopmar))

Collaborations are appreciated :-)

Release Notes
-------------

* 0.1 - 22/Aug/2014 - Initial version
