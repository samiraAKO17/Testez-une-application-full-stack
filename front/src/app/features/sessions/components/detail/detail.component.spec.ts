import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { SessionService } from '../../../../services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { DetailComponent } from './detail.component';
import { expect } from '@jest/globals';
import { ReactiveFormsModule } from '@angular/forms'; 


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiService: jest.Mocked<SessionApiService>;
  let teacherService: jest.Mocked<TeacherService>;
  let router: jest.Mocked<Router>;
  let snackBar: jest.Mocked<MatSnackBar>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  };
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailComponent],
      imports: [ReactiveFormsModule]
    }).compileComponents();
  });
  beforeEach(async () => {
    sessionApiService = {
      detail: jest.fn().mockReturnValue(of({
        id: '1',
        teacher_id: '101',
        users: [1, 2, 3]
      })),
      delete: jest.fn().mockReturnValue(of(void 0)),
      participate: jest.fn().mockReturnValue(of(void 0)),
      unParticipate: jest.fn().mockReturnValue(of(void 0)),
    } as unknown as jest.Mocked<SessionApiService>;

    teacherService = {
      detail: jest.fn().mockReturnValue(of({
        id: '101',
        name: 'John Doe'
      })),
    } as unknown as jest.Mocked<TeacherService>;

    router = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;
    snackBar = { open: jest.fn() } as unknown as jest.Mocked<MatSnackBar>;

    await TestBed.configureTestingModule({
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiService },
        { provide: TeacherService, useValue: teacherService },
        { provide: Router, useValue: router },
        { provide: MatSnackBar, useValue: snackBar },
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: { get: () => '1' } } },
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch session details on init', () => {
    expect(sessionApiService.detail).toHaveBeenCalledWith('1');
    expect(teacherService.detail).toHaveBeenCalledWith('101');
    expect(component.session).toEqual({
      id: '1',
      teacher_id: '101',
      users: [1, 2, 3]
    });
    expect(component.teacher).toEqual({
      id: '101',
      name: 'John Doe'
    });
    expect(component.isParticipate).toBe(true);
  });

  it('should delete session and navigate to sessions list', () => {
    component.delete();
    expect(sessionApiService.delete).toHaveBeenCalledWith('1');
    expect(snackBar.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(router.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should participate in the session', () => {
    component.participate();
    expect(sessionApiService.participate).toHaveBeenCalledWith('1', '1');
  });

  it('should unparticipate from the session', () => {
    component.unParticipate();
    expect(sessionApiService.unParticipate).toHaveBeenCalledWith('1', '1');
  });

});