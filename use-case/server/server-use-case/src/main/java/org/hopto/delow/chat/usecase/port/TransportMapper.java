package org.hopto.delow.chat.usecase.port;

public interface TransportMapper<T, V> {

    V process(T input);

}
