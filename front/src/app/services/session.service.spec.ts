import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';


describe('SessionService', () => {
  let service: SessionService;
  let mockUser: SessionInformation;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);

    // Initialisation de mockUser pour chaque test
    mockUser = {
      id: 123,
      token: 'someToken',
      type: 'Bearer',
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
    

  it('should set isLogged to true and store session information on logIn', () => {
    
    service.logIn(mockUser);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(mockUser);
  });

  it('should set isLogged to false and clear session information on logOut', () => {
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit the correct isLogged value', (done) => {
    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(false);
      done();
    });
  });

  it('should update isLoggedSubject when logIn is called', (done) => {
    service.logIn(mockUser);

    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(true);
      done();
    });
  });

  it('should update isLoggedSubject when logOut is called', (done) => {
    service.logIn(mockUser);
    service.logOut();

    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(false);
      done();
    });
  });
});
