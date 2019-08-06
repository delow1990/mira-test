package org.hopto.delow.chat.usecase.port;

public interface TransportChannel {

    boolean isLoggedIn();

    void setLoggedIn(boolean loggedIn);

    void write(Object o);

    void writeAndClose(Object body);

    void close();
}
