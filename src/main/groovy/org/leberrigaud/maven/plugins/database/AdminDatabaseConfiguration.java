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

    @Override
    public String getRootUsername()
    {
        return configuration.getRootUsername();
    }

    public String getPassword()
    {
        return configuration.getPassword();
    }

    @Override
    public String getRootPassword()
    {
        return configuration.getRootPassword();
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
