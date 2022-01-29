package com.beckers.group.request;

import com.beckers.group.model.Complaint;
import com.beckers.group.model.Customer;
import com.beckers.group.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDto {

  @JsonIgnore
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  private Long id;

  @NotNull
  private String number;

  private String description;

  private String priority;

  private String solution;

  private String status;

  private UserDto userDto;

  private CustomerDto customerDto;

  public LocalDateTime stringFormatToLocalDateTime(String createdDateTimeFormat) {
    return createdDateTimeFormat != null
      ? LocalDateTime.parse(createdDateTimeFormat, formatter)
      : null;
  }

  public String localDateTimeToStringFormat(LocalDateTime localDateTime) {
    return localDateTime != null ? localDateTime.format(formatter) : null;
  }

  public void setCustomerDto(Customer customer){
    this.customerDto = new CustomerDto(customer);
  }

  public void setUserDto(User user){
    this.userDto = new UserDto(user);
  }

}
