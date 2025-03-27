import { HttpClientModule } from '@angular/common/http';
import { expect } from '@jest/globals';
import { Session } from '../../interfaces/session.interface';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { FormComponent } from './form.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgZone } from '@angular/core';


describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: jest.Mocked<SessionApiService>;
  let router: Router;
  let ngZone: NgZone;


  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  };

  const mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('1')
      }
    }
  };

  beforeEach(async () => {
    sessionApiService = {
      create: jest.fn().mockReturnValue(of({})),
      update: jest.fn().mockReturnValue(of({})),
      detail: jest.fn().mockReturnValue(of({
        id: '1',
        name: 'Test Session',
        date: '2025-03-26',
        teacher_id: '123',
        description: 'Test Description'
      })),
    } as unknown as jest.Mocked<SessionApiService>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule  
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: SessionApiService, useValue: sessionApiService }
      ],
      declarations: [FormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });
  beforeEach(() => {
    ngZone = TestBed.inject(NgZone);
  });
  
  it('should redirect non-admin users', () => {
    mockSessionService.sessionInformation.admin = false;
    const navigateSpy = jest.spyOn(router, 'navigate');
  
    ngZone.run(() => {
      component.ngOnInit();
    });
  
    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form correctly', () => {
    expect(component['sessionForm']).toBeDefined();
    expect(component['sessionForm']?.controls['name']).toBeDefined();
    expect(component['sessionForm']?.controls['date']).toBeDefined();
    expect(component['sessionForm']?.controls['teacher_id']).toBeDefined();
    expect(component['sessionForm']?.controls['description']).toBeDefined();
  });

  it('should disable update mode if not updating', () => {
    (mockActivatedRoute.snapshot.paramMap.get as jest.Mock).mockReturnValue(null);
    component.ngOnInit();
    expect(component['onUpdate']).toBe(false);
  });  
  
  it('should submit a new session', () => {
    const sessionData = {
      name: 'Test Session',
      date: '2025-03-26',
      teacher_id: '123',
      description: 'Test Description'
    };

    component['sessionForm']?.setValue(sessionData);

    jest.spyOn(sessionApiService, 'create').mockReturnValue(
      of({
        name: 'Test Session',
        date: new Date('2025-03-26'), // Convertir en Date
        teacher_id: 123,
        description: 'Test Description',
        users: [] // La propriété `users` est maintenant bien définie
      } as Session) // Assurer la correspondance avec l'interface Session
    );
    
    
        jest.spyOn(component as any, 'exitPage');

    component.submit();

    expect(sessionApiService.create).toHaveBeenCalledWith(sessionData);
    expect(component['exitPage']).toHaveBeenCalledWith('Session created !');
  });
  

  it('should redirect non-admin users', () => {
    mockSessionService.sessionInformation.admin = false;
    const navigateSpy = jest.spyOn(router, 'navigate');

    component.ngOnInit();

    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
  });
});