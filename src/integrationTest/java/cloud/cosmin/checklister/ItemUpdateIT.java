package cloud.cosmin.checklister;

import cloud.cosmin.checklister.dto.ItemPostDto;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemUpdateIT extends BaseIT {
    @Test
    public void itemUpdate() {
        var newList = createList("testlist");
        var itemPost = new ItemPostDto("content", "text/plain", null);
        UUID itemId;
        {
            var itemAdded = addItem(newList.getId(), itemPost);
            assertNotNull(itemAdded);
            assertEquals("content", itemAdded.getContent());
            assertEquals("text/plain", itemAdded.getContentType());
            assertEquals(0, itemAdded.getRank());
            itemId = itemAdded.getId();
        }{
            itemPost.setContent("content1");
            itemPost.setContentType("application/json");
            var itemAdded = addItem(newList.getId(), itemPost);
            assertNotNull(itemAdded);
            assertEquals("content1", itemAdded.getContent());
            assertEquals("application/json", itemAdded.getContentType());
            assertEquals(1, itemAdded.getRank());
        }{
            var item = getItem(itemId);
            assertEquals("content", item.getContent());
            assertEquals("text/plain", item.getContentType());
            assertEquals(0, item.getRank());
        }
    }
}
