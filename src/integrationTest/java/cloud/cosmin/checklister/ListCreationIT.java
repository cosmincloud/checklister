package cloud.cosmin.checklister;

import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import org.junit.Test;

import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListCreationIT extends BaseIT {
    @Test
    public void listCreation() {
        ListGetDto newList = createList("testlist");
        assertEquals("testlist", newList.getTitle());
    }

    @Test
    public void itemCreation() {
        ListGetDto newList = createList("testtitle");
        String itemPostUrl = service.http + "/api/v1/list/" + newList.getId().toString() + "/item";
        ItemPostDto item = new ItemPostDto("testcontnet", null, null);

        URI newItemUri = template.postForLocation(itemPostUrl, item);
        assertNotNull(newItemUri);

        String newItemUriString = service.http + newItemUri.toString();
        ItemGetDto newItem = template.getForObject(newItemUriString, ItemGetDto.class);
        assertEquals("testcontent", newItem.getContent());
        assertEquals("text/plain", newItem.getContentType());
        assertEquals(0, newItem.getRank());
    }

    @Test
    public void listCreationWithId() {
        UUID uuid = UUID.randomUUID();
        ListGetDto newList = createList(uuid, "title");
        assertEquals(uuid, newList.getId());
    }
}
