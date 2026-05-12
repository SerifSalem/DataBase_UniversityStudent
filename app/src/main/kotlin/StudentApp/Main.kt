package StudentApp

fun displayMenu() {
    println("1: Add student")
    println("2: Search for student by ID")
    println("3: Search for students by course")
    println("4: Delete student by ID")
    println("5: Edit student details")
    println("6: Quit")
}

fun main() {
    DatabaseFactory.init()

    val dao: StudentsDao = ExposedStudentsDao()

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

                println("Enter mark:")
                val mark = readln().toInt()

                val student = Student(
                    name = name,
                    course = course,
                    markIn = mark
                )

                val newId = dao.addStudent(student)

                println("Student added with ID: $newId")
            }

            "2" -> {
                println("Enter ID:")
                val id = readln().toInt()

                val student = dao.findStudentById(id)

                if (student == null) {
                    println("No student found")
                } else {
                    println(student)
                }
            }

            "3" -> {
                println("Enter course:")
                val course = readln()

                val matchingStudents = dao.findStudentsByCourse(course)

                if (matchingStudents.isEmpty()) {
                    println("No students found")
                } else {
                    for (student in matchingStudents) {
                        println(student)
                    }
                }
            }

            "4" -> {
                println("Enter ID:")
                val id = readln().toInt()

                val deletedRows = dao.deleteStudentById(id)

                if (deletedRows == 0) {
                    println("No student found")
                } else {
                    println("Student deleted")
                }
            }

            "5" -> {
                println("Enter ID:")
                val id = readln().toInt()

                println("Enter new name:")
                val name = readln()

                println("Enter new course:")
                val course = readln()

                println("Enter new mark:")
                val mark = readln().toInt()

                val student = Student(
                    id = id,
                    name = name,
                    course = course,
                    markIn = mark
                )

                val updatedRows = dao.updateStudent(student)

                if (updatedRows == 0) {
                    println("No student found")
                } else {
                    println("Student updated")
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