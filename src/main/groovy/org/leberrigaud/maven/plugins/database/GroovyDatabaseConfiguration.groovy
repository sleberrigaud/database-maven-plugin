package org.leberrigaud.maven.plugins.database

class GroovyDatabaseConfiguration implements DatabaseConfiguration
{
    String host, port, username, password, rootUsername, rootPassword, databaseSchema, databaseName, sid

    String getPort(String defaultPort) {
        return port ?: defaultPort
    }
}
