package org.leberrigaud.maven.plugins.database

final class Oracle implements Database
{
    final static String PORT = '1521'

    final String driver = "oracle.jdbc.OracleDriver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:oracle:thin:@$host:${port ? port : PORT}:orcl" }

    List create(String username, String password, String dbName, String schema)
    {
        def sql = []
        if (schema) sql.addAll([
                "CREATE USER $schema IDENTIFIED BY ${schema}_pwd DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS"
        ])
        sql.addAll([
                "CREATE USER $username IDENTIFIED BY $password DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS",
                "GRANT ALL PRIVILEGES TO $username WITH ADMIN OPTION",
                "GRANT CONNECT TO $username",
        ])
        sql
    }

    List drop(String username, String password, String dbName, String schema)
    {
        def sql = []
        if (schema) sql.add("DROP USER $schema CASCADE")
        sql.add("DROP USER $username CASCADE")
        sql
    }
}