package com.example.demo.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HeroDTO {

  @Id
  @NotNull
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private GenderDTO gender;

  @NotNull
  private String telephone;

  private String description;

  @NotNull
  private CategoryDTO category;

}
