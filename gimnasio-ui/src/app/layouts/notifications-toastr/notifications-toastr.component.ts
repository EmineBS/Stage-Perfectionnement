import { Component } from '@angular/core';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-notifications-toastr',
  templateUrl: './notifications-toastr.component.html',
})
export class NotificationsToastrComponent {
  private notifier: NotifierService;

  public constructor(notifier: NotifierService) {
    this.notifier = notifier;
  }

  /**
   * Show a notification
   */
  public showNotification(type: string, message: string): void {
    this.notifier.notify(type, message);
  }

  /**
   * Hide oldest notification
   */
  public hideOldestNotification(): void {
    this.notifier.hideOldest();
  }

  /**
   * Hide newest notification
   */
  public hideNewestNotification(): void {
    this.notifier.hideNewest();
  }

  /**
   * Hide all notifications at once
   */
  public hideAllNotifications(): void {
    this.notifier.hideAll();
  }
}
