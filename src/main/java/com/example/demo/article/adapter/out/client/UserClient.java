package com.example.demo.article.adapter.out.client;

import com.example.demo.article.application.port.out.LoadUserPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient implements LoadUserPort {
    private Integer port;

    public UserClient(Integer port) {
        this.port = port;
    }

    @Override
    public boolean existsUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        var url = "http://localhost:" + port + "/users/";
        var response = restTemplate.getForEntity(url + "/" + username, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}
