describe('Not Found Page', () => {
  it('should show 404 for unknown route', () => {
    cy.visit('/non-existent-route', { failOnStatusCode: false });
    cy.contains('Page not found').should('exist'); 
  });
});
