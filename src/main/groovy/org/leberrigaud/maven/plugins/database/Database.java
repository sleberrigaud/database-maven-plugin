package org.leberrigaud.maven.plugins.database;

import java.util.List;

public interface Database
{
    String driverClass();

    String defaultPort();

    boolean supportsSchema();

    String defaultRootUsername();

    String url(DatabaseConfiguration config);
    
    List<String> create(DatabaseConfiguration config);

    List<String> drop(DatabaseConfiguration config);
}
