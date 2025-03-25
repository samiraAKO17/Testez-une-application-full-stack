import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { expect } from '@jest/globals';

import { TestBed } from '@angular/core/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const dummySession: Session = {
    id: 1, 
    name: 'Yoga Class',
    description: 'A relaxing yoga session',
    date: new Date('2025-03-30'), 
    teacher_id: 1,
    users: [], 
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });

    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all sessions', () => {
    service.all().subscribe(sessions => {
      expect(sessions).toEqual([dummySession]);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush([dummySession]);
  });

  it('should fetch a session by id', () => {
    service.detail('1').subscribe(session => {
      expect(session).toEqual(dummySession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(dummySession);
  });

  it('should create a session', () => {
    service.create(dummySession).subscribe(session => {
      expect(session).toEqual(dummySession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dummySession);
    req.flush(dummySession);
  });

  it('should update a session', () => {
    service.update('1', dummySession).subscribe(session => {
      expect(session).toEqual(dummySession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(dummySession);
    req.flush(dummySession);
  });

  it('should delete a session', () => {
    service.delete('1').subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should handle participation in a session', () => {
    const userId = '2';
    service.participate('1', userId).subscribe(() => {
      expect(true).toBe(true);
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should handle unparticipation in a session', () => {
    const userId = '2';
    service.unParticipate('1', userId).subscribe(() => {
      expect(true).toBe(true); // Simplement vérifier que l'appel a réussi
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifie qu'il n'y a pas de requêtes HTTP en attente
  });
});