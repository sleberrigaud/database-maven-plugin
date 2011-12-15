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

    String url(DatabaseConfiguration config)
    {
        "jdbc:mysql://$config.host:${config.getPort(defaultPort())}/mysql?autoReconnect=true"
    }

    List<String> create(DatabaseConfiguration config)
    {
        [
                "create user '$config.username'@'localhost' identified by '$config.password'",
                "create database $config.databaseName",
                "grant all on ${config.databaseName}.* to '$config.username'@'localhost';"
        ]
    }

    List<String> drop(DatabaseConfiguration config)
    {
        [
                "drop database $config.databaseName",
                "drop user '$config.username'@'localhost'"
        ]
    }
}
