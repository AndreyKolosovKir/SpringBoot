package ru.netology.springbootdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    private static final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp")
            .withExposedPorts(8080);


    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myAppFirst.start();
        myAppSecond.start();
    }

    @Test
    void contextLoadsFirst() {
        ResponseEntity<String> forEntityFirst = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", forEntityFirst.getBody());
        System.out.println(forEntityFirst.getBody());
    }

    @Test
    void contextLoadsSecond() {
        ResponseEntity<String> forEntitySecond = restTemplate.getForEntity("http://localhost:" + myAppSecond.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", forEntitySecond.getBody());
        System.out.println(forEntitySecond.getBody());
    }

}
