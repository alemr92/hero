package com.example.demo.repository;

import static com.example.demo.HeroMother.ONE_HERO_NAME;
import static com.example.demo.HeroMother.otherHero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.demo.entity.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class HeroRepositoryTest {

  @Autowired
  private HeroRepository heroRepository;

  @Test
  @Transactional
  void findsAllHeroes() {
    assertEquals(9, heroRepository.findAll().size());
  }

  @Test
  @Transactional
  void createsHero() {
    final Hero newHero = otherHero();
    final Hero hero = heroRepository.save(newHero);
    final Optional<Hero> heroFromDB = heroRepository.findById(hero.getId());
    assertTrue(heroFromDB.isPresent());
    assertEquals(hero.getName(), heroFromDB.get().getName());
  }

  @Test
  @Transactional
  void findsHeroById() {
    final int id = 1;
    final Hero hero = heroRepository.findById(id).get();
    assertEquals(ONE_HERO_NAME, hero.getName());
  }

  @Test
  @Transactional
  void updatesHero() {
    final int id = 1;
    final Hero hero = heroRepository.findById(id).get();
    hero.setName(ONE_HERO_NAME);
    heroRepository.save(hero);
    final Hero updatedHero = heroRepository.findById(id).get();
    assertEquals(hero.getName(), updatedHero.getName());
  }

  @Test
  @Transactional
  void deletesHeroById() {
    final int id = 1;
    heroRepository.deleteById(id);
    assertThrows(JpaObjectRetrievalFailureException.class, () -> heroRepository.getById(id));
  }

}
