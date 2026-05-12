package StudentApp

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class ExposedStudentsDao : StudentsDao {

    override fun addStudent(student: Student): Int {
        var newId = 0

        transaction {
            newId = Students.insert {
                it[name] = student.name
                it[course] = student.course
                it[mark] = student.mark
            }[Students.id]
        }

        student.id = newId
        return newId
    }

    override fun findStudentById(id: Int): Student? {
        var student: Student? = null

        transaction {
            val row = Students
                .selectAll()
                .where { Students.id eq id }
                .singleOrNull()

            if (row != null) {
                student = Student(
                    id = row[Students.id],
                    name = row[Students.name],
                    course = row[Students.course],
                    markIn = row[Students.mark]
                )
            }
        }

        return student
    }

    override fun findStudentsByCourse(course: String): List<Student> {
        val matches = mutableListOf<Student>()

        transaction {
            val results = Students
                .selectAll()
                .where { Students.course eq course }

            for (row in results) {
                val student = Student(
                    id = row[Students.id],
                    name = row[Students.name],
                    course = row[Students.course],
                    markIn = row[Students.mark]
                )

                matches.add(student)
            }
        }

        return matches
    }

    override fun updateStudent(student: Student): Int {
        var updatedRows = 0

        transaction {
            updatedRows = Students.update(
                { Students.id eq student.id }
            ) {
                it[name] = student.name
                it[course] = student.course
                it[mark] = student.mark
            }
        }

        return updatedRows
    }

    override fun deleteStudentById(id: Int): Int {
        var deletedRows = 0

        transaction {
            deletedRows = Students.deleteWhere {
                Students.id eq id
            }
        }

        return deletedRows
    }
}