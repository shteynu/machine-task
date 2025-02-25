import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {interval, Subscription, switchMap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {PipelineStatus} from "./model/model";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NgForOf, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  status!: PipelineStatus;
  pollSubscription!: Subscription;
  private http = inject(HttpClient)
  public machineKeys: (keyof PipelineStatus['machines'])[] = ['machineA', 'machineB', 'machineC'];


  constructor() { }

  ngOnInit() {
    // Poll the /api/status endpoint every 2 seconds

  }

  ngOnDestroy() {
    if (this.pollSubscription) {
      this.pollSubscription.unsubscribe();
    }
  }

  startJob() {
    this.http.post('http://localhost:8080/api/job/start', {}).subscribe();
    this.pullingStatuses();
  }

  pullingStatuses() {
    this.pollSubscription = interval(2000).pipe(
      switchMap(() => this.http.get<PipelineStatus>('http://localhost:8080/api/status'))
    ).subscribe((data) => {
      console.log(data.machines.machineB.queue);
      this.status = data;
    });
  }
}
