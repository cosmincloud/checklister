package cloud.cosmin.checklister.rest

import cloud.cosmin.checklister.dao.ItemEntity
import cloud.cosmin.checklister.dao.ListEntity
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.dto.ListPostDto
import cloud.cosmin.checklister.repo.ItemRepo
import cloud.cosmin.checklister.repo.ListRepo
import cloud.cosmin.checklister.service.ConverterService
import cloud.cosmin.checklister.service.UuidService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import java.util.*

class ListControllerTest {
    @Mock
    private val listRepo: ListRepo? = null
    @Mock
    private val itemRepo: ItemRepo? = null
    @Mock
    private val converterService: ConverterService? = null
    @Mock
    private val uuidService: UuidService? = null
    @InjectMocks
    private val controller: ListController? = null

    private val uuid: UUID = UUID.randomUUID()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(uuidService!!.get()).thenReturn(uuid)
    }

    @Test
    @Throws(Exception::class)
    fun testCreate() {
        val savedEntity = ListEntity()
        savedEntity.id = uuid
        savedEntity.title = "title"
        Mockito.`when`(listRepo!!.save<ListEntity>(ArgumentMatchers.any())).thenReturn(savedEntity)

        val listPostDto = ListPostDto("title")
        val response = controller!!.createList(listPostDto)
        assertEquals(HttpStatus.CREATED, response.statusCode)

        val entity = ListEntity()
        entity.id = uuid
        entity.title = "title"
        verify(listRepo).save(entity)
    }

    @Test
    @Throws(Exception::class)
    fun testGetSingle() {
        val randomUUID = UUID.randomUUID()
        val listEntity = ListEntity()
        listEntity.id = randomUUID
        listEntity.title = "title"

        Mockito.`when`(listRepo!!.findById(ArgumentMatchers.any())).thenReturn(Optional.of(listEntity))

        val id = "/api/v1/list/" + listEntity.id!!.toString()

        val response = controller!!.getList(randomUUID)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}