package org.leberrigaud.maven.plugins.database

final class SqlServer
{
    final static String PORT = '1433'

    final String driver = 'net.sourceforge.jtds.jdbc.Driver'

    final def url(def host = 'localhost', def port = PORT) { "jdbc:jtds:sqlserver://$host:${port ? port : PORT}/master" }

    final def createUser = {username, password -> "CREATE LOGIN $username WITH PASSWORD = '$password'; CREATE USER $username FOR LOGIN $username"}
    final def createDb = {name -> "CREATE DATABASE $name"}
    final def grantPrivileges = {dbName, user -> "ALTER AUTHORIZATION ON DATABASE::$dbName TO $user; ALTER USER $user WITH DEFAULT_SCHEMA = $dbName"}
    final def dropUser = {username -> "DROP LOGIN $username;DROP USER $username" }
    final def dropDb = {name -> "DROP DATABASE $name"};
}
