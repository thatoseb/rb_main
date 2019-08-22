import { Component, OnInit } from '@angular/core';
import { IIncidentTypes, IncidentTypes } from '../shared/model/incident-types.model';

@Component({
  selector: 'app-incident',
  templateUrl: './incident.component.html',
  styleUrls: ['./incident.component.scss']
})
export class IncidentComponent implements OnInit {

  incidentTypes : IIncidentTypes[] = [
      new IncidentTypes(1, "Crime"),
      new IncidentTypes(2, "Theft")
  ];
  constructor() { }

  ngOnInit() {
  }

}
