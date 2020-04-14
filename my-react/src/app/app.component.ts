import {Component, OnInit} from '@angular/core';
import {Events} from "./Events";
import {HttpClient} from "@angular/common/http";
import {interval, Observable} from "rxjs";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-react';
  // name: string
  // events: Events
  events2: Observable<Events>
  eventList: Events[] = new Array()
  // eventsData: Observable<Events[]>
  testAsync=['qw', 'er', 'lk', 'fg', 'xs']
  test: Observable<string>

  constructor(private  http: HttpClient) {
    // this.name = 'test'
    // this.events = new Events(0, 'null', new Date());

  }

  ngOnInit(): void {
  //  this.onEventsData()
  }

//
  showTest() {
    this.test = interval(500).pipe(map((i: number) => this.testAsync[i]));
  }
//
//   onGetEvents() {
//     console.log("test1")
//     //   this.onStart().subscribe(event=>{this.events=event.valueOf(), console.log(event)})
// //this.onEvents().subscribe()
//     this.onEventsData()
//   }
//
//   onStart(): Observable<any> {
//     return this.http.get("http://localhost:8080/events");
//   }
//
  onEvents(): Observable<any> {
    this.eventList = new Array()

    return new Observable((observer) => {
      const eventSource = new EventSource("http://localhost:8081/events");
      eventSource.onmessage = (event) => {
        console.log(event)
        const json = JSON.parse(event.data)
        this.eventList.push(new Events(json['id'], json['name'], json['date']));
        observer.next(this.eventList);

      };
    });

  }
//
//   onEventsData() {
//     this.onEvents().subscribe(ev => {
//       // console.log(ev);
//       this.eventsData = ev;
//       this.events2 = ev[0];
//
//     });
//
//   }
//
//   onButtonClick() {
//     console.log(this.events2)
//     console.log(this.eventsData)
//   }
//

}
