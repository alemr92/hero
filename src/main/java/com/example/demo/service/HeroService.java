package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Hero;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;

public interface HeroService {

  List<Hero> getAllHeroes();

  Hero getHeroById(Integer id) throws InstanceNotFoundException;

  List<Hero> findHeroByKeywords(String keywords);

  List<Hero> searchHeroByKeywords(String keywords);

  Hero createHero(Hero hero) throws ValidationException;

  Hero updateHero(Hero hero) throws InstanceNotFoundException;

  void deleteHeroById(Integer id) throws InstanceNotFoundException;

  void deleteHeroByName(String keywords) throws InstanceNotFoundException;
}

