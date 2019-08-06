package org.hopto.delow.chat.usecase.port;

public interface TransportChannelFactory<T> {

    TransportChannel wrap(T channel);

}
