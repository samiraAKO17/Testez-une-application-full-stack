describe('logout', () => {
    beforeEach(() => {
      cy.login();
    });
  
    it('should log out and redirect to login', () => {
      cy.logout(); // Adapter au bon sélecteur
      cy.url().should('include', '/');
    });
  });
  