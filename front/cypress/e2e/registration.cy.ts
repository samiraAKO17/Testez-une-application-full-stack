describe('Register form validation', () => {
  
  beforeEach(() => {
    cy.visit('/register');

  });

  it('should disable submit button if form is invalid', () => {

    cy.get('input[formControlName=firstName]').type('firstName');
    cy.get('input[formControlName=lastName]').type('lastName');
    cy.get('input[formControlName=email]').type('invalid-email');
    cy.get('input[formControlName=password]').type('123');
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should enable submit button if form is valid', () => {
    cy.get('input[formControlName=firstName]').type('firstName');
    cy.get('input[formControlName=lastName]').type('lastName');
    cy.get('input[formControlName=email]').type('valid@email.com');
    cy.get('input[formControlName=password]').type('Test1234!');
    cy.get('button[type=submit]').should('not.be.disabled');
  });

  it('should display error message on failed register', () => {
    // Mocker l'erreur avec intercept
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: { message: 'An error occurred' }
    });
    cy.get('input[formControlName=firstName]').type('firstName');
    cy.get('input[formControlName=lastName]').type('lastName');
    cy.get('input[formControlName=email]').type('test@exists.com');
    cy.get('input[formControlName=password]').type('Test1234!');
    cy.get('button[type=submit]').click();

    cy.contains('An error occurred').should('be.visible');
  });

  it('should redirect to login after successful registration', () => {

    cy.intercept('POST', '/api/auth/register', {
      statusCode: 200
    }).as('successfullRegistration');

     cy.get('input[formControlName=firstName]').type("User")
    cy.get('input[formControlName=lastName]').type("Test")
    cy.get('input[formControlName=email]').type("test@example.com")
    cy.get('input[formControlName=password]').type("password{enter}{enter}")
    cy.get('span.error').should('not.exist');

    cy.wait('@successfullRegistration');
    
    cy.url().should('to.match', /\/login$/)
  }); 
});
