package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

  @Query("select u from Hero u where lower(u.name) like lower(concat('%', :name,'%'))")
  List<Hero> searchHeroByKeywords(@Param("name") String name);

  List<Hero> findByNameContainingIgnoreCase(String keywords);

}