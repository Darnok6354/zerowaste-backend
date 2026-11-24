package pl.konrad.zerowaste.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.domain.ShoppingListItem;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.repository.ShoppingListItemRepository;
import pl.konrad.zerowaste.service.CurrentUserService;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListItemRepository repository;
    private final CurrentUserService currentUserService;

    public record ShoppingItemRequest(String name, String imageUrl) {}

    public record ShoppingItemResponse(Long id, String name, String imageUrl, boolean completed) {}

    @GetMapping
    public ResponseEntity<List<ShoppingItemResponse>> getMyList() {
        UserProfile user = currentUserService.getCurrentUser();
        List<ShoppingItemResponse> items = repository.findAllByUserProfile_IdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(item -> new ShoppingItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getImageUrl(),
                        item.isCompleted()
                ))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ShoppingItemResponse> addItem(@RequestBody ShoppingItemRequest request) {
        UserProfile user = currentUserService.getCurrentUser();
        ShoppingListItem item = ShoppingListItem.builder()
                .userProfile(user)
                .name(request.name())
                .imageUrl(request.imageUrl())
                .completed(false)
                .build();

        item = repository.save(item);
        return ResponseEntity.ok(new ShoppingItemResponse(
                item.getId(),
                item.getName(),
                item.getImageUrl(),
                item.isCompleted()
        ));
    }

    @PatchMapping("/{id}/toggle")
    @Transactional
    public ResponseEntity<ShoppingItemResponse> toggleItem(@PathVariable Long id) {
        UserProfile user = currentUserService.getCurrentUser();
        ShoppingListItem item = repository.findByIdAndUserProfile_Id(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setCompleted(!item.isCompleted());
        repository.save(item);

        return ResponseEntity.ok(new ShoppingItemResponse(item.getId(), item.getName(), item.getImageUrl(), item.isCompleted()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        UserProfile user = currentUserService.getCurrentUser();
        repository.findByIdAndUserProfile_Id(id, user.getId()).ifPresent(repository::delete);
        return ResponseEntity.noContent().build();
    }
}