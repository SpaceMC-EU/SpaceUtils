package org.spacemc.utils.database

import org.bukkit.configuration.ConfigurationSection
import org.ktorm.database.Database
import org.ktorm.support.mysql.MySqlDialect

object DBConnectionHolder {

    lateinit var config: ConfigurationSection

    fun getDbConnection(): Database {
        val host = config.getString("database.ip")
        val port = config.getInt("database.port")
        val dbName = config.getString("database.db")
        val username = config.getString("database.user")
        val password = config.getString("database.password")

        return Database.connect("jdbc:mysql://${host}:${port}/${dbName}", user=username, password=password, dialect = MySqlDialect())
    }
}

val database
    get() = DBConnectionHolder.getDbConnection()