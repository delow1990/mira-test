package org.hopto.delow.chat.repository;

import org.hopto.delow.chat.domain.server.TextResponse;
import org.hopto.delow.chat.usecase.port.MessageRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class InMemoryMessageRepository implements MessageRepository<TextResponse> {

    private static final int NUM_OF_MESSAGES_TO_RETURN = 100;
    private Deque<TextResponse> deque = new ArrayDeque<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    @Override
    public TextResponse add(TextResponse entity) {
        lock.writeLock().lock();
        deque.addFirst(entity);
        lock.writeLock().unlock();
        return entity;
    }

    @Override
    public List<TextResponse> findAll() {
        lock.readLock().lock();
        ArrayDeque<TextResponse> collect = deque.stream()
                .limit(NUM_OF_MESSAGES_TO_RETURN)
                .collect(Collectors.toCollection(ArrayDeque::new));
        lock.readLock().unlock();

        List<TextResponse> response = new ArrayList<>(NUM_OF_MESSAGES_TO_RETURN);

        collect.descendingIterator().forEachRemaining(response::add);

        return response;
    }
}
