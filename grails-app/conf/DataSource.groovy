dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of '', 'create', 'create-drop','update'
            driverClassName = "org.postgresql.Driver"
            dialect = "net.kaleidos.hibernate.PostgresqlExtensionsDialect"
            url = "jdbc:postgresql://localhost:5432/i18n_domain"
            username = "i18n_domain"
            password = "i18n_domain"
            loggingSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop" // one of '', 'create', 'create-drop','update'
            driverClassName = "org.postgresql.Driver"
            dialect = "net.kaleidos.hibernate.PostgresqlExtensionsDialect"
            url = "jdbc:postgresql://localhost:5432/i18n_domain_test"
            username = "i18n_domain"
            password = "i18n_domain"
            loggingSql = true
        }
    }
}