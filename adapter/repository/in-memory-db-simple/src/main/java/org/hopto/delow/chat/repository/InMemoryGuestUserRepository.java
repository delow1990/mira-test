package org.hopto.delow.chat.repository;

import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGuestUserRepository implements UserRepository<String, User> {

    private final ConcurrentHashMap<String, User> repo = new ConcurrentHashMap<>();

    @Override
    public User add(User entity) {
        repo.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public void delete(User entity) {
        repo.remove(entity.getId());
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(repo.values());
    }
}
