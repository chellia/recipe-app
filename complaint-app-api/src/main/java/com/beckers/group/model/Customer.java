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
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueCustomerName",
  columnNames = {"name"})})
public class Customer implements Serializable {

  private static final long serialVersionUID = 5560221391479816650L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  private String email;

  private String phone;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((!(o instanceof Customer))) {
      return false;
    }
    Customer user = (Customer) o;
    return name.equals(user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "User{" + "name='" + name + '\'' + ", email=" + email + '}';
  }
}
