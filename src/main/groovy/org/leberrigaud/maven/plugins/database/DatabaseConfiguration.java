package org.leberrigaud.maven.plugins.database;

public interface DatabaseConfiguration
{
    String getHost();

    String getPort(String defaultPort);

    String getUsername();

    String getPassword();

    String getRootUsername();

    String getRootPassword();

    String getDatabaseSchema();

    String getDatabaseName();
    
    String getSid();
}
