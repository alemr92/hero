package com.example.demo;

import static com.example.demo.entity.Category.A;
import static com.example.demo.entity.Category.C;
import static com.example.demo.entity.Category.S;
import static com.example.demo.entity.Gender.M;

import java.util.List;

import com.example.demo.entity.Hero;

public class HeroMother {

  public static final Integer ONE_ID = 1;

  public static final String ONE_KEYWORD = "Man";

  public static final String ONE_TELEPHONE = "662000000";

  public static final String ONE_HERO_NAME = "New Hero";

  public static Hero oneHero() {
    return new Hero("Superman", M, "661666666", "Superman returns", S);
  }

  public static Hero anotherHero() {
    return new Hero("Spiderman", M, "662222222", "Spiderman 2000", A);
  }

  public static Hero otherHero() {
    return new Hero("Capitan Garfio", M, "662500221", "Capitan", C);
  }

  public static List<Hero> someHeroes() {
    final Hero superman = new Hero("Superman", M, "661666666", "Superman returns", S);
    final Hero batman = new Hero("Batman", M, "662666666", "Night", A);
    return List.of(superman, batman);
  }
}
