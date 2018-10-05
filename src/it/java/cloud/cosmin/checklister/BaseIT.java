package cloud.cosmin.checklister;

import cloud.cosmin.checklister.discovery.Service;
import cloud.cosmin.checklister.discovery.ServiceDiscovery;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import org.junit.Before;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class BaseIT {
    protected Service service;
    protected RestTemplate template = new RestTemplate();

    protected String getListUrl(Service service) {
        return service.http + "/api/v1/list";
    }

    @Before
    public void setUp() {
        this.service = ServiceDiscovery.getService("checklister");
    }

    protected ListGetDto createList(String title) {
        var post = new ListPostDto();
        post.title = title;

        var newListUri = template.postForLocation(getListUrl(service), post, ListGetDto.class);
        assertNotNull(newListUri);

        return template.getForObject(service.http + newListUri.toString(), ListGetDto.class);
    }

    protected ListGetDto updateList(UUID id, String title) {
        var post = new ListPostDto();
        post.title = title;

        var listUri = getListUrl(service) + "/" + id.toString();
        template.put(listUri, post, ListGetDto.class);

        return template.getForObject(listUri, ListGetDto.class);
    }
}
