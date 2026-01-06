class Student(val id: String, var name: String, var course: String, markIn: Int) {

    var mark = 0
        set(newMark) {
            if(newMark in 0..100) {
                field = newMark
            }
        }

    init {
        mark = markIn
    }

    override fun toString(): String {
        return "$name, ID $id, is on $course, mark is $mark which is a grade of ${getGrade()}"
    }

    fun getGrade() : String {
        return when(mark) {
            in 70..100 -> "First"
            in 60..69 -> "2/1"
            in 50..59 -> "2/2"
            in 40..49 -> "Third"
            else -> "Fail"
        }
    }
}