package org.leberrigaud.maven.plugins.database

final mojo = new CreateMojo()

// db admin
mojo.database = 'sqlserver'

mojo.rootUsername = 'sa'
mojo.rootPassword = '57ia7ouq'

// host config
mojo.host = '192.168.0.106'
mojo.port = '1433'

mojo.schema = 'ao_schema'

// user and database created
mojo.name = 'ao_test'
mojo.username = 'ao_user'
mojo.password = 'ao_password'

mojo.execute()

println "Mojo $mojo executed"