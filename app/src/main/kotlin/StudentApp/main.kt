package StudentApp

import StudentApp.*
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update

// Step 3: This object defines the database table structure using Exposed.
// It represents the "students" table in SQLite.
object Students : Table("students") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val course = varchar("course", 100)
    val mark = integer("mark")
    override val primaryKey = PrimaryKey(id)
}

// Step 4: Helper function to add student to the DB.
fun addStudent(name: String, course: String, mark: Int): Int {
    return Students.insert {
        it[Students.name] = name
        it[Students.course] = course
        it[Students.mark] = mark
    }[Students.id]
}

// Step 5: Helper Function to search student by Course
fun findStudentsByCourse(course: String): List<Student> {

    val matches = mutableListOf<Student>()

    val results = Students.selectAll()
        .where { Students.course eq course }

    for (row in results) {
        val student = Student(
            row[Students.id].toString(),
            row[Students.name],
            row[Students.course],
            row[Students.mark]
        )

        matches.add(student)
    }

    return matches
}


// Step 6: Helper Function to search student by ID
fun findStudentById(id: Int): Student? {

    val row = Students
        .selectAll()
        .where { Students.id eq id }
        .singleOrNull()

    if (row == null) {
        return null
    }

    return Student(
        row[Students.id].toString(),
        row[Students.name],
        row[Students.course],
        row[Students.mark]
    )
}

// Step 7 — Delete Student by ID
fun deleteStudentById(id: Int): Int {

    return Students.deleteWhere {
        Students.id eq id
    }
}

// Step 8 — Edit Student Details
fun updateStudent(
    id: Int,
    name: String,
    course: String,
    mark: Int
): Int {

    return Students.update(
        { Students.id eq id }
    ) {

        it[Students.name] = name
        it[Students.course] = course
        it[Students.mark] = mark
    }
}

fun displayMenu() {
    println("1: Add student")
    println("2: Search for student by ID")
    println("3: Search for students by course")
    // Step 7 — Delete Student by ID
    println("4: Delete student by ID")
    // Step 8 — Edit Student Details
    println("5: Edit student details")
    println("6: Quit")
}

fun main() {

    // Step 3: Connects the Kotlin application to the SQLite database file.
    // If university.db does not exist, SQLite will create it.
    Database.connect(url = "jdbc:sqlite:university.db", driver = "org.sqlite.JDBC")

    // SchemaUtils.create creates the Students table if it does not already exist.
    transaction { SchemaUtils.create(Students) }

    // ORIGINAL CODE:
    val university = University()
    var running = true

    while (running) {
        displayMenu()

        val input = readln()

        when (input) {
            "1" -> {
                println("Enter name:")
                val name = readln()

                println("Enter course:")
                val course = readln()

                // XXprintln("Enter id:")
                // XXval id = readln()

                println("Enter mark:")
                val mark = readln().toInt()

                // XXval student = Student(id, name, course, mark)
                // XXuniversity.addStudent(student)

                // Step 4: insert a student in the database.
                transaction {
                    val newId = addStudent(name, course, mark)
                    println("Student added with ID: $newId")
                }
            }

            "2" -> {
                println("Enter ID:")
                val id = readln().toInt()

                //X val student = university.findStudentById(id)

                // Step 6: Serach a student by ID.
                transaction {
                    val student = findStudentById(id)

                    if (student == null) {
                        println("No student found")
                    } else {
                        println(student)
                    }
                }
            }

            "3" -> {
                println("Enter course:")
                val course = readln()

                // XXval matchingStudents = university.findStudentsByCourse(course)

                // Step 5: Serach Student By Course
                transaction {
                    val matchingStudents = findStudentsByCourse(course)

                    if (matchingStudents.isEmpty()) {
                        println("No students found")
                    } else {
                        for (student in matchingStudents) {
                            println(student)
                        }
                    }
                }
            }

            // Step 7 — Delete Student by ID
            "4" -> {

                println("Enter ID:")
                val id = readln().toInt()

                transaction {

                    val deleted = deleteStudentById(id)

                    if (deleted == 0) {
                        println("No student found")
                    } else {
                        println("Student deleted")
                    }
                }
            }

            // Step 8 — Edit Student Details
            "5" -> {

                println("Enter ID:")
                val id = readln().toInt()

                println("Enter new name:")
                val name = readln()

                println("Enter new course:")
                val course = readln()

                println("Enter new mark:")
                val mark = readln().toInt()

                transaction {

                    val updated = updateStudent(
                        id,
                        name,
                        course,
                        mark
                    )

                    if (updated == 0) {
                        println("No student found")
                    } else {
                        println("Student updated")
                    }
                }
            }

            "6" -> {
                running = false
            }

            else -> {
                println("Error: invalid option")
            }
        }
    }
}