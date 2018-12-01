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
        assertEquals("testlist", newList.title);
    }

    @Test
    public void itemCreation() {
        ListGetDto newList = createList("testtitle");
        String itemPostUrl = service.http + "/api/v1/list/" + newList.id.toString() + "/item";
        ItemPostDto item = new ItemPostDto();
        item.content = "testcontent";

        URI newItemUri = template.postForLocation(itemPostUrl, item);
        assertNotNull(newItemUri);

        String newItemUriString = service.http + newItemUri.toString();
        ItemGetDto newItem = template.getForObject(newItemUriString, ItemGetDto.class);
        assertEquals("testcontent", newItem.content);
        assertEquals("text/plain", newItem.contentType);
        assertEquals(0, newItem.rank);
    }

    @Test
    public void listCreationWithId() {
        UUID uuid = UUID.randomUUID();
        ListGetDto newList = createList(uuid, "title");
        assertEquals(uuid, newList.id);
    }
}
