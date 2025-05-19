
Cypress.Commands.add('login', () => {
  cy.intercept('POST', '/api/auth/login', {
    body: {
      id:3,
      username: 'samiraako17@gmail.com',
      firstName: 'Samira',
      lastName: 'Abdoulkarim',
      admin: false
    }
  }).as('login');

  cy.intercept('GET', '/api/session', [
    {
        "id": 2,
        "name": "F1",
        "date": "2025-04-24T00:00:00.000+00:00",
        "teacher_id": 2,
        "description": "Lorem Lorem",
        "users": [3],
        "createdAt": "2025-04-07T16:56:30",
        "updatedAt": "2025-04-07T16:56:30"
    },
    {
        "id": 3,
        "name": "F2",
        "date": "2025-04-30T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Lorem Lorem",
        "users": [3],
        "createdAt": "2025-04-07T16:57:17",
        "updatedAt": "2025-04-07T16:57:17"
    }]).as('getSessions');

  cy.visit('/login');
  cy.get('input[formControlName=email]').type('samiraako17@gmail.com');
  cy.get('input[formControlName=password]').type('123456{enter}{enter}');
});
Cypress.Commands.add('Adminlogin', () => {
  cy.intercept('POST', '/api/auth/login', {
    body: {
      id: 1,
      email: 'yoga@studio.com',
      firstName: 'Admin',
      lastName: 'Admin',
      admin: true
    },
  }).as('login');

  cy.intercept('GET', '/api/session', [
    {
        "id": 2,
        "name": "F1",
        "date": "2025-04-24T00:00:00.000+00:00",
        "teacher_id": 2,
        "description": "Lorem Lorem",
        "users": [3],
        "createdAt": "2025-04-07T16:56:30",
        "updatedAt": "2025-04-07T16:56:30"
    },
    {
        "id": 3,
        "name": "F2",
        "date": "2025-04-30T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Lorem Lorem",
        "users": [3],
        "createdAt": "2025-04-07T16:57:17",
        "updatedAt": "2025-04-07T16:57:17"
    }]).as('getSessions');

  cy.visit('/login');
  cy.get('input[formControlName=email]').type('samiraako17@gmail.com');
  cy.get('input[formControlName=password]').type('123456{enter}{enter}');
});
Cypress.Commands.add('logout', () => {
  // On clique sur le lien "Logout"
  cy.get('span').contains('Logout').click();

  // Redirection vers la page de login ou d'accueil ?
  cy.url().should('include', '/');
});