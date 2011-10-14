package org.leberrigaud.maven.plugins.database

final class SqlServer implements Database
{
    final static String PORT = '1433'

    final String driver = 'net.sourceforge.jtds.jdbc.Driver'

    final def url(def host = 'localhost', def port = PORT) { "jdbc:jtds:sqlserver://$host:${port ? port : PORT}/master" }

    List create(String username, String password, String dbName, String schema)
    {
        [
                "CREATE LOGIN $username WITH PASSWORD = '$password'",
                "CREATE USER $username FOR LOGIN $username",
                "CREATE DATABASE $dbName",
                "ALTER AUTHORIZATION ON DATABASE::$dbName TO $username",
                "ALTER USER $username WITH DEFAULT_SCHEMA = $dbName"
        ]
    }

    List drop(String username, String password, String dbName, String schema)
    {
        [
                "DROP DATABASE $dbName",
                "DROP LOGIN $username;DROP USER $username"
        ]
    }
}
