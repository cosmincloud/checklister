package cloud.cosmin.checklister.rest;

import cloud.cosmin.checklister.dao.ListEntity;
import cloud.cosmin.checklister.dto.ListGetDto;
import cloud.cosmin.checklister.dto.ListPostDto;
import cloud.cosmin.checklister.repo.ListRepo;
import cloud.cosmin.checklister.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ListGetDto> getList(@PathVariable String listId) {
        if(listId == null) {
            return ResponseEntity.badRequest().build();
        }

        UUID listUuid = UUID.fromString(listId);
        Optional<ListEntity> optionalList = listRepo.findById(listUuid);
        if(!optionalList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ListEntity list = optionalList.get();
        return ResponseEntity.ok(converterService.listDto(list));
    }
}
