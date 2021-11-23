package com.home.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient implements Serializable {

  private static final long serialVersionUID = 3252591505029724236L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  private double quantity;

  private String units;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY,
             optional = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Recipe recipe;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((!(o instanceof Ingredient))) {
      return false;
    }
    Ingredient that = (Ingredient) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return quantity + " " + units + " of " + name;
  }
}
