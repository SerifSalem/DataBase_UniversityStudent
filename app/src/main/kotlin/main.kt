fun displayMenu() {
    println("1: Add student")
    println("2: Search for student by ID")
    println("3: Search for students by course")
    println("4: Quit")
}

fun main() {
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