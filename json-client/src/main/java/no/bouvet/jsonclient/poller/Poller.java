package no.bouvet.jsonclient.poller;

public interface Poller {

    public boolean isConditionFulfilled(Object... conditions);
}
