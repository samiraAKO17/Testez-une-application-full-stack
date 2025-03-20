import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { User } from '../interfaces/user.interface';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Importation de HttpClientTestingModule pour tester les requêtes HTTP
      providers: [UserService],
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController); // Injection de HttpTestingController pour simuler les appels HTTP
  });

  afterEach(() => {
    httpMock.verify(); // Vérification de toutes les requêtes HTTP effectuées dans les tests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch user by id', () => {
    const mockUser: User = {
      id: 123,
      email: 'testuser@example.com',
      lastName: 'User',
      firstName: 'Test',
      admin: false,
      password: 'hashedPassword',
      createdAt: new Date('2021-01-01'),
    };

    service.getById('123').subscribe((user) => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne('api/user/123');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser); // Simulation de la réponse HTTP
  });

  it('should delete user by id', () => {
    const id = '123';
    service.delete(id).subscribe((response) => {
      expect(response).toBeNull(); // On suppose qu'il n'y a pas de réponse (ou autre selon le backend)
    });

    const req = httpMock.expectOne('api/user/123');
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // Simulation de la réponse de suppression
  });
});
