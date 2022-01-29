package com.beckers.group.service.impl;

import com.beckers.group.exception.BadRequestException;
import com.beckers.group.exception.ResourceAlreadyExistsException;
import com.beckers.group.exception.ResourceNotFoundException;
import com.beckers.group.model.Complaint;
import com.beckers.group.repository.CustomerRepository;
import com.beckers.group.repository.UserRepository;
import com.beckers.group.repository.ComplaintRepository;
import com.beckers.group.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
class DefaultComplaintService implements ComplaintService {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;

  @Autowired
  DefaultComplaintService(
    ComplaintRepository complaintRepository, UserRepository userRepository,
     CustomerRepository customerRepository) {
    this.complaintRepository = complaintRepository;
    this.userRepository = userRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public Complaint getComplaintById(Long id) {
    return complaintRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Could not find complaint with ID provided"));
  }

  @Override
  public void deleteComplaintById(Long complaintId) {
    Optional<Complaint> complaint = complaintRepository.findById(complaintId);
    if (complaint.isPresent()) {
      deleteComplaintInTransaction(complaint.get());
    } else {
      throw new ResourceNotFoundException("Could not find complaint with ID provided");
    }
  }

  @Transactional
  public void deleteComplaintInTransaction(Complaint complaint) {
    complaintRepository.delete(complaint);
  }

  @Override
  public List<Complaint> getAllComplaints() {
    return complaintRepository.findAll();
  }

  @Override
  public Complaint createComplaint(Complaint complaint) {
    if (complaint.getId() != null && complaintRepository.existsById(complaint.getId())) {
      throw new ResourceAlreadyExistsException("The ID is already exists");
    }
    return complaintRepository.save(complaint);
  }

  @Override
  public Complaint updateComplaint(Complaint complaint) {
    if (complaint.getId() != null) {
      return complaintRepository.save(complaint);
    }
    throw new BadRequestException("The ID must be provided when updating a Complaint");
  }
}
