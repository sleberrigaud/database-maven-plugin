package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class MySql
{
    final static String PORT = '3306'
    final String driver = "com.mysql.jdbc.Driver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:mysql://$host:${port ? port : PORT}/mysql?autoReconnect=true" }

    final def createUser = {username, password -> "create user '$username'@'localhost' identified by '$password'" }
    final def createDb = {name -> "create database $name" }
    final def grantPrivileges = {dbName, user -> "grant all on ${dbName}.* to '$user'@'localhost';" }
    final def dropUser = {username -> "drop user '$username'@'localhost'" }
    final def dropDb = {name -> "drop database $name" };
}
