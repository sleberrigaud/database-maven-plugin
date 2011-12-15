package org.leberrigaud.maven.plugins.database

final class SqlServer implements Database
{
    String driverClass()
    {
        'net.sourceforge.jtds.jdbc.Driver'
    }

    String defaultPort()
    {
        '1433'
    }

    boolean supportsSchema()
    {
        true
    }

    String defaultRootUsername()
    {
        'sa'
    }

    String url(DatabaseConfiguration config)
    {
        "jdbc:jtds:sqlserver://$config.host:${config.getPort(defaultPort())}/master"
    }

    List<String> create(DatabaseConfiguration config)
    {
        def sql = []
        sql.addAll([
                "CREATE LOGIN $config.username WITH PASSWORD = '$config.password'",
                "CREATE USER $config.username FOR LOGIN $config.username"
        ])
        sql.addAll([
                "CREATE DATABASE $config.databaseName",
                "ALTER AUTHORIZATION ON DATABASE::$config.databaseName TO $config.username",
                "ALTER USER $config.username WITH DEFAULT_SCHEMA = ${config.databaseSchema ?: config.databaseName}",
        ])
        if (config.databaseSchema)
        {
            sql.addAll([
                    "USE $config.databaseName",
                    "CREATE SCHEMA $config.databaseSchema",
            ])
        }

        sql
    }

    List<String> drop(DatabaseConfiguration config)
    {
        [
                "DROP DATABASE $config.databaseName",
                "DROP LOGIN $config.username",
                "DROP USER $config.username"
        ]
    }
}
