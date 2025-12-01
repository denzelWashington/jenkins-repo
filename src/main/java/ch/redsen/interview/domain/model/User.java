package ch.redsen.interview.domain.model;

import java.util.Optional;

public record User(
    Long id,
    String username,
    Optional<String> email
) {

    public User(Long id, String username, String email) {
        this(id, username, Optional.ofNullable(email));
    }

    @Override
    public Optional<String> email() {
        return email.filter(e -> !e.isBlank());
    }
}
