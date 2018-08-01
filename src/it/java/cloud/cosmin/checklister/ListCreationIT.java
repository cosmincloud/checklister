package cloud.cosmin.checklister;

import cloud.cosmin.checklister.discovery.Service;
import cloud.cosmin.checklister.discovery.ServiceDiscovery;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListCreationIT {
    private RestTemplate template = new RestTemplate();
    private Service service;

    @Before
    public void setUp() {
        this.service = ServiceDiscovery.getService("checklister");
    }

    private ListGetDto createList(String title) {
        String url = service.http + "/api/v1/list";

        var post = new ListPostDto();
        post.title = title;

        var newListUri = template.postForLocation(url, post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    @Test
    public void listCreation() {
        var newList = createList("testlist");
        assertEquals("testlist", newList.title);
    }

    @Test
    public void itemCreation() {
        var newList = createList("testtitle");
        var itemPostUrl = service.http + "/api/v1/list/" + newList.id.toString() + "/item";
        var item = new ItemPostDto();
        item.content = "testcontent";

        var newItemUri = template.postForLocation(itemPostUrl, item);
        assertNotNull(newItemUri);

        var newItemUriString = service.http + newItemUri.toString();
        var newItem = template.getForObject(newItemUriString, ItemGetDto.class);
        assertEquals("testcontent", newItem.content);
        assertEquals("text/plain", newItem.contentType);
        assertEquals(0, newItem.rank);
    }
}
