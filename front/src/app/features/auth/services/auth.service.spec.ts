import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from '../../../interfaces/sessionInformation.interface';

describe('AuthService (Integration Test)', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifie qu'aucune requête n'est en attente
  });

  it('should register a user (integration test)', () => {
    const registerRequest: RegisterRequest = {
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    };

    service.register(registerRequest).subscribe(response => {
      expect(response).toBeUndefined(); // L'API renvoie `void`
    });

    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registerRequest);
    req.flush(null); // Simule une réponse vide
  });

  it('should login a user (integration test)', () => {
    const loginRequest: LoginRequest = {
      email: 'test@example.com',
      password: 'password123'
    };

    const mockSessionInfo: SessionInformation = {
      token: 'fake-token',
      type: 'Bearer',
      id: 1,
      username: 'john_doe',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    };

    service.login(loginRequest).subscribe(response => {
      expect(response).toEqual(mockSessionInfo);
    });

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(loginRequest);
    req.flush(mockSessionInfo); // Simule une réponse API avec session
  });

  it('should handle login error (wrong credentials)', () => {
    const loginRequest: LoginRequest = {
      email: 'wrong@example.com',
      password: 'wrongpassword'
    };
  
    service.login(loginRequest).subscribe({
      next: () => fail('Expected error, but got success response'),
      error: (error) => {
        expect(error.status).toBe(401);
        expect(error.error).toEqual({ message: 'Invalid credentials' });
      }
    });
  
    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
  
    // Simuler une réponse API avec une erreur 401
    req.flush({ message: 'Invalid credentials' }, { status: 401, statusText: 'Unauthorized' });
  });
  
});