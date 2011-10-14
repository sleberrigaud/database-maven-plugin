package org.leberrigaud.maven.plugins.database

final class Oracle
{
    final static String PORT = '1521'

    final String driver = "oracle.jdbc.OracleDriver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:oracle:thin:@$host:${port ? port : PORT}:orcl" }

    final def createUser = {username, password -> ["GRANT CONNECT, RESOURCE TO $username IDENTIFIED BY $password"]}
    final def createDb = {name -> []}
    final def grantPrivileges = {dbName, user, schema -> []}
    final def dropUser = {username -> ["DROP USER $username CASCADE"] }
    final def dropDb = {name, schema -> []};
}
