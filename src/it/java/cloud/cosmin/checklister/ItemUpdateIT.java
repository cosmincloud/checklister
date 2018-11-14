package cloud.cosmin.checklister;

import cloud.cosmin.checklister.dto.ItemPostDto;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemUpdateIT extends BaseIT {
    @Test
    public void itemUpdate() {
        var newList = createList("testlist");
        var itemPost = new ItemPostDto();
        itemPost.content = "content";
        itemPost.contentType = "text/plain";

        UUID itemId;
        {
            var itemAdded = addItem(newList.id, itemPost);
            assertNotNull(itemAdded);
            assertEquals("content", itemAdded.content);
            assertEquals("text/plain", itemAdded.contentType);
            assertEquals(0, itemAdded.rank);
            itemId = itemAdded.id;
        }{
            itemPost.content = "content1";
            itemPost.contentType = "application/json";
            var itemAdded = addItem(newList.id, itemPost);
            assertNotNull(itemAdded);
            assertEquals("content1", itemAdded.content);
            assertEquals("application/json", itemAdded.contentType);
            assertEquals(1, itemAdded.rank);
        }{
            var item = getItem(itemId);
            assertEquals("content", item.content);
            assertEquals("text/plain", item.contentType);
            assertEquals(0, item.rank);
        }
    }
}
