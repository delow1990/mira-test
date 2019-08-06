package org.hopto.delow.chat.repository;

import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.TextResponse;
import org.hopto.delow.chat.usecase.port.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryMessageRepositoryTest {

    private MessageRepository<TextResponse> repository;

    @BeforeEach
    void init() {
        repository = new InMemoryMessageRepository();
    }

    @Test
    void shouldReadInOrder() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        TextResponse tx1 = new TextResponse(user, "Message 1");
        TextResponse tx2 = new TextResponse(user, "Message 2");
        TextResponse tx3 = new TextResponse(user, "Message 3");

        List<TextResponse> refList = List.of(tx1, tx2, tx3);

        repository.add(tx1);
        repository.add(tx2);
        repository.add(tx3);

        List<TextResponse> all = repository.findAll();

        assertThat(all).containsExactlyInAnyOrderElementsOf(refList);

        for (int i = 0; i < all.size(); i++) {
            assertThat(all.get(i)).isEqualTo(refList.get(i));
        }
    }

    @Test
    void shouldReturnOnly100() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        IntStream.range(1, 200).forEach(i -> {
            TextResponse tx = new TextResponse(user, "Message " + i);
            repository.add(tx);
        });

        List<TextResponse> all = repository.findAll();

        assertThat(all).hasSize(100);

        for (int i = 0, j = 101; i < all.size(); i++) {
            assertThat(all.get(i).getMessage().equals("Message " + j));
        }

    }

}