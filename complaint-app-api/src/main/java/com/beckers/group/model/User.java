package com.beckers.group.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueUserName",
  columnNames = {"name"})})
public class User implements Serializable {

  private static final long serialVersionUID = 5560221391479816650L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  private String password;

  private String email;

  private String role;

  private String full_name;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((!(o instanceof User))) {
      return false;
    }
    User user = (User) o;
    return name.equals(user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", role=" + role + '}';
  }
}
