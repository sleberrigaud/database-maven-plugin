package org.leberrigaud.maven.plugins.database

/**
 * @goal create
 */
class CreateMojo extends AbstractDatabaseMojo
{
    void doExecute()
    {
        drop()
        create()
    }
}
