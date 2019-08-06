package org.hopto.delow.chat.usecase.port;

import java.util.List;

public interface MessageRepository<T> {

    T add(T entity);

    List<T> findAll();

}
