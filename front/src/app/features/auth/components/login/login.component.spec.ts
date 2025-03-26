import { ComponentFixture, TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { SessionService } from '../../../../services/session.service';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: jest.Mocked<AuthService>;
  let sessionServiceMock: jest.Mocked<SessionService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    authServiceMock = {
      login: jest.fn(),
      register: jest.fn(), // Ajout des autres méthodes requises
    } as unknown as jest.Mocked<AuthService>;

    sessionServiceMock = {
      logIn: jest.fn(),
      logOut: jest.fn(),
      isLogged: false,
      sessionInformation: undefined,
      $isLogged: of(false), // Doit être un MockInstance<Observable<boolean>, []>
    } as unknown as jest.Mocked<SessionService>;

    routerMock = {
      navigate: jest.fn(),
      events: of(), // Ajout pour correspondre à la définition
      config: [],
      routerState: {} as any,
      errorHandler: jest.fn(),
      resetConfig: jest.fn(),
      dispose: jest.fn(),
      createUrlTree: jest.fn(),
      serializeUrl: jest.fn(),
      parseUrl: jest.fn(),
      isActive: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService login and navigate on successful login', () => {
    const mockSession: SessionInformation = {
      token: 'mock-token',
      type: 'Bearer',
      id: 1,
      username: 'testUser',
      firstName: 'Test',
      lastName: 'User',
      admin: false,
    };

    authServiceMock.login.mockReturnValue(of(mockSession));

    component.form.setValue({ email: 'test@example.com', password: 'password' });
    component.submit();

    expect(authServiceMock.login).toHaveBeenCalledWith({ email: 'test@example.com', password: 'password' });
    expect(sessionServiceMock.logIn).toHaveBeenCalledWith(mockSession);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should handle login error', () => {
    authServiceMock.login.mockReturnValue(throwError(() => new Error('Login failed')));

    component.form.setValue({ email: 'test@example.com', password: 'wrongpassword' });
    component.submit();

    expect(authServiceMock.login).toHaveBeenCalledWith({ email: 'test@example.com', password: 'wrongpassword' });
    expect(component.onError).toBeTruthy();
  });
});
