package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class Postgres
{
    final String driver = "org.postgresql.Driver"
    final String url = "jdbc:postgresql://localhost:5432/postgres"

    final def createUser = {username, password -> "create user $username with password '$password'" }
    final def createDb = {name -> "create database $name" }
    final def grantPrivileges = {dbName, user -> "grant all privileges on database $dbName to $user" }
    final def dropUser = {username -> "drop user $username" }
    final def dropDb = {name -> "drop database $name" };
}
