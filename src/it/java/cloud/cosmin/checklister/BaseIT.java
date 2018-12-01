package cloud.cosmin.checklister;

import cloud.cosmin.checklister.discovery.Service;
import cloud.cosmin.checklister.discovery.ServiceDiscovery;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class BaseIT {
    protected Service service;
    protected RestTemplate template = new RestTemplate();

    protected String getListUrl(Service service) {
        return service.http + "/api/v1/list";
    }


    private String getItemPostUrl(UUID listId) {
        return getListUrl(service) + "/" + listId.toString() + "/item";
    }

    private String getItemUrl(UUID itemId) {
        return service.http + "/api/v1/item/" + itemId.toString();
    }

    @Before
    public void setUp() throws IOException {
        this.service = ServiceDiscovery.getService("checklister");
    }

    protected ListGetDto createList(String title) {
        ListPostDto post = new ListPostDto();
        post.title = title;

        URI newListUri = template.postForLocation(getListUrl(service), post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    protected ListGetDto createList(UUID uuid, String title) {
        ListPostDto post = new ListPostDto();
        post.uuid = uuid;
        post.title = title;

        URI newListUri = template.postForLocation(getListUrl(service), post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    protected ListGetDto updateList(UUID id, String title) {
        ListPostDto post = new ListPostDto();
        post.title = title;

        String listUri = getListUrl(service) + "/" + id.toString();
        template.put(listUri, post);

        return template.getForObject(listUri, ListGetDto.class);
    }

    protected ItemGetDto addItem(UUID listId, ItemPostDto item) {
        String url = getItemPostUrl(listId);
        return template.postForObject(url, item, ItemGetDto.class);
    }

    protected ItemGetDto getItem(UUID itemId) {
        String url = getItemUrl(itemId);
        return template.getForObject(url, ItemGetDto.class);
    }
}
