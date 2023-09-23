package com.example.demo.controller;

import static com.example.demo.ConfigMother.ID_ENDPOINT;
import static com.example.demo.ConfigMother.NAME_ENDPOINT;
import static com.example.demo.ConfigMother.ONE_BASE_URL;
import static com.example.demo.ConfigMother.ONE_HERO_ENDPOINT;
import static com.example.demo.ConfigMother.PWD_DATA;
import static com.example.demo.ConfigMother.USER_DATA;
import static com.example.demo.HeroMother.ONE_HERO_NAME;
import static com.example.demo.HeroMother.ONE_KEYWORD;
import static com.example.demo.HeroMother.anotherHero;
import static com.example.demo.HeroMother.otherHero;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.example.demo.entity.Hero;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HeroControllerTest {

  @LocalServerPort
  private int port;

  private String baseUrl = ONE_BASE_URL;

  private static RestTemplate restTemplate = null;

  @BeforeAll
  public static void init() {
    restTemplate = new RestTemplate(getClientHttpRequestFactory());
  }

  private static HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
    final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
        = new HttpComponentsClientHttpRequestFactory();

    clientHttpRequestFactory.setHttpClient(httpClient());

    return clientHttpRequestFactory;
  }

  private static HttpClient httpClient() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    credentialsProvider.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials(USER_DATA, PWD_DATA));

      return HttpClientBuilder
        .create()
        .setDefaultCredentialsProvider(credentialsProvider)
        .build();
  }

  @BeforeEach
  public void setUp() {
    baseUrl = baseUrl.concat(":").concat(port + "").concat(ONE_HERO_ENDPOINT);
  }

  @Test
  void getsAHeroById() {
    final Hero heroResponse = restTemplate.getForObject(baseUrl.concat(ID_ENDPOINT), Hero.class, 1);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertEquals(anotherHero().getName(), heroResponse.getName())
    );
  }

  @Test
  void findsAHeroByName() {
    final List<Hero> heroResponse = restTemplate.getForObject(baseUrl.concat(NAME_ENDPOINT), List.class, ONE_KEYWORD);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertEquals(3, heroResponse.size())
    );
  }

  @Test
  void createsAHero() {
    final Hero hero = otherHero();
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<Hero> httpEntity = new HttpEntity<>(hero, headers);

    final Hero heroResponse = restTemplate.postForObject(baseUrl, httpEntity, Hero.class);
    assertAll(
        () -> assertNotNull(heroResponse),
        () -> assertNotNull(heroResponse.getId()),
        () -> assertEquals(hero.getName(), heroResponse.getName()));
  }

  @Test
  void updatesAHero() {
    final Hero hero = restTemplate.getForObject(baseUrl.concat(ID_ENDPOINT), Hero.class, 1);
    assertNotNull(hero);
      hero.setName(ONE_HERO_NAME);
    final HttpEntity<Hero> entity = new HttpEntity<Hero>(hero);
    final ResponseEntity<Hero> heroResponse = restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, Hero.class);
    final Hero heroUpdated = (Hero) heroResponse.getBody();
    assertAll(
        () -> assertNotNull(heroUpdated),
        () -> assertNotNull(heroUpdated.getId()),
        () -> assertEquals(hero.getName(), heroUpdated.getName()));
  }

}