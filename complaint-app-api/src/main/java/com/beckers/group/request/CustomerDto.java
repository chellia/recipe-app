package com.beckers.group.request;

import com.beckers.group.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

  private Long id;

  @NotNull
  private String name;

  private String email;

  private String phone;

  public CustomerDto(Customer customer){
    this.name = customer.getName();
    this.email = customer.getEmail();
    this.phone = customer.getPhone();
    this.id = customer.getId();
  }

}
