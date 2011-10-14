package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class Postgres implements Database
{
    final static String PORT = '5432'
    final String driver = "org.postgresql.Driver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:postgresql://$host:${port ? port : PORT}/postgres" }

    List create(String username, String password, String dbName, String schema)
    {
        [
                "create user $username with password '$password'",
                "create database $dbName",
                "grant all privileges on database $dbName to $username"
        ]
    }

    List drop(String username, String password, String dbName, String schema)
    {
        [
                "drop database $dbName",
                "drop role $username"
        ]
    }
}
