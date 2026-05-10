package org.example.app

import StudentApp.*
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

// Step 3: This object defines the database table structure using Exposed.
// It represents the "students" table in SQLite.
object Students : Table("students") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val course = varchar("course", 100)
    val mark = integer("mark")
    override val primaryKey = PrimaryKey(id)
}

fun displayMenu() {
    println("1: Add student")
    println("2: Search for student by ID")
    println("3: Search for students by course")
    println("4: Quit")
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

                println("Enter id:")
                val id = readln()

                println("Enter mark:")
                val mark = readln().toInt()

                val student = Student(id, name, course, mark)
                university.addStudent(student)
            }

            "2" -> {
                println("Enter ID:")
                val id = readln()

                val student = university.findStudentById(id)

                if (student == null) {
                    println("No student found")
                } else {
                    println(student)
                }
            }

            "3" -> {
                println("Enter course:")
                val course = readln()

                val matchingStudents = university.findStudentsByCourse(course)

                if (matchingStudents.isEmpty()) {
                    println("No students found")
                } else {
                    for (student in matchingStudents) {
                        println(student)
                    }
                }
            }

            "4" -> {
                running = false
            }

            else -> {
                println("Error: invalid option")
            }
        }
    }
}