import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModalCreateVisitComponent } from './modal-create-visit.component';
import { NotifierService } from 'angular-notifier';

describe('ModalCreateVisitComponent', () => {
  let component: ModalCreateVisitComponent;
  let fixture: ComponentFixture<ModalCreateVisitComponent>;
  let notifier: NotifierService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalCreateVisitComponent],
      providers: [NotifierService],
    }).compileComponents();

    fixture = TestBed.createComponent(ModalCreateVisitComponent);
    notifier = TestBed.inject(NotifierService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
