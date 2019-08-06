package org.hopto.delow.chat.usecase.port;

import org.hopto.delow.chat.domain.exception.UserAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T, V> {

    V add(V entity) throws UserAlreadyExists;

    Optional<V> findById(T id);

    void delete(V entity);

    List<V> getAll();

}
