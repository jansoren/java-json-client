package no.bouvet.jsonclient;

import org.junit.Test;

public class JsonClientTest {

    private JsonClient jsonClient = new JsonClient();

    @Test(expected=RuntimeException.class)
    public void testJsonClientContentType() {
        jsonClient.http().get("http://www.google.no").object(TestObject.class);
    }

    @Test(expected=RuntimeException.class)
    public void testThatHttpClientThrowsExceptionWhenNull() {
        jsonClient.get("http://www.google.no");
    }
}
