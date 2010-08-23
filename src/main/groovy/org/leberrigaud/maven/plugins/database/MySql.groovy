package org.leberrigaud.maven.plugins.database

/**
 *
 */
final class MySql
{
    final String driver = "com.mysql.jdbc.Driver"
    final String url = "jdbc:mysql://localhost:3306/mysql"

    final String createUser = {username, password -> "create user '$username'@'localhost' identified by '$password'" }
    final String createDb = {name -> "create database $name" }
    final String grantPrivileges = {dbName, user -> "grant all on ${dbName}.* to '$user'@'localhost';" }
    final String dropUser = {username -> "drop user '$username'@'localhost'" }
    final String dropDb = {name -> "drop database $name" };
}
