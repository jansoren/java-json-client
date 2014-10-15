package no.bouvet.jsonclient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ConvertTestObjectTest {

    private JsonConverter converter = new JsonConverter();

    @Test
    public void testConvertToJson() {
        String json = converter.toJson(new TestObject());
        assertNotNull(json);
    }

    @Test
    public void testConvertToObject() {
        String json = converter.toJson(new TestObject());
        TestObject testObject = converter.toObject(json, TestObject.class);
        assertNotNull(testObject);
    }

    @Test
    public void testConvertToList() {
        String json = converter.toJson(createList());
        List<TestObject> list = converter.toList(json, TestObject.class);
        assertNotNull(list);
        assertNotNull(list.get(0));
    }

    @Test
    public void testConvertToListOfList() {
        String json = converter.toJson(createListOfList());
        List<List<TestObject>> listOfList = converter.toListOfList(json, TestObject.class);
        assertNotNull(listOfList);
        assertNotNull(listOfList.get(0).get(0));
    }

    private List<List<TestObject>> createListOfList() {
        List<List<TestObject>> listOfList = new ArrayList<List<TestObject>>();
        listOfList.add(createList());
        listOfList.add(createList());
        listOfList.add(createList());
        return listOfList;
    }


    private List<TestObject> createList() {
        List<TestObject> list = new ArrayList<TestObject>();
        list.add(new TestObject());
        list.add(new TestObject());
        list.add(new TestObject());
        return list;
    }
}
