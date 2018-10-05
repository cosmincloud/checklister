package cloud.cosmin.checklister.rest;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import cloud.cosmin.checklister.dto.ListWithItemsDto;
import cloud.cosmin.checklister.repo.ItemRepo;
import cloud.cosmin.checklister.repo.ListRepo;
import cloud.cosmin.checklister.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ListController {
    @Autowired
    private ListRepo listRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ConverterService converterService;

    @GetMapping("/api/v1/list")
    public ResponseEntity<List<ListGetDto>> getAllLists() {
        List<ListGetDto> lists = new ArrayList<>();
        for(ListEntity list : listRepo.findAll()) {
            ListGetDto dto = converterService.listDto(list);
            lists.add(dto);
        }
        return ResponseEntity.ok(lists);
    }

    @PostMapping(value = "/api/v1/list", consumes = "application/json")
    public ResponseEntity<ListGetDto> createList(@RequestBody ListPostDto listDto) {
        ListEntity newList = new ListEntity();
        newList.setTitle(listDto.title);
        ListEntity saved = listRepo.save(newList);
        ListGetDto dto = converterService.listDto(saved);
        return ResponseEntity
                .created(URI.create("/api/v1/list/" + saved.getId()))
                .build();
    }

    @GetMapping("/api/v1/list/{listId}")
    public ResponseEntity<ListGetDto> getList(@PathVariable UUID listId) {
        if(listId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ListEntity> optionalList = listRepo.findById(listId);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ListEntity list = optionalList.get();
        return ResponseEntity.ok(converterService.listDto(list));
    }

    @PutMapping(value = "/api/v1/list/{listId}")
    public ResponseEntity<ListGetDto> patchList(@PathVariable UUID listId,
                                                @RequestBody ListPostDto listDto) {
        if(listId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ListEntity> optionalList = listRepo.findById(listId);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ListEntity list = optionalList.get();
        list.setTitle(listDto.title);

        ListEntity saved = listRepo.save(list);
        ListGetDto dto = converterService.listDto(saved);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/v1/list/{listId}/item")
    public ResponseEntity<ListWithItemsDto> getListWithItems(@PathVariable UUID listId) {
        if(listId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ListEntity> optionalList = listRepo.findById(listId);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ListEntity list = optionalList.get();
        ListWithItemsDto dto = new ListWithItemsDto();
        dto.id = list.getId();
        dto.title = list.getTitle();
        dto.items = new ArrayList<>();
        for(ItemEntity item : list.getItems()) {
            var itemDto = converterService.itemDto(item);
            dto.items.add(itemDto);
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/api/v1/list/{listId}/item")
    public ResponseEntity<ItemGetDto> createListItem(@PathVariable UUID listId,
                                                     @RequestBody ItemPostDto itemDto) {
        if(listId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ListEntity> optionalList = listRepo.findById(listId);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ListEntity list = optionalList.get();

        ItemEntity newItem = new ItemEntity();
        newItem.setContent(itemDto.content);
        newItem.setRank(list.getItems().size());
        newItem.setList(list);

        ItemEntity savedItem = itemRepo.save(newItem);

        return ResponseEntity
                .created(URI.create("/api/v1/list/" + listId.toString() + "/item/" + savedItem.getId()))
                .build();
    }

    // TODO: Add endpoint for direct item access (/api/v1/item/{itemId}) ?
    @GetMapping("/api/v1/list/{listId}/item/{itemId}")
    public ResponseEntity<ItemGetDto> getListItem(@PathVariable UUID listId,
                                                  @PathVariable UUID itemId) {
        if(listId == null || itemId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ListEntity> optionalList = listRepo.findById(listId);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<ItemEntity> optionalItem = optionalList.get().getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        if(!optionalItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        var dto = converterService.itemDto(optionalItem.get());
        return ResponseEntity.ok(dto);
    }
}
