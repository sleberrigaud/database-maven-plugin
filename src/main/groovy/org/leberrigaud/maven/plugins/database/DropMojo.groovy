package org.leberrigaud.maven.plugins.database

/**
 * @goal drop
 */
class DropMojo extends AbstractDatabaseMojo
{
    void doExecute()
    {
        drop()
    }
}
