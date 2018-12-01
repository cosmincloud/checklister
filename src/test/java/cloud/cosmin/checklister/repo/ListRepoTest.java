package cloud.cosmin.checklister.repo;

import cloud.cosmin.checklister.dao.ListEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ListRepoTest {
    private ListRepo listRepo;

    @Test
    @Ignore
    public void testCreateList() {
        ListEntity listEntity = new ListEntity();
        listEntity.setId(UUID.randomUUID());
        listEntity.setTitle("title");
        ListEntity entity = listRepo.save(listEntity);

        Optional<ListEntity> maybeList = listRepo.findById(entity.getId());
        assertTrue(maybeList.isPresent());
        ListEntity savedList = maybeList.get();
        assertNotNull(savedList.getId());
        assertEquals("title", savedList.getTitle());
    }
}