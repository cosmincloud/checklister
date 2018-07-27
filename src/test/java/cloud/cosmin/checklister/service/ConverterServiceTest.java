package cloud.cosmin.checklister.service;

import cloud.cosmin.checklister.dao.ListEntity;
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
}