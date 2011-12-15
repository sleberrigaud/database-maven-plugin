package org.leberrigaud.maven.plugins.database

/**
 * @requiresProject false
 * @goal drop
 */
class DropMojo extends AbstractDatabaseMojo
{
    void doExecute()
    {
        final config = validate()
        drop(config)
    }
}
