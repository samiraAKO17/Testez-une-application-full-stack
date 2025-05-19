import { enableProdMode, isDevMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .then(async moduleRef => {
    if (isDevMode() || (window as any).Cypress) {
      const injector = moduleRef.injector;

      const { SessionService } = await import('./app/services/session.service');

      const sessionService = injector.get(SessionService);
      (window as any).sessionService = sessionService;
    }
  })
  .catch(err => console.error(err));
