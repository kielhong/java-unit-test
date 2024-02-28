package com.example.demo.article.adapter.out.client;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

class UserClientTest {
    private static ClientAndServer mockServer;

    private UserClient userClient;

    @BeforeAll
    static void startServer() {
        mockServer = startClientAndServer(1234);
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void setUp() {
        userClient = new UserClient(1234);
    }

    @Test
    void user() {
        mockServer.when(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/users/username")
        ).respond(
            HttpResponse.response()
                .withBody("{\"username\": \"username\", \"nickname\": \"nick\" }")
                .withStatusCode(200)
        );

        var result = userClient.existsUser("username");

        then(result)
            .isTrue();
    }
}