package org.leberrigaud.maven.plugins.database

final class MySql implements Database
{
    String driverClass()
    {
        'com.mysql.jdbc.Driver'
    }

    String defaultPort()
    {
        '3306'
    }

    boolean supportsSchema()
    {
        false
    }

    String defaultRootUsername()
    {
        'root'
    }

    String adminDbName()
    {
        'mysql'
    }

    String url(DatabaseConfiguration config)
    {
        "jdbc:mysql://$config.host:${config.getPort(defaultPort())}/${config.databaseName}?autoReconnect=true"
    }

    List<String> create(DatabaseConfiguration config)
    {
        [
                "create user '$config.username'@'$config.host' identified by '$config.password';",
                "create database $config.databaseName;",
                "grant all on ${config.databaseName}.* to '$config.username'@'$config.host' identified by '$config.password';",
                "flush privileges;"
        ]
    }

    List<String> update(DatabaseConfiguration config)
    {
        []
    }
    
    List<String> drop(DatabaseConfiguration config)
    {
        [
                "drop database $config.databaseName",
                "drop user '$config.username'@'$config.host'"
        ]
    }
}
