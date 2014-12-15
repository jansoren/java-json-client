package no.bouvet.jsonclient;

public interface Condition<T> {

    public boolean isFulfilled(T object);
}
