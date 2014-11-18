package no.bouvet.jsonclient;

import no.bouvet.jsonclient.poller.Poller;
import org.joda.time.DateTime;

public class TestObject implements Poller {

    private long id;
    private String text;
    private DateTime date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public boolean isConditionFulfilled(Object... conditions) {
        return id == conditions[0];
    }
}
