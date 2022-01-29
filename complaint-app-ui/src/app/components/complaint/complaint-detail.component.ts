import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {IComplaint} from './complaint';
import {ComplaintService} from '../../services/complaint.service';
import {Subscription} from 'rxjs';

@Component({
  templateUrl: './complaint-detail.component.html',
  styleUrls: ['./complaint-detail.component.css']
})
export class ComplaintDetailComponent implements OnInit {
  pageTitle = 'Complaint Detail';
  errorMessage = '';
  complaint: IComplaint | undefined;
  sub!: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private complaintService: ComplaintService) {
  }

 /* ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.sub = this.complaintService.getComplaint(id).subscribe({
        next: complaint => this.complaint = complaint,
        error: err => this.errorMessage = err
      });
    }
  }*/

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.sub = this.complaintService.getComplaint(id).subscribe(complaint => {
          this.complaint = complaint;
      });
    }
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onBack(): void {
    this.router.navigate(['/complaints']);
  }
}
