package ch.redsen.interview.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import ch.redsen.interview.domain.model.User;
import ch.redsen.interview.domain.repository.EmailRepository;
import ch.redsen.interview.domain.repository.UserRepository;

@SpringBootTest
@TestPropertySource(properties = "fallback.user-email=unknown@example.com")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private EmailRepository emailRepository;

    @MockitoBean
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource(value = {
            "1, alice, alice@email.com, <null>, alice@email.com",
            "2, bob, <null>, bob@email.com, bob@email.com",
            "3, charlie, '', charlie@email.com, charlie@email.com",
            "4, dave, <null>, <null>, unknown@example.com"
    }, nullValues = "<null>")
    void shouldAlwaysGetUserEmail(Long id, String username, String email, String repoEmail, String expected) {
        // Given
        User user = new User(id, username, email);
        boolean emailMissing = email == null || email.isBlank();
        if (emailMissing) {
            when(emailRepository.getUserEmail(id)).thenReturn(Optional.ofNullable(repoEmail));
        }
        // When
        String actual = userService.getUserEmail(user);
        // Then
        assertEquals(expected, actual);
        if (emailMissing) {
            verify(emailRepository).getUserEmail(id);
        } else {
            verify(emailRepository, never()).getUserEmail(id);
        }
    }
}
