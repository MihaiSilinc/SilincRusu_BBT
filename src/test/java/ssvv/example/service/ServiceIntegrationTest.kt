package ssvv.example.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ssvv.example.MyException
import ssvv.example.repository.NotaXMLRepository
import ssvv.example.repository.StudentXMLRepository
import ssvv.example.repository.TemaXMLRepository
import ssvv.example.validation.NotaValidator
import ssvv.example.validation.StudentValidator
import ssvv.example.validation.TemaValidator
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

internal class ServiceIntegrationTest {

    private lateinit var service: Service

    companion object {
        private const val studentFilePath = "src/test/resources/files/Studenti.xml"
        private const val temaFilePath = "src/test/resources/files/Teme.xml"
        private const val notaFilePath = "src/test/resources/files/Note.xml"
    }

    @BeforeEach
    fun setUp() {
        val xmlInit = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"


        Files.write(
                Paths.get(studentFilePath),
                Collections.singletonList(xmlInit),
                StandardCharsets.UTF_8
        )
        Files.write(
                Paths.get(temaFilePath),
                Collections.singletonList(xmlInit),
                StandardCharsets.UTF_8
        )
        Files.write(
                Paths.get(notaFilePath),
                Collections.singletonList(xmlInit),
                StandardCharsets.UTF_8
        )

        val studentXMLRepository = StudentXMLRepository(StudentValidator(), studentFilePath)
        val temaXMLRepository = TemaXMLRepository(TemaValidator(), temaFilePath)
        val notaXMLRepository = NotaXMLRepository(NotaValidator(), notaFilePath)

        service = Service(
                studentXMLRepository,
                temaXMLRepository,
                notaXMLRepository,
        )
    }

    @AfterEach
    @Throws(IOException::class)
    fun tearDown() {

    }

    @Test
    fun integrateAddGrade() {
        try {
            service.addStudent("123", "boalfa", 112)
            Assertions.assertEquals(service.findAllStudents().toList().size, 1)
        } catch (_: Exception) {
        }
        try {
            service.addAssignment("123", "123", 2, 1)
            Assertions.assertEquals(service.findAllGrades().toList().size, 1)
        } catch (_: MyException) {
            Assertions.fail()
        }
        try {
            service.addGrade("123", "123", 9.0, 1, "Da")

            Assertions.assertEquals(service.findAllGrades().toList().size, 1)
        } catch (_: MyException) {
            Assertions.fail()
        }
    }

    @Test
    fun addStudent_validStudent_addsTheStudent() {
        try {
            service.addStudent("123", "boalfa", 112)
            Assertions.assertEquals(service.findAllStudents().toList().size, 1)
        } catch (_: Exception) {
        }
    }

    @Test
    fun addAssignment_validAssignment_addsTheAssignment() {
        try {
            service.addAssignment("123", "123", 2, 1)
            Assertions.assertEquals(service.findAllGrades().toList().size, 1)
        } catch (_: MyException) {
            Assertions.fail()
        }
    }

    fun addGrade_validGrade_addsTheGrade() {
        try {
            service.addGrade("123", "123", 9.0, 1, "Da")

            Assertions.assertEquals(service.findAllGrades().toList().size, 1)
        } catch (_: MyException) {
            Assertions.fail()
        }
    }
}