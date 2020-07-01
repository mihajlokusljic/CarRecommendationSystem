import { CarModelDTO } from './../../models/CarModelDTO';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-car-model-card',
  templateUrl: './car-model-card.component.html',
  styleUrls: ['./car-model-card.component.scss']
})
export class CarModelCardComponent implements OnInit {

  @Input() carModel: CarModelDTO;

  constructor() { }

  ngOnInit() {
  }

}
