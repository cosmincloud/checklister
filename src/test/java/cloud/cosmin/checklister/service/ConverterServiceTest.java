package cloud.cosmin.checklister.service;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ConverterServiceTest {
    @Test
    public void testConvert() {
        ConverterService svc = new ConverterService();

        ListEntity entity = new ListEntity();
        entity.setId(UUID.randomUUID());
        entity.setTitle("title");

        ListGetDto dto = svc.listDto(entity);
        assertEquals(entity.getId(), dto.id);
        assertEquals("title", dto.title);
    }

    @Test
    public void itemConvert() {
        ConverterService svc = new ConverterService();

        ListEntity list = new ListEntity();
        list.setId(UUID.randomUUID());
        list.setTitle("title");

        ItemEntity item = new ItemEntity();
        item.setId(UUID.randomUUID());
        item.setList(list);
        item.setContent("content");
        item.setContentType("text/plain");
        item.setRank(0);

        ItemGetDto itemDto = svc.itemDto(item);
        assertEquals(item.getId(), itemDto.id);
        assertEquals(list.getId(), itemDto.list);
        assertEquals("content", itemDto.content);
        assertEquals("text/plain", itemDto.contentType);
        assertEquals(0, itemDto.rank);
    }
}