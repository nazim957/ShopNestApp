// mode.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ModeService {
  private currentModeSubject = new BehaviorSubject<string>('light');
  currentMode$ = this.currentModeSubject.asObservable();

  toggleMode(): void {
    const newMode = this.currentModeSubject.value === 'light' ? 'dark' : 'light';
    //from here upating newmode and notifying all subscribers
    this.currentModeSubject.next(newMode);

    // Dynamically update the background color and text color of the body based on the current mode
    document.body.style.backgroundColor = newMode === 'dark' ? 'black' : 'white';
    document.body.style.color = newMode === 'dark' ? 'white' : 'black';
    // Add other styles as needed
  }
}
