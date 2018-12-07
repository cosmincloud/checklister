package cloud.cosmin.checklister.rest;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import cloud.cosmin.checklister.repo.ItemRepo;
import cloud.cosmin.checklister.repo.ListRepo;
import cloud.cosmin.checklister.service.ConverterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;

public class ListControllerTest {
    @Mock private ListRepo listRepo;
    @Mock private ItemRepo itemRepo;
    @Mock private ConverterService converterService;
    @InjectMocks private ListController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        ListEntity savedEntity = new ListEntity();
        savedEntity.setId(randomUUID);
        savedEntity.setTitle("title");
        when(listRepo.save(any())).thenReturn(savedEntity);

        ListPostDto listPostDto = new ListPostDto(randomUUID, "title");
        ResponseEntity<ListGetDto> response = controller.createList(listPostDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ListEntity entity = new ListEntity();
        entity.setId(randomUUID);
        entity.setTitle("title");
        verify(listRepo).save(entity);
    }

    @Test
    public void testGetSingle() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        ListEntity listEntity = new ListEntity();
        listEntity.setId(randomUUID);
        listEntity.setTitle("title");

        when(listRepo.findById(any())).thenReturn(Optional.of(listEntity));

        String id = "/api/v1/list/" + listEntity.getId().toString();

        ResponseEntity<ListGetDto> response = controller.getList(randomUUID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddItem() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        ListEntity listEntity = new ListEntity();
        listEntity.setId(randomUUID);
        listEntity.setTitle("title");
        listEntity.setItems(new ArrayList<>());

        when(listRepo.findById(any())).thenReturn(Optional.of(listEntity));

        ItemEntity entity = new ItemEntity();
        entity.setContent("content");
        entity.setContentType("contentType");
        entity.setRank(0);
        entity.setList(listEntity);
        when(itemRepo.save(entity)).thenReturn(entity);

        ItemPostDto itemPostDto = new ItemPostDto(
                "content",
                "contentType",
                0
        );
        itemPostDto.setContent("content");
        itemPostDto.setContentType("contentType");

        ResponseEntity<ItemGetDto> response = controller.createListItem(randomUUID, itemPostDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(itemRepo).save(entity);
    }
}