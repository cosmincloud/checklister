package cloud.cosmin.checklister;

import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListCreationIT extends BaseIT {
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
