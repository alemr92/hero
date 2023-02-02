package com.example.demo.service.impl;

import static com.example.demo.config.cache.CacheNames.ALL_HEROES_CACHE;
import static com.example.demo.config.cache.CacheNames.HERO_CACHE;
import static com.example.demo.config.cache.CacheNames.HERO_NAME_CACHE;

import java.util.List;

import com.example.demo.entity.Hero;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.HeroService;
import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = HERO_NAME_CACHE)
@AllArgsConstructor
@Service
public class HeroServiceImpl implements HeroService {

  private final HeroRepository heroRepository;

  @Cacheable(value = ALL_HEROES_CACHE)
  @Override
  public List<Hero> getAllHeroes() {
    return heroRepository.findAll();
  }

  @Cacheable(value = HERO_CACHE, key = "#id")
  @Override
  public Hero getHeroById(final Integer id) throws InstanceNotFoundException {
    return heroRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException("Hero not found"));
  }

  @Override
  public List<Hero> findHeroByKeywords(final String keywords) {
    return heroRepository.findByNameContainingIgnoreCase(keywords);
  }

  @Override
  public List<Hero> searchHeroByKeywords(final String keywords) {
    return heroRepository.searchHeroByKeywords(keywords);
  }

  @Caching(evict = {@CacheEvict(value = ALL_HEROES_CACHE, allEntries = true)})
  @Override
  public Hero createHero(final Hero hero) throws ValidationException {
    final List<Hero> heroes = heroRepository.findByNameContainingIgnoreCase(hero.getName());
    if (!heroes.isEmpty()) {
      throw (new ValidationException("Hero exists"));
    }
    return heroRepository.save(hero);
  }

  @Caching(evict = {@CacheEvict(value = ALL_HEROES_CACHE, allEntries = true)})
  @Override
  public Hero updateHero(final Hero hero) throws InstanceNotFoundException {
    getHeroById(hero.getId());
    return heroRepository.save(hero);
  }

  @Caching(evict = {@CacheEvict(value = ALL_HEROES_CACHE, allEntries = true)})
  @Override
  public void deleteHeroById(final Integer id) throws InstanceNotFoundException {
    getHeroById(id);
    heroRepository.deleteById(id);
  }

  @Caching(evict = {@CacheEvict(value = ALL_HEROES_CACHE, allEntries = true)})
  @Override
  public void deleteHeroByName(final String keywords) throws InstanceNotFoundException {
    final Hero hero = findHeroByKeywords(keywords).stream()
        .findFirst().orElseThrow(() -> new InstanceNotFoundException("Hero not found"));
    heroRepository.deleteById(hero.getId());
  }
}

