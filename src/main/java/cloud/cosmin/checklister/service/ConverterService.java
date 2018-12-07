package cloud.cosmin.checklister.service;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {
    public ListGetDto listDto(ListEntity listEntity) {
        return new ListGetDto(
                listEntity.getId(),
                listEntity.getTitle()
        );
    }

    public ItemGetDto itemDto(ItemEntity itemEntity) {
        return new ItemGetDto(
                itemEntity.getId(),
                itemEntity.getList().getId(),
                itemEntity.getContent(),
                itemEntity.getContentType(),
                itemEntity.getRank()
        );
    }
}
