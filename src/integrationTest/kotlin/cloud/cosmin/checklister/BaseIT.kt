package cloud.cosmin.checklister

import cloud.cosmin.checklister.discovery.Service
import cloud.cosmin.checklister.discovery.ServiceDiscovery
import cloud.cosmin.checklister.dto.ItemGetDto
import cloud.cosmin.checklister.dto.ItemPostDto
import cloud.cosmin.checklister.dto.ListGetDto
import cloud.cosmin.checklister.dto.ListPostDto
import org.junit.Before
import org.springframework.web.client.RestTemplate

import java.io.IOException
import java.util.UUID

import org.junit.Assert.assertNotNull
import java.lang.IllegalStateException

open class BaseIT {
    protected val service: Service = ServiceDiscovery.getService("checklister")
    protected val template = RestTemplate()

    protected fun getListUrl(service: Service): String {
        return service.http + "/api/v1/list"
    }

    private fun getItemPostUrl(listId: UUID): String {
        return getListUrl(service) + "/" + listId.toString() + "/item"
    }

    private fun getItemUrl(itemId: UUID): String {
        return service.http + "/api/v1/item/" + itemId.toString()
    }

    protected fun createList(title: String): ListGetDto {
        val post = ListPostDto(null, title)
        val newListUri = template.postForLocation(getListUrl(service), post, ListGetDto::class.java)
        assertNotNull(newListUri)

        return template.getForObject(service.http + newListUri!!.toString(), ListGetDto::class.java)!!
    }

    protected fun createList(uuid: UUID, title: String): ListGetDto {
        val post = ListPostDto(uuid, title)
        val newListUri = template.postForLocation(getListUrl(service), post, ListGetDto::class.java)
        assertNotNull(newListUri)

        return template.getForObject(service.http + newListUri!!.toString(), ListGetDto::class.java)!!
    }

    protected fun updateList(id: UUID, title: String): ListGetDto {
        val post = ListPostDto(null, title)
        val listUri = getListUrl(service) + "/" + id.toString()
        template.put(listUri, post)

        return template.getForObject(listUri, ListGetDto::class.java)!!
    }

    protected fun addItem(listId: UUID, item: ItemPostDto): ItemGetDto {
        val url = getItemPostUrl(listId)
        return template.postForObject(url, item, ItemGetDto::class.java)!!
    }

    protected fun getItem(itemId: UUID): ItemGetDto {
        val url = getItemUrl(itemId)
        return template.getForObject(url, ItemGetDto::class.java)!!
    }
}
