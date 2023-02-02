package com.example.demo.service;

import static com.example.demo.HeroMother.ONE_ID;
import static com.example.demo.HeroMother.ONE_KEYWORD;
import static com.example.demo.HeroMother.ONE_TELEPHONE;
import static com.example.demo.HeroMother.oneHero;
import static com.example.demo.HeroMother.someHeroes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.impl.HeroServiceImpl;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

  @Mock
  HeroRepository heroRepository;

  @InjectMocks
  private HeroServiceImpl heroServiceImpl;

  @Test
  void getsAllHeroes() {
    givenSomeHeroes();
    final List<Hero> heroes = heroServiceImpl.getAllHeroes();
    assertThat(heroes).hasSize(2);
  }

  @Test
  void getsHeroById() throws InstanceNotFoundException {
    givenOneHero();
    final Hero hero = heroServiceImpl.getHeroById(ONE_ID);
    assertThat(hero.getName()).isEqualTo(oneHero().getName());
  }

  @Test
  void throwsExceptionWhenHeroIsNotFound() {
    givenZeroHero();
    assertThrows(InstanceNotFoundException.class, () -> heroServiceImpl.getHeroById(ONE_ID));
  }

  @Test
  void findsHeroByKeywords() {
    givenSomeHeroesFindByName();
    final List<Hero> heroes = heroServiceImpl.findHeroByKeywords(ONE_KEYWORD);
    assertThat(isNotEmpty(heroes));
    assertThat(heroes).hasSize(2);
  }

  @Test
  void searchesHeroByKeywords() {
    givenSomeHeroesSearchHeroByKeywords();
    final List<Hero> heroes = heroServiceImpl.searchHeroByKeywords(ONE_KEYWORD);
    assertThat(isNotEmpty(heroes));
    assertThat(heroes).hasSize(2);
  }

  @Test
  void createsHero() throws ValidationException {
    givenZeroHerofindByNameContainingIgnoreCase();
    givenOneHeroSaved(oneHero());
    final Hero hero = heroServiceImpl.createHero(oneHero());
    assertThat(hero.getName()).isEqualTo(oneHero().getName());
  }

  @Test
  void createsHeroWithRepeatedNameThrowsException() {
    givenSomeHeroesFindByName();
    assertThrows(ValidationException.class, () -> heroServiceImpl.createHero(oneHero()));
  }

  @Test
  void updatesHero() throws InstanceNotFoundException {
    givenOneHero();
    final Hero heroUpdated = oneHero();
    heroUpdated.setTelephone(ONE_TELEPHONE);
    givenOneHeroSaved(heroUpdated);
    final Hero hero = heroServiceImpl.updateHero(heroUpdated);
    assertThat(hero.getTelephone()).isEqualTo(heroUpdated.getTelephone());
  }

  @Test
  void updatesHeroThatDoNotExistsThrowsException() {
    givenZeroHero();
    assertThrows(InstanceNotFoundException.class, () -> heroServiceImpl.updateHero(oneHero()));
  }

  @Test
  void deletesHeroThatDoNotExistsThrowsException() {
    givenZeroHero();
    assertThrows(InstanceNotFoundException.class, () -> heroServiceImpl.deleteHeroById(oneHero().getId()));
  }

  @Test
  void deletesHeroById() {
    givenOneHero();
    assertDoesNotThrow(() -> heroServiceImpl.deleteHeroById(oneHero().getId()));
  }

  @Test
  void deletesHeroByName() {
    givenSomeHeroesFindByName();
    assertDoesNotThrow(() -> heroServiceImpl.deleteHeroByName(oneHero().getName()));
  }

  private void givenOneHero() {
    doReturn(Optional.of(oneHero())).when(heroRepository).findById(any());
  }

  private void givenSomeHeroes() {
    doReturn(someHeroes()).when(heroRepository).findAll();
  }

  private void givenSomeHeroesFindByName() {
    doReturn(someHeroes()).when(heroRepository).findByNameContainingIgnoreCase(any());
  }

  private void givenZeroHerofindByNameContainingIgnoreCase() {
    doReturn(List.of()).when(heroRepository).findByNameContainingIgnoreCase(any());
  }

  private void givenSomeHeroesSearchHeroByKeywords() {
    doReturn(someHeroes()).when(heroRepository).searchHeroByKeywords(any());
  }

  private void givenOneHeroSaved(final Hero hero) {
    doReturn(hero).when(heroRepository).save(any());
  }

  private void givenZeroHero() {
    doReturn(Optional.empty()).when(heroRepository).findById(any());
  }
}