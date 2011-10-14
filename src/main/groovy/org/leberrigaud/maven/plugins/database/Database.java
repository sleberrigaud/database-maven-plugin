package org.leberrigaud.maven.plugins.database;

import java.util.List;

public interface Database
{
    List create(String username, String password, String dbName, String schema);

    List drop(String username, String password, String dbName, String schema);
}
