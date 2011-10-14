package org.leberrigaud.maven.plugins.database

final class Oracle implements Database
{
    final static String PORT = '1521'

    final String driver = "oracle.jdbc.OracleDriver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:oracle:thin:@$host:${port ? port : PORT}:orcl" }

    List create(String username, String password, String dbName, String schema)
    {
        [
                "GRANT CONNECT, RESOURCE TO $username IDENTIFIED BY $password"
        ]
    }

    List drop(String username, String password, String dbName, String schema)
    {
        [
                "DROP USER $username CASCADE"
        ]
    }
}
