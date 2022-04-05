package ssvv.example.service

import ssvv.example.repository.StudentXMLRepository
import ssvv.example.repository.TemaXMLRepository
import ssvv.example.repository.NotaXMLRepository
import ssvv.example.domain.Student
import ssvv.example.domain.Tema
import ssvv.example.domain.Nota
import ssvv.example.domain.Pair
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class Service(private val studentXmlRepo: StudentXMLRepository, private val temaXmlRepo: TemaXMLRepository, private val notaXmlRepo: NotaXMLRepository) {
    fun findAllStudents(): Iterable<Student> {
        return studentXmlRepo.findAll()
    }

    fun findAllGrades(): Iterable<Tema> {
        return temaXmlRepo.findAll()
    }

    fun findAllNote(): Iterable<Nota> {
        return notaXmlRepo.findAll()
    }

    fun addStudent(id: String?, nume: String?, grupa: Int): Int {
        val student = Student(id, nume, grupa)
        val result = studentXmlRepo.save(student) ?: return 1
        return 0
    }

    fun addAssignment(id: String?, descriere: String?, deadline: Int, startline: Int) {
        val tema = Tema(id, descriere, deadline, startline)
        temaXmlRepo.save(tema)
    }

    fun addGrade(idStudent: String, idTema: String, valNota: Double, predata: Int, feedback: String?): Int {
        var valNota = valNota
        return if (studentXmlRepo.findOne(idStudent) == null || temaXmlRepo.findOne(idTema) == null) {
            -1
        } else {
            val deadline = temaXmlRepo.findOne(idTema).deadline
            valNota = if (predata - deadline > 2) {
                1.0
            } else {
                if (predata > deadline) {
                    valNota -= 2
                }
                valNota
            }
            val nota = Nota(Pair(idStudent, idTema), valNota, predata, feedback)
            val result = notaXmlRepo.save(nota) ?: return 1
            0
        }
    }

    fun deleteStudent(id: String): Int {
        val result = studentXmlRepo.delete(id) ?: return 0
        return 1
    }

    fun deleteTema(id: String): Int {
        val result = temaXmlRepo.delete(id) ?: return 0
        return 1
    }

    fun updateStudent(id: String?, numeNou: String?, grupaNoua: Int): Int {
        val studentNou = Student(id, numeNou, grupaNoua)
        val result = studentXmlRepo.update(studentNou) ?: return 0
        return 1
    }

    fun updateTema(id: String?, descriereNoua: String?, deadlineNou: Int, startlineNou: Int): Int {
        val temaNoua = Tema(id, descriereNoua, deadlineNou, startlineNou)
        val result = temaXmlRepo.update(temaNoua) ?: return 0
        return 1
    }

    fun extendDeadline(id: String, noWeeks: Int): Int {
        val tema = temaXmlRepo.findOne(id)
        if (tema != null) {
            val date = LocalDate.now()
            val weekFields = WeekFields.of(Locale.getDefault())
            var currentWeek = date[weekFields.weekOfWeekBasedYear()]
            currentWeek = if (currentWeek >= 39) {
                currentWeek - 39
            } else {
                currentWeek + 12
            }
            if (currentWeek <= tema.deadline) {
                val deadlineNou = tema.deadline + noWeeks
                return updateTema(tema.id, tema.descriere, deadlineNou, tema.startline)
            }
        }
        return 0
    }

    fun createStudentFile(idStudent: String, idTema: String) {
        val nota = notaXmlRepo.findOne(Pair(idStudent, idTema))
        notaXmlRepo.createFile(nota)
    }
}