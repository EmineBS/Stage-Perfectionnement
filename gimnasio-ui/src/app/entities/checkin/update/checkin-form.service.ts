import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICheckin, NewCheckin } from '../checkin.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckin for edit and NewCheckinFormGroupInput for create.
 */
type CheckinFormGroupInput = ICheckin | PartialWithRequiredKeyOf<NewCheckin>;

type CheckinFormDefaults = Pick<NewCheckin, 'id'>;

type CheckinFormGroupContent = {
  id: FormControl<ICheckin['id'] | NewCheckin['id']>;
  status: FormControl<ICheckin['status']>;
  user: FormControl<ICheckin['user']>;
  badge: FormControl<ICheckin['badge']>;
};

export type CheckinFormGroup = FormGroup<CheckinFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckinFormService {
  createCheckinFormGroup(checkin: CheckinFormGroupInput = { id: null }): CheckinFormGroup {
    const checkinRawValue = {
      ...this.getFormDefaults(),
      ...checkin,
    };
    return new FormGroup<CheckinFormGroupContent>({
      id: new FormControl(
        { value: checkinRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(checkinRawValue.status),
      user: new FormControl(checkinRawValue.user),
      badge: new FormControl(checkinRawValue.badge),
    });
  }

  getCheckin(form: CheckinFormGroup): ICheckin | NewCheckin {
    return form.getRawValue() as ICheckin | NewCheckin;
  }

  resetForm(form: CheckinFormGroup, checkin: CheckinFormGroupInput): void {
    const checkinRawValue = { ...this.getFormDefaults(), ...checkin };
    form.reset(
      {
        ...checkinRawValue,
        id: { value: checkinRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CheckinFormDefaults {
    return {
      id: null,
    };
  }
}
