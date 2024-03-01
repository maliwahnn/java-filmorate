package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDBStorageTest {

    private final UserDBStorage userStorage;

    @Test
    public void save() {
        User user1 = User.builder()
                .name("test")
                .email("testEmail")
                .login("testlogin")
                .birthday(LocalDate.of(1994, 2, 1))
                .build();

        userStorage.save(user1);
        Optional<User> userOptional = Optional.of(userStorage.get(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );

        user1.setId(1);
        user1.setName("Update");
        userStorage.update(user1);

        User userUp = userStorage.get(1);
        assertEquals("Update", userUp.getName());

        userStorage.delete(1);
        assertThrows(EmptyResultDataAccessException.class, () -> userStorage.get(1));

    }

}