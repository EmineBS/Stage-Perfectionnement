import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPack, NewPack } from 'app/entities/pack/pack.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPack for edit and NewPackFormGroupInput for create.
 */
type PackFormGroupInput = IPack | PartialWithRequiredKeyOf<NewPack>;

type PackFormDefaults = Pick<NewPack, 'id'>;

type PackFormGroupContent = {
  id: FormControl<IPack['id'] | NewPack['id']>;
  name: FormControl<IPack['name']>;
  description: FormControl<IPack['description']>;
  workoutSessions: FormControl<IPack['workoutSessions']>;
  gym: FormControl<IPack['gym']>;
  price: FormControl<IPack['price']>;
  autoConfirm: FormControl<IPack['autoConfirm']>;
  rdvEnabled: FormControl<IPack['rdvEnabled']>;
};

export type PackFormGroup = FormGroup<PackFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GymPackFormService {
  createPackFormGroup(pack: PackFormGroupInput = { id: null }): PackFormGroup {
    const packRawValue = {
      ...this.getFormDefaults(),
      ...pack,
    };
    return new FormGroup<PackFormGroupContent>({
      id: new FormControl(
        { value: packRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(packRawValue.name),
      description: new FormControl(packRawValue.description),
      workoutSessions: new FormControl(packRawValue.workoutSessions),
      gym: new FormControl(packRawValue.gym),
      price: new FormControl(packRawValue.price),
      autoConfirm: new FormControl(packRawValue.autoConfirm),
      rdvEnabled: new FormControl(packRawValue.rdvEnabled),
    });
  }

  getPack(form: PackFormGroup): IPack | NewPack {
    return form.getRawValue() as IPack | NewPack;
  }

  resetForm(form: PackFormGroup, pack: PackFormGroupInput): void {
    const packRawValue = { ...this.getFormDefaults(), ...pack };
    form.reset(
      {
        ...packRawValue,
        id: { value: packRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PackFormDefaults {
    return {
      id: null,
    };
  }
}
