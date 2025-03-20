import { ComponentFixture, TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals'; // Importer Jest si nécessaire
import { AppComponent } from './app.component';
import { Router } from '@angular/router';
import { AuthService } from './features/auth/services/auth.service';
import { SessionService } from './services/session.service';
import { of } from 'rxjs';

// Créer des mocks des services
class MockAuthService {}
class MockSessionService {
  $isLogged() {
    return of(true); // Simuler que l'utilisateur est connecté
  }

  logOut() {
    // Simuler la déconnexion
  }
}
class MockRouter {
  navigate(path: string[]) {
    // Simuler la navigation
  }
}

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockSessionService: MockSessionService;
  let mockRouter: MockRouter;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      providers: [
        { provide: AuthService, useClass: MockAuthService },
        { provide: SessionService, useClass: MockSessionService },
        { provide: Router, useClass: MockRouter }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    mockSessionService = TestBed.inject(SessionService) as MockSessionService;
    mockRouter = TestBed.inject(Router) as MockRouter;
    fixture.detectChanges(); // Détecter les changements dans le composant
  });

  it('should create the app component', () => {
    expect(component).toBeTruthy();
  });

  it('should call $isLogged from sessionService and return true', (done) => {
    component.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(true);
      done();
    });
  });

 /* it('should call logout and navigate to home', () => {
    spyOn(mockSessionService, 'logOut');  // Espionner la méthode logOut
    spyOn(mockRouter, 'navigate');  // Espionner la méthode navigate

    component.logout();  // Appeler la méthode logout du composant

    expect(mockSessionService.logOut).toHaveBeenCalled();  // Vérifier que logOut a été appelé
    expect(mockRouter.navigate).toHaveBeenCalledWith(['']);  // Vérifier que la navigation a été effectuée vers l'accueil
  });*/
});
