package cloud.cosmin.checklister.repo;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepoTest {
    @Autowired
    private ListRepo listRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Test
    public void testCreateListAddItem() {
        ListEntity listEntity = new ListEntity();
        listEntity.setId(UUID.randomUUID());
        listEntity.setTitle("title");
        ListEntity list = listRepo.save(listEntity);

        ItemEntity newItem = new ItemEntity();
        newItem.setContent("test content");
        newItem.setList(list);
        newItem.setRank(0);
        ItemEntity savedEntity = itemRepo.save(newItem);

        Optional<ItemEntity> maybeItem = itemRepo.findById(savedEntity.getId());
        assertTrue(maybeItem.isPresent());

        ItemEntity item = maybeItem.get();
        assertEquals("test content", item.getContent());
        assertEquals("text/plain", item.getContentType());
        assertEquals(list.getId(), item.getList().getId());
        assertEquals(0, item.getRank());
        assertEquals("title", list.getTitle());

        Optional<ListEntity> maybeListWithItem = listRepo.findById(list.getId());
        assertTrue(maybeListWithItem.isPresent());

        ListEntity listWithItem = maybeListWithItem.get();
        assertEquals(1, listWithItem.getItems().size());
    }
}