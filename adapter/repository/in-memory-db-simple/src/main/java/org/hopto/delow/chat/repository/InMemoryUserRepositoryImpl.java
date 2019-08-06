package org.hopto.delow.chat.repository;

import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.exception.UserAlreadyExists;
import org.hopto.delow.chat.usecase.port.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryUserRepositoryImpl implements UserRepository<String, User> {

    private final ConcurrentHashMap<String, User> repo = new ConcurrentHashMap<>();

    private final HashSet<String> usernames = new HashSet<>();

    private Lock lock = new ReentrantLock(true);

    @Override
    public User add(User entity) throws UserAlreadyExists {
        try {
            lock.lock();
            if (!usernames.contains(entity.getUsername())) {
                usernames.add(entity.getUsername());
                return repo.put(entity.getId(), entity);
            } else {
                throw new UserAlreadyExists(entity.getUsername());
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(repo.getOrDefault(id, null));
    }

    @Override
    public void delete(User entity) {
        repo.remove(entity.getId());
        usernames.remove(entity.getUsername());
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(repo.values());
    }
}
