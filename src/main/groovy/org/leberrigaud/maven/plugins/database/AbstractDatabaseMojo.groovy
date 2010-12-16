package org.leberrigaud.maven.plugins.database

import groovy.sql.Sql
import org.codehaus.gmaven.mojo.GroovyMojo
import org.apache.maven.plugin.MojoFailureException

/**
 *
 */
abstract class AbstractDatabaseMojo extends GroovyMojo
{
    static final DB = [
            'mysql': new MySql(),
            'postgres': new Postgres(),
            'oracle': new Oracle()
    ]

    /**
     * The type of database to connect to
     * @parameter default-value="mysql"
     */
    String database

    /**
     * The host the database server lives on
     * @parameter expression="${db.host}" default-value="localhost"
     */
    String host;

    /**
     * the port on which to access the database via JDBC.
     * It has no default value set here, if unassigned it will default to the default port for each DB.
     * @parameter expression="${db.port}"
     */
    String port;

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
     * Additional properties one might want to pass when connecting to the database
     * @parameter
     */
    Map<String, String> properties = [:]

    /**
     * @parameter
     */
    String rootUsername

    /**
     * @parameter
     */
    String rootPassword

    /**
     * @parameter expression="${db.skip}" default-value="false"
     */
    boolean skip

    /**
     * Whether or not to show passwords when running the plugin in DEBUG mode.
     * @parameter expression="${db.showPasswords}" default-value="false"
     */
    boolean showPasswords

    void drop()
    {
        final def db = db()
        final Sql sql = newSql(db)

        executeSql(sql, db.dropDb(name), true)
        executeSql(sql, db.dropUser(username), true)
    }

    void create()
    {
        final def db = db()
        final Sql sql = newSql(db)
        executeSql(sql, db.createUser(username, password))
        executeSql(sql, db.createDb(name))
        executeSql(sql, db.grantPrivileges(name, username))
    }

    def db()
    {
        def db = DB[database]
        if (!db)
        {
            throw new MojoFailureException("Could not find database '$database'")
        }
        return db
    }

    Sql newSql(def db)
    {
        final Properties props = new Properties();
        props.putAll(properties)
        props['user'] = rootUsername
        props['password'] = rootPassword ? rootPassword : ""

        final url = db.url(host, port)

        if (log.debugEnabled)
        {
            log.debug "Accessing database at '$url' with username '$rootUsername' ${showPasswords ? "and password $rootPassword":''}"
            log.debug "Additional properies are '$properties'"
        }
        return Sql.newInstance(url, props, db.driver)
    }

    private void executeSql(Sql runner, String sql)
    {
        if (sql) executeSql runner, sql, false
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
            else log.info("Error running '$sql': $e.message")
        }
    }

    void execute()
    {
        if (!skip) doExecute()
        else log.info("Skipping database plugin execution")
    }

    abstract void doExecute()
}
