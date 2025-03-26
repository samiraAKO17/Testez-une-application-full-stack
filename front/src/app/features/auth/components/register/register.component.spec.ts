import { ComponentFixture, TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('RegisterComponent (Integration Test)', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([])
      ],
      providers: [AuthService]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  it('should register a user and redirect to login', () => {
    const spyRegister = jest.spyOn(authService, 'register').mockReturnValue(of(void 0));
    const spyNavigate = jest.spyOn(router, 'navigate');
  
    component.form.setValue({
      email: 'user@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'securePass123'
    });
  
    component.submit(); // Exécute directement la soumission du formulaire
  
    expect(spyRegister).toHaveBeenCalledWith({
      email: 'user@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'securePass123'
    });
  
    expect(spyNavigate).toHaveBeenCalledWith(['/login']);
  });
});  