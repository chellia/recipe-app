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
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueComplaintName",
  columnNames = {"number"})})
public class Complaint implements Serializable {

  private static final long serialVersionUID = 5560221391479816650L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String number;

  private String description;

  private String priority;

  private String solution;

  private String detail;

  private String status;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((!(o instanceof Complaint))) {
      return false;
    }
    Complaint complaint = (Complaint) o;
    return number.equals(complaint.getNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }

  @Override
  public String toString() {
    return "User{" + "number='" + number + '\'' + ", status=" + status + '}';
  }
}
