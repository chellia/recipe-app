import { Component, OnInit } from '@angular/core';
import { ComplaintService } from '../../services/complaint.service';
import {IComplaint} from './complaint';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './complaint.component.html',
  styleUrls: ['./complaint.component.css']
})
export class ComplaintComponent implements OnInit {

  pageTitle = 'Complaint List';
  errorMessage = '';
  sub!: Subscription;
  // tslint:disable-next-line:variable-name
  private _listFilter = '';
  get listFilter(): string {
    return this._listFilter;
  }

  set listFilter(value: string) {
    this._listFilter = value;
    this.filteredComplaints = this.performFilter(value);
  }

  filteredComplaints: IComplaint[] = [];
  complaints: IComplaint[] = [];

  constructor(private complaintService: ComplaintService) { }

  performFilter(filterBy: string): IComplaint[] {
    filterBy = filterBy.toLocaleLowerCase();
    return this.complaints.filter((complaint: IComplaint) =>
        complaint.name.toLocaleLowerCase().includes(filterBy));
  }

  ngOnInit(): void {
    this.sub = this.complaintService.getAllComplaints().subscribe({
      next: complaints => {
        this.complaints = complaints;
        this.filteredComplaints = this.complaints;
      },
      error: err => this.errorMessage = err
    });
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
