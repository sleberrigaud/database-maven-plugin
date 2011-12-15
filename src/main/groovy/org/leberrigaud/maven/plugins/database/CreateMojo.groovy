package org.leberrigaud.maven.plugins.database

/**
 * @requiresProject false
 * @goal create
 */
class CreateMojo extends AbstractDatabaseMojo
{
    void doExecute()
    {
        final config = validate()
        drop(config)
        create(config)
    }
}
