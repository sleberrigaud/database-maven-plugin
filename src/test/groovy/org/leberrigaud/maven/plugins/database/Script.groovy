package org.leberrigaud.maven.plugins.database

final mojo = new CreateMojo()
mojo.database = 'sqlserver'

// db admin
mojo.rootUsername = 'sa'
mojo.rootPassword = '57ia7ouq'

// host config
mojo.host = '192.168.0.106'
mojo.port = '1433'

// user and database created
mojo.name = 'ao_test'
mojo.schema = 'ao_schema'
mojo.username = 'ao_user'
mojo.password = 'ao_password'

mojo.execute()

println "Mojo $mojo executed"