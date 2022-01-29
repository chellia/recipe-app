package com.beckers.group.service;

import com.beckers.group.model.Complaint;
import com.beckers.group.model.Recipe;

import java.util.List;

public interface ComplaintService {

  List<Complaint> getAllComplaints();

  Complaint getComplaintById(Long id);

  void deleteComplaintById(Long id);

  Complaint createComplaint(Complaint complaint);

  Complaint updateComplaint(Complaint complaint);

}
