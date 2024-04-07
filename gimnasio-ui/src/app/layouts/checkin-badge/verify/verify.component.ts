import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss'],
})
export class VerifyComponent implements OnInit {
  email: string = '';
  pin: string = '';
  confirmPin: string = '';
  confirmForm: FormGroup;

  constructor(protected activeModal: NgbActiveModal, private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.confirmForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        pin: ['', [Validators.required, Validators.minLength(6)]],
        confirmPin: ['', [Validators.required, Validators.minLength(6)]],
      },
      { validator: this.arePinsMatching }
    );
  }

  submitForm() {
    // Handle form submission logic here
    console.log('Submitted email:', this.confirmForm.value.email);
    console.log('Submitted PIN code:', this.confirmForm.value.pin);
    console.log('Submitted Confirm PIN code:', this.confirmForm.value.confirmPin);
    // You can perform further validation, API calls, etc.
  }

  arePinsMatching(group: FormGroup) {
    const pinControl = group.get('pin');
    const confirmPinControl = group.get('confirmPin');

    if (pinControl && confirmPinControl) {
      const pin = pinControl.value;
      const confirmPin = confirmPinControl.value;

      return pin === confirmPin ? null : { mismatchedPins: true };
    }

    return null;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
