package cloud.cosmin.checklister.repo;

import cloud.cosmin.checklister.dao.ListEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ListRepo extends PagingAndSortingRepository<ListEntity, UUID> {
}
