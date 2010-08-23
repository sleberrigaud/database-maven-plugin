package org.leberrigaud.maven.plugins.database

import groovy.sql.Sql
import org.codehaus.gmaven.mojo.GroovyMojo

/**
 *
 */
abstract class AbstractDatabaseMojo extends GroovyMojo
{
    static final DB = [
            'mysql': new MySql(),
            'postgres': new Postgres()
    ]

    /**
     * The type of database to connect to
     * @parameter default-value="MYSQL"
     */
    String database

    /**
     * The name of the database to create
     * @parameter
     */
    String name

    /**
     * The username of the user to associate to the database
     * @parameter
     */
    String username

    /**
     * The password of the user to associate to the database
     * @parameter
     */
    String password

    /**
     * @parameter
     */
    String rootUsername

    /**
     * @parameter
     */
    String rootPassword

    /**
     * @parameter default-value="false"
     */
    boolean skip

    void drop()
    {
        final def db = DB[database]
        final Sql sql = Sql.newInstance(db.url, rootUsername, rootPassword, db.driver)

        executeSql(sql, db.dropUser(username), true)
        executeSql(sql, db.dropDb(name), true)
    }

    void create()
    {
        final def db = DB[database]
        final Sql sql = Sql.newInstance(db.url, rootUsername, rootPassword, db.driver)
        executeSql(sql, db.createUser(username, password))
        executeSql(sql, db.createDb(name))
        executeSql(sql, db.grantPrivileges(name, username))
    }

    private void executeSql(Sql runner, String sql)
    {
        executeSql runner, sql, false
    }

    private void executeSql(Sql runner, String sql, boolean ignoreException)
    {
        log.info sql
        try
        {
            runner.execute(sql)
        }
        catch (Exception e)
        {
            if (!ignoreException) throw e
        }
    }

    void execute()
    {
        if (!skip) doExecute()
        else log.info("Skipping database plugin execution")
    }

    abstract void doExecute()
}
