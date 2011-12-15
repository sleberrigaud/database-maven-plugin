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
            'oracle': new Oracle(),
            'sqlserver': new SqlServer(),
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
     * @parameter default-value="testdb"
     */
    String name

    /**
     * The schema of the database to create
     * @parameter
     */
    String schema

    /**
     * The username of the user to associate to the database
     * @parameter default-value="testuser"
     */
    String username

    /**
     * The password of the user to associate to the database
     * @parameter default-value="testpwd"
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
     * @parameter default-value=""
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

    /**
     * @parameter expression="${db.prompt}" default-value="false"
     */
    boolean prompt

    DatabaseConfiguration validate()
    {
        DatabaseConfiguration config = new GroovyDatabaseConfiguration()
        
        checkDatabase()
        config.host = checkHost()
        config.port = checkPort()
        config.databaseName = checkName()
        config.databaseSchema = checkSchema()
        config.sid = checkSid();
        config.username = checkUsername()
        config.password = checkPassword()
        checkSysDba()
        checkRootUsername()
        checkRootPassword()
        
        config
    }

    def checkDatabase()
    {
        if (!isBatch())
        {
            database = prompt("What database do you want to work with.\nPossible values are: ${DB.keySet().join(',')}", database)
        }
    }

    def checkHost()
    {
        if (!isBatch())
        {
            host = prompt("What host does the database live on", host)
        }
        host
    }

    def checkPort()
    {
        if (!isBatch())
        {
            port = prompt("What port does the database listen on", db().defaultPort())
        }
        port
    }
    
    def checkName()
    {
        if (!isBatch())
        {
            name = prompt("Please specify the name of the database to create/drop", name)
        }
        if (!name)
        {
            log.error "You didn't specify a name for the database"
            throw new MojoFailureException("You didn't specify a name for the database")
        }
        name
    }

    def checkSchema()
    {
        if (db().supportsSchema() && !isBatch())
        {
            schema = prompt("Please optionally specify the name of the database schema to use")
        }
        schema
    }

    def checkSid()
    {
        def sid = ''
        if (database == 'oracle' && !isBatch())
        {
            sid = prompt("Please specify the sid of the database to use", db().defaultSid())
        }
        sid
    }

    def checkUsername()
    {
        if (!isBatch())
        {
            username = prompt("Please specify the username of the user that will have access to the database", username)
        }
        if (!username)
        {
            log.error "You didn't specify a username for the database user"
            throw new MojoFailureException("You didn't specify a username for the database user")
        }
        username
    }

    def checkPassword()
    {
        if (!isBatch())
        {
            password = prompt("Please specify the password of the user that will have access to the database", password)
        }
        if (!password)
        {
            log.error "You didn't specify a password for the database user"
            throw new MojoFailureException("You didn't specify a password for the database user")
        }
        password
    }

    def checkRootUsername()
    {
        if (!isBatch())
        {
            rootUsername = prompt("Please specify the root user of the database", db().defaultRootUsername())
        }
        if (!rootUsername)
        {
            log.error "You didn't specify the root user for the database"
            throw new MojoFailureException("You didn't specify the root user for the database")
        }
    }

    def checkRootPassword()
    {
        if (!isBatch())
        {
            rootPassword = prompt("Please specify the root password of the database")
        }
    }

    def checkSysDba()
    {
        if (database == 'oracle' && !isBatch())
        {
            db().sysdba = prompt("Should the connection to Oracle be as sysdba (y/n)").toBoolean()
        }
    }
    
    private boolean isBatch()
    {
        return !prompt
    }

    String prompt(String msg, String defaultValue = "")
    {
        String message = msg
        if (defaultValue) message += " ($defaultValue)"

        final input = System.console().readLine("$message:\n")
        input ?: defaultValue
    }

    void drop(DatabaseConfiguration config)
    {
        final def db = db()
        final Sql sql = newSql(db, config)
        executeSql(sql, db.drop(config), true)
    }

    void create(DatabaseConfiguration config)
    {
        final def db = db()
        final Sql sql = newSql(db, config)
        executeSql(sql, db.create(config))
    }

    Database db()
    {
        def db = DB[database]
        if (!db)
        {
            throw new MojoFailureException("Could not find database '$database'")
        }
        return db
    }

    Sql newSql(def db, def config)
    {
        final Properties props = new Properties();
        props.putAll(properties)
        if (database == 'oracle' && db.sysdba)
        {
            props['internal_logon'] = 'sysdba'
        }
        props['user'] = rootUsername
        props['password'] = rootPassword ? rootPassword : ""

        final url = db.url(config)

        log.info"Accessing database at '$url'..."
        if (log.debugEnabled)
        {
            log.debug "... with username '$rootUsername' ${showPasswords ? "and password $rootPassword" : ''}"
            log.debug "Additional properies are '$properties'"
        }
        Sql.newInstance(url, props, db.driverClass())
    }

    private void executeSql(Sql runner, List sql)
    {
        executeSql runner, sql, false
    }

    private void executeSql(Sql runner, List sql, boolean ignoreException)
    {
        sql.each
        {
            log.info it
            try
            {
                runner.execute(it.toString())
            }
            catch (Exception e)
            {
                if (!ignoreException) throw e
                else log.info("Error running '$sql': $e.message")
            }
        }
    }

    void execute()
    {
        if (!skip) doExecute()
        else log.info("Skipping database plugin execution")
    }

    abstract void doExecute()
}
