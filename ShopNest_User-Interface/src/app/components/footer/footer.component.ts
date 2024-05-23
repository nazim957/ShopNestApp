import { Component, OnInit } from '@angular/core';
import { ModeService } from 'src/app/services/mode.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  currentMode = 'light';

  constructor(private modeService: ModeService) {}

  ngOnInit(): void {

     // Subscribe to mode changes
     this.modeService.currentMode$.subscribe((mode) => {
      this.currentMode = mode;
    //  console.log("MODE Footer"+ this.currentMode);
      
    });
  }

}
