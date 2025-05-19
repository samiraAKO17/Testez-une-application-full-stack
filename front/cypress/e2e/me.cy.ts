describe('Profile', () => {
  beforeEach(() => {
    cy.visit('/login')

    cy.visit('/me');

  });

  it('should display Admin data', () => {
   
    cy.Adminlogin();
    cy.intercept(
      {
        method: 'GET',
        url: '/api/user/1',
      },
      {
        "id": 1,
        "email": "yoga@studio.com",
        "lastName": "Admin",
        "firstName": "Admin",
        "admin": true,
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    }).as('me')
     cy.contains('span.link','Account').click();
    // cy.url().should('include', '/me'); 
    // cy.contains('User information').should('exist');
    cy.url().should('to.match', /\/me$/);
        cy.contains('h1', 'User information')
        cy.contains('p', 'ADMIN')
        cy.contains('p', 'Email: yoga@studio.com')
        cy.contains('p', 'You are admin')
  });

  it('should display user data', () => {
   
    cy.login();
    cy.intercept(
      {
        method: 'GET',
        url: '/api/user/3',
      },
      {
        "id": 3,
        "email": "samiraako17@gmail.com",
        "lastName": "Samira",
        "firstName": "Abdoulkarim",
        "admin": false,
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    }).as('me')
     cy.contains('span.link','Account').click();
     cy.url().should('include', '/me'); 
     cy.contains('User information').should('exist');
     cy.url().should('to.match', /\/me$/);
        cy.contains('h1', 'User information')
        cy.contains('p', 'Email: samiraako17@gmail.com')
        cy.contains('p', 'Delete my account')
  });
});
