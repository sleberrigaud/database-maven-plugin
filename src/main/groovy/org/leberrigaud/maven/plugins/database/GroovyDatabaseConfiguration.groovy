package org.leberrigaud.maven.plugins.database

class GroovyDatabaseConfiguration implements DatabaseConfiguration
{
    String host, port, username, password, databaseSchema, databaseName, sid

    String getPort(String defaultPort)
    {
        return port ?: defaultPort
    }
}
