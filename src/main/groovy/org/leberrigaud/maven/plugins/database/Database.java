package org.leberrigaud.maven.plugins.database;

import java.util.List;

public interface Database
{
    String driverClass();

    String defaultPort();

    boolean supportsSchema();

    String defaultRootUsername();

    String adminDbName();
    
    String url(DatabaseConfiguration config);
    
    List<String> create(DatabaseConfiguration config);

    List<String> update(DatabaseConfiguration config);

    List<String> drop(DatabaseConfiguration config);
}
