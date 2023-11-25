package org.spacemc.spaceutils.database

import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.ktorm.schema.SqlType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.util.*

object UuidSqlType : SqlType<UUID>(Types.OTHER, "uuid") {

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: UUID) {
        ps.setObject(index, parameter.toString())
    }

    override fun doGetResult(rs: ResultSet, index: Int): UUID? {
        val uuid = rs.getObject(index) as String? ?: return null
        return UUID.fromString(uuid)
    }
}

@Suppress("unused")
fun BaseTable<*>.uuid(name: String): Column<UUID> {
    return registerColumn(name, UuidSqlType)
}