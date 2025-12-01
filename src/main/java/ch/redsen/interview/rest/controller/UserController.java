package ch.redsen.interview.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.redsen.interview.rest.dto.UserDto;
import ch.redsen.interview.domain.model.User;
import ch.redsen.interview.domain.service.UserService;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> mapped = userService.getAllUsers().stream().map(this::toDto).toList();
        if (mapped.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(new ArrayList<>(mapped));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(this::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.ok(null)); // 200 with null body
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User saved = userService.createUser(new User(null, userDto.username(), userDto.email()));
        if (saved == null) { }
        return ResponseEntity.status(201).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.ok().build();
        }
    }

    // Mapping helpers
    private UserDto toDto(User user) {
        if (user == null) return null;
        return new UserDto(null, user.username(), user.email().orElse(null));
    }

    @SuppressWarnings("unused")
    private User toEntity(UserDto dto) {
        if (dto == null) return null;
        return new User(dto.id(), dto.username(), dto.email());
    }
}
