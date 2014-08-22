class I18nDomainPostgresqlGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2.5 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/controllers/**",
        "grails-app/domain/**",
        "grails-app/i18n/**",
        "grails-app/services/**",
        "grails-app/taglib/**",
        "grails-app/utils/**",
        "grails-app/views/**",
        "web-app/**",
        "scripts/**"
    ]

    def title = "Grails I18n Domain Postgresql Plugin"
    def author = "Iván López"
    def authorEmail = "lopez.ivan@gmail.com"
    def description = '''\
This plugin is intented to internationalize the domain classes using a Postgresql Hstore type for storing all the values in one column instead of using one column for every language.
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/lmivan/grails-i18-domain-postgresql/blob/master/README.md"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
   def license = "APACHE"

    // Location of the plugin's issue tracker.
   def issueManagement = [ system: "GITHUB", url: "https://github.com/lmivan/grails-i18-domain-postgresql/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/lmivan/grails-i18-domain-postgresql" ]

    def doWithSpring = {
        i18nLocaleResolver(net.kaleidos.grails.plugins.i18n.resolver.I18nDefaultLocaleResolver)
        i18Processor(net.kaleidos.grails.plugins.i18n.I18nProcessor) {
            i18nLocaleResolver = ref("i18nLocaleResolver")
            grailsApplication = ref("grailsApplication")
        }
    }
}
