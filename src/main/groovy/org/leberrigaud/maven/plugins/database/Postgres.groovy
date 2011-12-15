package org.leberrigaud.maven.plugins.database

final class Postgres implements Database
{
    String driverClass()
    {
        'org.postgresql.Driver'
    }

    String defaultPort()
    {
        '5432'
    }

    boolean supportsSchema()
    {
        false
    }

    String defaultRootUsername()
    {
        'postgres'
    }

    String url(DatabaseConfiguration config)
    {
        "jdbc:postgresql://$config.host:${config.getPort(defaultPort())}/postgres"
    }

    List<String> create(DatabaseConfiguration config)
    {
        [
                "create user $config.username with password '$config.password'",
                "create database $config.databaseName",
                "grant all privileges on database $config.databaseName to $config.username"
        ]
    }

    List<String> drop(DatabaseConfiguration config)
    {
        [
                "drop database $config.databaseName",
                "drop role $config.username"
        ]
    }
}
