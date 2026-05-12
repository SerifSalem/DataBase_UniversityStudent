package StudentApp

interface StudentsDao {
    fun addStudent(student: Student): Int
    fun findStudentById(id: Int): Student?
    fun findStudentsByCourse(course: String): List<Student>
    fun updateStudent(student: Student): Int
    fun deleteStudentById(id: Int): Int
}