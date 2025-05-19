describe('Login form validation', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('should show error when email is missing', () => {
    // Remplir uniquement le champ password
    cy.get('input[formControlName=password]').type('test!1234');
    
    // Vérifier que le bouton submit est désactivé
    cy.get('button[type=submit]').should('be.disabled');
    
  });

  it('should show error when password is missing', () => {
    // Remplir uniquement le champ email
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    
    // Vérifier que le bouton submit est désactivé
    cy.get('button[type=submit]').should('be.disabled');
    
    cy.url().should('include', '/login');
  });
  it('should show error when email format is invalid', () => {
    // Remplir un email invalide et un mot de passe valide
    cy.get('input[formControlName=email]').type('not-an-email');
    cy.get('input[formControlName=password]').type('test!1234');
    
    // Vérifier que le bouton submit n'est pas activé
    cy.get('button[type=submit]').should('be.disabled');
    
    cy.url().should('include', '/login');
  });
});