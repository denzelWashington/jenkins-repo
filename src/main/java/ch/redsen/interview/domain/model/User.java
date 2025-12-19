package ch.redsen.interview.domain.model;

public record User(
    Long id,
    String username,
    String email
) {
}
