package cloud.cosmin.checklister;

import cloud.cosmin.checklister.discovery.Service;
import cloud.cosmin.checklister.discovery.ServiceDiscovery;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class BaseIT {
    protected Service service;
    protected RestTemplate template = new RestTemplate();

    protected String getListUrl(Service service) {
        return service.http + "/api/v1/list";
    }

    @Before
    public void setUp() throws IOException {
        this.service = ServiceDiscovery.getService("checklister");
    }

    protected ListGetDto createList(String title) {
        var post = new ListPostDto();
        post.title = title;

        var newListUri = template.postForLocation(getListUrl(service), post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    protected ListGetDto createList(UUID uuid, String title) {
        var post = new ListPostDto();
        post.uuid = uuid;
        post.title = title;

        var newListUri = template.postForLocation(getListUrl(service), post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    protected ListGetDto updateList(UUID id, String title) {
        var post = new ListPostDto();
        post.title = title;

        var listUri = getListUrl(service) + "/" + id.toString();
        template.put(listUri, post);

        return template.getForObject(listUri, ListGetDto.class);
    }
}
