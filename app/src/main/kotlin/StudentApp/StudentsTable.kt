package StudentApp

import org.jetbrains.exposed.v1.core.Table

object Students : Table("students") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val course = varchar("course", 100)
    val mark = integer("mark")

    override val primaryKey = PrimaryKey(id)
}