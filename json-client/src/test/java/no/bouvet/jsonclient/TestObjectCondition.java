package no.bouvet.jsonclient;

public class TestObjectCondition implements Condition<TestObject> {

    public final String wantedState;

    public TestObjectCondition(String wantedState) {
        this.wantedState = wantedState;
    }

    @Override
    public boolean isFulfilled(TestObject object) {
        return object.getState().equals(wantedState);
    }
}
