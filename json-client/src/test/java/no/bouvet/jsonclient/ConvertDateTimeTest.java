package no.bouvet.jsonclient;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConvertDateTimeTest {

    private JsonConverter converter = new JsonConverter();

    @Test
    public void testConvertDateTimeToJson() {
        String json = converter.toJson(new DateTime());
        assertNotNull(json);
    }

    @Test
    public void testConvertJsonToDateTime() {
        String json = converter.toJson(new DateTime());
        DateTime dateTime = converter.toObject(json, DateTime.class);
        assertNotNull(dateTime);
    }

    @Test
    public void testConvertListOfDateTimeToJson() {
        String json = converter.toJson(createDateTimeList());
        assertNotNull(json);
    }

    @Test
    public void testConvertJsonToListOfDateTime() {
        String json = converter.toJson(createDateTimeList());
        List<DateTime> dateTimeList = converter.toList(json, DateTime.class);
        assertNotNull(dateTimeList);
        assertEquals(3, dateTimeList.size());
    }

    @Test
    public void testConvertListOfListOfDateTimeToJson() {
        String json = converter.toJson(createDateTimeListOfList());
        assertNotNull(json);
    }

    @Test
    public void testConvertJsonToListOfListOfDateTime() {
        String json = converter.toJson(createDateTimeListOfList());
        List<List<DateTime>> dateTimeListOfList = converter.toListOfList(json, DateTime.class);
        assertNotNull(dateTimeListOfList);
        assertNotNull(dateTimeListOfList.get(0).get(0));
        assertNotNull(dateTimeListOfList.get(0).get(0) instanceof DateTime);
    }

    private List<List<DateTime>> createDateTimeListOfList() {
        List<List<DateTime>> listOfList = new ArrayList<List<DateTime>>();
        listOfList.add(createDateTimeList());
        listOfList.add(createDateTimeList());
        listOfList.add(createDateTimeList());
        return listOfList;
    }

    private List<DateTime> createDateTimeList() {
        List<DateTime> list = new ArrayList<DateTime>();
        list.add(new DateTime());
        list.add(new DateTime());
        list.add(new DateTime());
        return list;
    }
}
