import { Router } from '@angular/router';
import { CarModelDTO } from './../../models/CarModelDTO';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-car-model-card',
  templateUrl: './car-model-card.component.html',
  styleUrls: ['./car-model-card.component.scss']
})
export class CarModelCardComponent implements OnInit {

  @Input() carModel: CarModelDTO;
  @Output() showModelDetailsEvent = new EventEmitter<number>();

  constructor() { }

  ngOnInit() {
  }

  onModelDetailsClicked(id: number) {
    this.showModelDetailsEvent.emit(id);
  }

}
