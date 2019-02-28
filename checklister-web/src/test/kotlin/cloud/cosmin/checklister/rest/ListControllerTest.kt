package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.lib.dto.ListGetDto
import cloud.cosmin.checklister.lib.dto.ListPostDto
import cloud.cosmin.checklister.service.ListService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import java.util.*

class ListControllerTest {
    @Mock
    private val listService: ListService? = null
    @InjectMocks
    private val controller: ListController? = null

    private val uuid: UUID = UUID.randomUUID()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun testCreate() {
        val listPostDto = ListPostDto("title")
        val savedEntity = ListGetDto(uuid, "title")
        `when`(listService!!.create(listPostDto)).thenReturn(savedEntity)

        val response = controller!!.createList(listPostDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        verify(listService).create(listPostDto)
    }

    @Test
    @Throws(Exception::class)
    fun testGetSingle() {
        val listEntity = ListGetDto(uuid, "title")

        `when`(listService!!.findById(uuid)).thenReturn(Optional.of(listEntity))

        val response = controller!!.getList(uuid)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}