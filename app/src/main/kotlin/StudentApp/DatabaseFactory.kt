package StudentApp

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:sqlite:university.db",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(Students)
        }
    }
}