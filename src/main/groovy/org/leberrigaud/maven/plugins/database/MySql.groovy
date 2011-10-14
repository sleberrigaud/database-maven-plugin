package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class MySql implements Database
{
    final static String PORT = '3306'
    final String driver = "com.mysql.jdbc.Driver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:mysql://$host:${port ? port : PORT}/mysql?autoReconnect=true" }

    List create(String username, String password, String dbName, String schema)
    {
        [
                "create user '$username'@'localhost' identified by '$password'",
                "create database $dbName",
                "grant all on ${dbName}.* to '$username'@'localhost';"
        ]
    }

    List drop(String username, String password, String dbName, String schema)
    {
        [
                "drop database $dbName",
                "drop user '$username'@'localhost'"
        ]
    }
}
