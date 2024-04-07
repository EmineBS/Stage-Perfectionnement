import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFeature, NewFeature } from '../feature.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFeature for edit and NewFeatureFormGroupInput for create.
 */
type FeatureFormGroupInput = IFeature | PartialWithRequiredKeyOf<NewFeature>;

type FeatureFormDefaults = Pick<NewFeature, 'id'>;

type FeatureFormGroupContent = {
  id: FormControl<IFeature['id'] | NewFeature['id']>;
  name: FormControl<IFeature['name']>;
  description: FormControl<IFeature['description']>;
  enabled: FormControl<IFeature['enabled']>;
  price: FormControl<IFeature['price']>;
};

export type FeatureFormGroup = FormGroup<FeatureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FeatureFormService {
  createFeatureFormGroup(feature: FeatureFormGroupInput = { id: null }): FeatureFormGroup {
    const featureRawValue = {
      ...this.getFormDefaults(),
      ...feature,
    };
    return new FormGroup<FeatureFormGroupContent>({
      id: new FormControl(
        { value: featureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(featureRawValue.name),
      description: new FormControl(featureRawValue.description),
      enabled: new FormControl(featureRawValue.enabled),
      price: new FormControl(featureRawValue.price),
    });
  }

  getFeature(form: FeatureFormGroup): IFeature | NewFeature {
    return form.getRawValue() as IFeature | NewFeature;
  }

  resetForm(form: FeatureFormGroup, feature: FeatureFormGroupInput): void {
    const featureRawValue = { ...this.getFormDefaults(), ...feature };
    form.reset(
      {
        ...featureRawValue,
        id: { value: featureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FeatureFormDefaults {
    return {
      id: null,
    };
  }
}
