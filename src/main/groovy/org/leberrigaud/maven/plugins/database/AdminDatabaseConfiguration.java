package org.leberrigaud.maven.plugins.database;

class AdminDatabaseConfiguration implements DatabaseConfiguration
{
    private final Database database;
    private final DatabaseConfiguration configuration;

    AdminDatabaseConfiguration(Database database, DatabaseConfiguration configuration)
    {
        this.configuration = configuration;
        this.database = database;
    }

    public String getHost()
    {
        return configuration.getHost();
    }

    public String getPort(String defaultPort)
    {
        return configuration.getPort(defaultPort);
    }

    public String getUsername()
    {
        return configuration.getUsername();
    }

    public String getPassword()
    {
        return configuration.getPassword();
    }

    public String getDatabaseSchema()
    {
        return configuration.getDatabaseSchema();
    }

    public String getDatabaseName()
    {
        return database.adminDbName();
    }

    public String getSid()
    {
        return configuration.getSid();
    }
}
