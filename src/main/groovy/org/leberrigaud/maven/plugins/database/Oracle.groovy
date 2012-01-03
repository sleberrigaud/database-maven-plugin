package org.leberrigaud.maven.plugins.database

final class Oracle implements Database
{
    boolean sysdba

    String driverClass()
    {
        'oracle.jdbc.OracleDriver'
    }

    String defaultPort()
    {
        '1521'
    }

    String defaultSid()
    {
        'orcl'
    }

    boolean supportsSchema()
    {
        true
    }

    String defaultRootUsername()
    {
        'sys'
    }

    String adminDbName()
    {
        ''
    }

    String url(DatabaseConfiguration config)
    {
        "jdbc:oracle:thin:@$config.host:${config.getPort(defaultPort())}:${config.sid ?: defaultSid()}"
    }

    List<String> create(DatabaseConfiguration config)
    {
        def sql = []
        if (config.databaseSchema) sql.addAll([
                "CREATE USER $config.databaseSchema IDENTIFIED BY ${config.databaseSchema}_pwd DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS"
        ])
        sql.addAll([
                "CREATE USER $config.username IDENTIFIED BY $config.password DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS",
                "GRANT ALL PRIVILEGES TO $config.username WITH ADMIN OPTION",
                "GRANT CONNECT TO $config.username",
        ])
        sql
    }

    List<String> update(DatabaseConfiguration config)
    {
        []
    }

    List<String> drop(DatabaseConfiguration config)
    {
        def sql = []
        if (config.databaseSchema) sql.add("DROP USER $config.databaseSchema CASCADE")
        sql.add("DROP USER $config.username CASCADE")
        sql
    }
}