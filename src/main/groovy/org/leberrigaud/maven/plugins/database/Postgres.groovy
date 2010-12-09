package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class Postgres
{
    final static String PORT = '5432'
    final String driver = "org.postgresql.Driver"

    final def url(def host = 'localhost', def port = PORT) { "jdbc:postgresql://$host:${port ? port : PORT}/postgres" }

    final def createUser = {username, password -> "create user $username with password '$password'" }
    final def createDb = {name -> "create database $name" }
    final def grantPrivileges = {dbName, user -> "grant all privileges on database $dbName to $user" }
    final def dropUser = {username -> "drop role $username" }
    final def dropDb = {name -> "drop database $name" };
}
