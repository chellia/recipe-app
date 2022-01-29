package com.beckers.group.service;

import com.beckers.group.model.Customer;

import java.util.List;

public interface CustomerService {

  List<Customer> getAllCustomers();

  Customer getCustomerById(Long id);

  void deleteCustomerById(Long id);

  Customer createCustomer(Customer customer);

  Customer updateCustomer(Customer customer);

}
