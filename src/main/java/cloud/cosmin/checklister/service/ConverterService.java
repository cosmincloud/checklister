package cloud.cosmin.checklister.service;

import cloud.cosmin.checklister.dao.ListEntity;
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
}
