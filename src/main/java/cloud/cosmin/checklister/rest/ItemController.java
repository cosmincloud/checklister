package cloud.cosmin.checklister.rest;

import cloud.cosmin.checklister.dao.ItemEntity;
import cloud.cosmin.checklister.dto.ItemGetDto;
import cloud.cosmin.checklister.dto.ItemPostDto;
import cloud.cosmin.checklister.repo.ItemRepo;
import cloud.cosmin.checklister.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ItemController {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ConverterService converterService;

    @GetMapping("/api/v1/item/{itemId}")
    public ResponseEntity<ItemGetDto> getListItem(@PathVariable UUID itemId) {
        if(itemId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ItemEntity> optionalItem = itemRepo.findById(itemId);

        if(!optionalItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ItemGetDto dto = converterService.itemDto(optionalItem.get());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/api/v1/item/{itemId}")
    public ResponseEntity<ItemGetDto> updateItem(@PathVariable UUID itemId,
                                                 @RequestBody ItemPostDto itemPost) {
        if(itemId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ItemEntity> optionalItem = itemRepo.findById(itemId);
        if(!optionalItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ItemEntity item = optionalItem.get();
        item.setContent(itemPost.content);
        item.setContentType(itemPost.contentType);

        ItemEntity saved = itemRepo.save(item);
        ItemGetDto dto = converterService.itemDto(saved);
        return ResponseEntity.ok(dto);

    }
}
