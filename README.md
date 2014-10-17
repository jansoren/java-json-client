java-json-client
================

This is a library that provides you with a simple way to do http/https requests to your REST API. 

### Usage

```
<dependency>
    <groupId>no.bouvet</groupId>
    <artifactId>json-client</artifactId>
    <version>1.0.1</version>
</dependency>
```

Create your json client like this:
```
JsonClient jsonClient = new JsonClient();
```

Or with a `ObjectMapper` like this:
```
JsonClient jsonClient = new JsonClient(objectMapper);
```

How to get your REST-API response for GET, POST, PUT and DELETE:
```
HttpResponse response = jsonClient.http().get("http://").response();
HttpResponse response = jsonClient.http().post("http://", new ObjectToPost()).response();
HttpResponse response = jsonClient.http().put("http://", new ObjectToPut()).response();
HttpResponse response = jsonClient.http().delete("http://").response();
```

How to get your response using https:
```
HttpResponse response = jsonClient.ssl().get("http://").response();
```

How to get and parse your response directly to your preferred object:
```
MyObject myObject = jsonClient.http().get("http://").object(MyObject.class);
```

How to get and parse your response directly to your preferred list:
```
List<MyObject> myObject = jsonClient.http().get("http://").list(MyObject.class);
```

How to get and parse your response directly to your preferred list of list:
```
List<MyObject> myObject = jsonClient.http().get("http://").listOfList(MyObject.class);
```

How you get and parse your response directly to your preferred map:
```
Map<String, MyObject> myObject = jsonClient.http().get("http://").map(MyObject.class);
```

If you are using Spring and Joda DateTime you might need to configure it using [JsonClientJackson2ObjectMapperFactoryBean](https://github.com/bouvet-openlab/java-json-client/blob/master/json-client/src/main/java/no/bouvet/jsonclient/spring/JsonClientJackson2ObjectMapperFactoryBean.java) in your servlet.xml
```
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="no.bouvet.jsonclient.spring.JsonClientJackson2ObjectMapperFactoryBean"/>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

This library has been tested on a REST API implemented in:
 - [Jersey](https://jersey.java.net/) 
 - [Spring](http://spring.io/guides/tutorials/rest/)
