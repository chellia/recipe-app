package com.beckers.group.request;

import com.beckers.group.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;

  @NotNull
  private String name;

  private String password;

  private String email;

  private String role;

  private String full_name;

  public UserDto(User user){
    this.name = user.getName();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.full_name = user.getFull_name();
  }

 }
