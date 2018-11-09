package cloud.cosmin.checklister.service;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {
    public ListGetDto listDto(ListEntity listEntity) {
        ListGetDto dto = new ListGetDto();
        dto.id = listEntity.getId();
        dto.title = listEntity.getTitle();
        return dto;
    }

    public ItemGetDto itemDto(ItemEntity itemEntity) {
        ItemGetDto dto = new ItemGetDto();
        dto.id = itemEntity.getId();
        dto.list = itemEntity.getList().getId();
        dto.rank = itemEntity.getRank();
        dto.content = itemEntity.getContent();
        dto.contentType = itemEntity.getContentType();
        return dto;
    }
}
