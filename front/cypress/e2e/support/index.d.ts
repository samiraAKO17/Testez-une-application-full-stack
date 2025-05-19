declare namespace Cypress {
    interface Chainable {
      login(): Chainable<voidd>;
      register(): Chainable<void>;
      logout(): Chainable<void>;
    }
  }