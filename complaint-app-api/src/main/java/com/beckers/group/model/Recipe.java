package com.beckers.group.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueRecipeName",
                                              columnNames = {"name"})})
public class Recipe implements Serializable {

  private static final long serialVersionUID = 5560221391479816650L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @CreationTimestamp
  private LocalDateTime createdDateTime;

  private boolean vegetarian;

  private Integer servings;

  private String instructions;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((!(o instanceof Recipe))) {
      return false;
    }
    Recipe recipe = (Recipe) o;
    return name.equals(recipe.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Recipe{" + "name='" + name + '\'' + ", createdDate=" + createdDateTime + ", vegitarian="
      + vegetarian + ", servings=" + servings + ", instructions='" + instructions + '\'' + '}';
  }
}
