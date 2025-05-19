describe('Session Details', () => {
  
  it('should display session details for Admin', () => {
    cy.Adminlogin();
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session/2',
      },
      {
        "id": 2,
        "name": "F1",
        "date": "2025-04-24T00:00:00.000+00:00",
        "teacher_id": 2,
        "description": "Lorem Lorem",
        "users": [],
        "createdAt": "2025-04-07T16:56:30",
        "updatedAt": "2025-04-07T16:56:30"
    }).as('2');
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher/2',
      },
      {
        "id": 2,
        "lastName": "THIERCELIN",
        "firstName": "Hélène",
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    }).as('teacher');
    
    cy.contains('button', 'Detail').first().click();

    cy.get('h1, .session-title').should('be.visible');
    cy.contains('Delete').should('exist');
    cy.contains('attendees').should('exist');
    cy.contains('Last update:').should('exist');
    cy.contains('Create at:').should('exist');
  });
  it('should display session details for user', () => {
    cy.login();
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session/2',
      },
      {
        "id": 2,
        "name": "F1",
        "date": "2025-04-24T00:00:00.000+00:00",
        "teacher_id": 2,
        "description": "Lorem Lorem",
        "users": [],
        "createdAt": "2025-04-07T16:56:30",
        "updatedAt": "2025-04-07T16:56:30"
    }).as('2');
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher/2',
      },
      {
        "id": 2,
        "lastName": "THIERCELIN",
        "firstName": "Hélène",
        "createdAt": "2025-03-11T11:15:08",
        "updatedAt": "2025-03-11T11:15:08"
    }).as('teacher');
    
    cy.contains('button', 'Detail').first().click();

    cy.get('h1, .session-title').should('be.visible');
    cy.contains('Participate').should('exist');
    cy.contains('attendees').should('exist');
    cy.contains('Last update:').should('exist');
    cy.contains('Create at:').should('exist');
  });
});
