This is the database maven plugin. Its intent is simply to make it easy to create and drop databases and an associated
user when using maven. A typical usage would be for creating a database before integration testing and cleaning up
afterwards.

Supported databases for now are:
    * MySQL
    * PostgresQL

Future versions of the plugin will aim at supporting:
    * Oracle
    * MS SQL
    * DB2

Note: This is plugin is different to the sql-maven-plugin in that it is more specific and aims at being simpler in
doing this specific task. The sql-maven-plugin allows you to run any SQL statements against databases. You can find the
SQL maven plugin at http://mojo.codehaus.org/sql-maven-plugin/
