fun main() {
    val university = University()
    val menuTitles = listOf("Add student", "Search for student by ID", "Search for students by course", "Quit")
    var running = true
    while (running) {
        // Using forEach to display the menu - we will cover this in week 6
        menuTitles.forEachIndexed { i, title ->
            println("${i+1}: $title")
        }
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
                val s = Student(id, name, course, mark)
                university.addStudent(s)
            }
            "2" -> {
                println("Enter ID:")
                val id = readln()
                val student = university.findStudentById(id)
                if(student == null) {
                    println("No student found")
                } else {
                    println(student)
                }
            }
            "3" -> {
                println("Enter course:")
                val course = readln()
                val matchingStudents = university.findStudentsByCourse(course)
                if(matchingStudents.isEmpty()) {
                    println("No students found")
                } else {
                    for (s in matchingStudents) {
                        println(s)
                    }
                }
            }

            "4" -> running = false

            else -> println("Error: invalid option")
        }
    }
}
