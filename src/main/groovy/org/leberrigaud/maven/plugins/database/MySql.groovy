package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class MySql
{
    final String driver = "com.mysql.jdbc.Driver"
    final String url = "jdbc:mysql://localhost:3306/mysql"

    final def createUser = {username, password -> "create user '$username'@'localhost' identified by '$password'" }
    final def createDb = {name -> "create database $name" }
    final def grantPrivileges = {dbName, user -> "grant all on ${dbName}.* to '$user'@'localhost';" }
    final def dropUser = {username -> "drop user '$username'@'localhost'" }
    final def dropDb = {name -> "drop database $name" };
}
