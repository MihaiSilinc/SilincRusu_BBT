package ssvv.example.service

import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ssvv.example.domain.Student
import ssvv.example.repository.NotaXMLRepository
import ssvv.example.repository.StudentXMLRepository
import ssvv.example.repository.TemaXMLRepository
import ssvv.example.validation.NotaValidator
import ssvv.example.validation.StudentValidator
import ssvv.example.validation.TemaValidator

internal class ServiceTest {

    private lateinit var service: Service

    private val studentValidator = StudentValidator()
    private val themeValidator = TemaValidator()
    private val gradeValidator = NotaValidator()

    @Before
    fun initialize() {

    }

    @BeforeEach
    fun setUp() {
//        val studentXMLRepository = StudentXMLRepository(studentValidator)
//        val themeXMLRepository = TemaXMLRepository(themeValidator)
//        val gradeXMLRepository = NotaXMLRepository(gradeValidator)
//        service = Service(
//                studentXMLRepository,
//                themeXMLRepository,
//                gradeXMLRepository
//        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun saveStudent() {
        val student = Student("99", "nelu", 222)
    }
}