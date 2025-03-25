import { TestBed, ComponentFixture } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { MeComponent } from './me.component';
import { UserService } from '../../services/user.service';
import { SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

jest.mock('../../services/user.service');
jest.mock('../../services/session.service');
jest.mock('@angular/router');
jest.mock('@angular/material/snack-bar');

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockUserService: jest.Mocked<UserService>;
  let mockSessionService: jest.Mocked<SessionService>;
  let mockRouter: jest.Mocked<Router>;
  let mockMatSnackBar: jest.Mocked<MatSnackBar>;

  beforeEach(async () => {
    mockUserService = {
      delete: jest.fn().mockReturnValue(of({})), // Retourne un Observable vide
    } as unknown as jest.Mocked<UserService>;

    mockSessionService = {
      logOut: jest.fn(),
      sessionInformation: { id: 1 } // Simule une session
    } as unknown as jest.Mocked<SessionService>;

    mockRouter = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    mockMatSnackBar = {
      open: jest.fn(),
    } as unknown as jest.Mocked<MatSnackBar>;

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: mockRouter },
        { provide: MatSnackBar, useValue: mockMatSnackBar }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  it('should delete the user and navigate to home', () => {
    component.delete();

    expect(mockUserService.delete).toHaveBeenCalledWith('1'); //  Vérifie que delete est appelé avec l'ID
    expect(mockSessionService.logOut).toHaveBeenCalled(); //  Vérifie que logOut est bien appelé
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']); // Vérifie la navigation
  });
});