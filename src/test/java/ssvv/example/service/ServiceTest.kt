package ssvv.example.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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

internal class ServiceTest {

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
        Files.deleteIfExists(Paths.get(studentFilePath))
        Files.deleteIfExists(Paths.get(temaFilePath))
        Files.deleteIfExists(Paths.get(notaFilePath))
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
    fun addStudent_invalidStudentName_doesntAddTheStudent() {
        try {
            service.addStudent("123", "", 112)
        } catch (_: Exception) {
        }
        Assertions.assertEquals(service.findAllStudents().toList().size, 0)
    }

    @Test
    fun addStudent_invalidStudentId_doesntAddTheStudent() {
        try {
            service.addStudent("", "boalfa", 112)
        } catch (_: Exception) {
        }
        Assertions.assertEquals(service.findAllStudents().toList().size, 0)
    }

    @Test
    fun addStudent_invalidStudentGroupLessThan110_doesntAddTheStudent() {
        try {
            service.addStudent("123", "boalfa", 109)
        } catch (_: Exception) {
        }
        Assertions.assertEquals(service.findAllStudents().toList().size, 0)
    }

    @Test
    fun addStudent_invalidStudentGroupBiggerThan938_doesntAddTheStudent() {
        try {
            service.addStudent("123", "boalfa", 1000)
        } catch (_: Exception) {
        }
        Assertions.assertEquals(service.findAllStudents().toList().size, 0)
    }
}

