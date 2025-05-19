describe('Create Session', () => {
  
  beforeEach(() => {
      cy.Adminlogin();  
    });
  it('should create  a session and display sessions with created session', () => {

    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher',
      },
      [{
        "id": 1,
        "lastName": "DELAHAYE",
        "firstName": "Margot",
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    },
    {
        "id": 2,
        "lastName": "THIERCELIN",
        "firstName": "Hélène",
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    }]).as('teacher');
    cy.contains('button','Create').click();
    cy.get('input[formControlName="name"]').type('Séance Cypress');
    cy.get('input[formControlName="date"]').type('2025-05-01');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').first().click();
    cy.get('textarea[formControlName="description"]').type('Test session créée via Cypress.');

    cy.get('button[type="submit"]').click();

    cy.url().should('include', '/sessions');

    cy.intercept('POST', 'api/session', {        statusCode: 204,
    }).as('create');

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
      },
      {
          "id": 4,
          "name": "Séance Cypress",
          "date": "2025-04-30T00:00:00.000+00:00",
          "teacher_id": 1,
          "description": "Test session créée via Cypress.",
          "users": [3],
          "createdAt": "2025-04-07T16:57:17",
          "updatedAt": "2025-04-07T16:57:17"
      }]).as('getSessions');

    cy.get('button[type=submit]').should('not.be.disabled');
    cy.get('button[type=submit]').click();
    cy.contains('Test session créée via Cypress.');
    cy.contains('Session created !');

  });
});
