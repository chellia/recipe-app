package com.beckers.group.controller;

import com.beckers.group.model.Complaint;
import com.beckers.group.request.ComplaintDto;
import com.beckers.group.service.ComplaintService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

  private final ComplaintService complaintService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  public ComplaintController(ComplaintService complaintService) {
    this.complaintService = complaintService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ComplaintDto> getAllComplaints() {
    List<Complaint> complaints = complaintService.getAllComplaints();
    return complaints.stream().map(this::convertToComplaintDto).collect(Collectors.toList());
  }

  @GetMapping(value = "{complaintId}")
  @ResponseStatus(HttpStatus.OK)
  public ComplaintDto getComplaint(
    @PathVariable
      Long complaintId) {
    Complaint complaint = complaintService.getComplaintById(complaintId);
    return convertToComplaintDto(complaint);
  }

  @DeleteMapping("{complaintId}")
  void deleteComplaintById(
    @PathVariable
      Long complaintId) {
    complaintService.deleteComplaintById(complaintId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ComplaintDto createComplaint(
    @RequestBody
      ComplaintDto complaintDto) {
    Complaint complaint = complaintService.createComplaint(convertToComplaintEntity(complaintDto));
    return convertToComplaintDto(complaint);
  }

  @PutMapping("{complaintId}")
  @ResponseStatus(HttpStatus.OK)
  public ComplaintDto updateComplaint(
    @PathVariable
      Long complaintId,
    @RequestBody
      ComplaintDto complaintUpdateDto) {
    Complaint complaint = complaintService.getComplaintById(complaintId);
    updateComplaintFromDto(complaint, complaintUpdateDto);
    complaintService.updateComplaint(complaint);
    return convertToComplaintDto(complaint);
  }

  private void updateComplaintFromDto(Complaint complaint, ComplaintDto complaintUpdateDto) {
    complaint.setNumber(complaintUpdateDto.getNumber());
    complaint.setPriority(complaintUpdateDto.getPriority());
    complaint.setDescription(complaintUpdateDto.getDescription());
    complaint.setSolution(complaintUpdateDto.getSolution());

    /*complaint.getIngredients().clear();
    complaint
        .getIngredients()
        .addAll(complaintUpdateDto
            .getIngredients()
            .stream()
            .map(this::convertToIngredientEntity)
            .collect(Collectors.toList()));*/
  }


  private Complaint convertToComplaintEntity(ComplaintDto complaintDto) {
    Complaint complaint = modelMapper.map(complaintDto, Complaint.class);
   /* complaint.setIngredients(complaintDto
        .getIngredients()
        .stream()
        .map(this::convertToIngredientEntity)
        .collect(Collectors.toList()));*/
    //complaint.setCreatedDateTime(complaintDto.stringFormatToLocalDateTime(complaintDto.getCreatedDateTime()));
    return complaint;
  }

  private ComplaintDto convertToComplaintDto(Complaint complaint) {
    ComplaintDto complaintDto = modelMapper.map(complaint, ComplaintDto.class);
    complaintDto.setCustomerDto(complaint.getCustomer());
    complaintDto.setUserDto(complaint.getUser());
   /* complaintDto.setIngredients(complaint
        .getIngredients()
        .stream()
        .map(this::convertToIngredientDto)
        .collect(Collectors.toList()));*/
    //complaintDto.setCreatedDateTime(complaintDto.localDateTimeToStringFormat(complaint.getCreatedDateTime()));
    return complaintDto;
  }

}
