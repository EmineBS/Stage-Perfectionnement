import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { ICheckin } from 'app/entities/checkin/checkin.model';
import { CheckinService } from 'app/entities/checkin/service/checkin.service';
import { LoginService } from 'app/login/login.service';
import { ConfirmComponent } from './confirm/confirm.component';
import { filter, switchMap } from 'rxjs';
import { EntityArrayResponseType } from 'app/entities/gym/service/gym.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { CheckinResultComponent } from './checkin-result/checkin-result.component';
import { VerifyComponent } from './verify/verify.component';

@Component({
  selector: 'jhi-checkin-badge',
  templateUrl: './checkin-badge.component.html',
  styleUrls: ['./checkin-badge.component.scss'],
})
export class CheckinBadgeComponent implements OnInit {
  //to store badge from input
  message: string;
  loading = false;
  checkin: ICheckin;
  errrorMessage = '';
  // used to store account history
  account: Account | null = null;

  constructor(
    private router: Router,
    private checkinService: CheckinService,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private loginService: LoginService,
    private offcanvasService: NgbOffcanvas,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.showLoadingIndicator();
    this.message = this.router.url.substring(4);
    this.form.value['badge'] = this.router.url.substring(4);
    if (this.message !== '') this.checkinBadgeUid(this.message!);
  }

  checkinBadgeUid(msg?: any) {
    this.errrorMessage = '';
    const uid = (this.message = this.form.value['badge'] as string);
    if (msg) {
      const uid = msg;
    }

    this.checkinService.checkin(uid).subscribe(
      data => {
        this.checkin = data.body!;
        if (data.body?.status === 'PENDING') {
          console.log('PENDING  ' + data.body?.status);
          this.confirm();
        } else if (data.body?.status === 'CONFIRMED') {
          this.openResult();
        } else if (data.body?.status === 'REFUSED') {
          this.openResult();
        }
      },
      error => {
        this.errrorMessage = error.error.detail;
        this.openResult(error.error.detail);
      }
    );
  }

  form = new FormGroup({
    badge: new FormControl('', [Validators.required]),
  });

  get f() {
    return this.form.controls;
  }

  showLoadingIndicator() {
    const loadingIndicatorElement = document.getElementById('loadingIndicator');
    if (loadingIndicatorElement) {
      loadingIndicatorElement.style.display = 'block';
    }
  }

  hideLoadingIndicator() {
    const loadingIndicatorElement = document.getElementById('loadingIndicator');
    if (loadingIndicatorElement) {
      loadingIndicatorElement.style.display = 'none';
    }
  }

  confirm(): void {
    const modalRef = this.modalService.open(ConfirmComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.badge = this.checkin;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      console.log('reason : ' + reason);
      this.openResult();
    });
  }

  verify(): void {
    const modalRef = this.modalService.open(VerifyComponent, { size: 'lg', backdrop: 'static', centered: true });

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      console.log('reason : ' + reason);
    });
  }

  openResult(msg?: string): void {
    const modalRef = this.offcanvasService.open(CheckinResultComponent, { position: 'top' });
    this.loading = false;
    modalRef.componentInstance.ContentRef = CheckinResultComponent;
    modalRef.componentInstance.badge = this.checkin;
    modalRef.componentInstance.exception = this.errrorMessage;
  }
}
