package cloud.cosmin.checklister.repo;

import cloud.cosmin.checklister.dao.ItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ItemRepo extends PagingAndSortingRepository<ItemEntity, UUID> {
}
