describe('Annuler une participation', () => {
    beforeEach(() => {
      
      cy.login();
      
    });
  
    it('should unparticipate in a session', () => {
        //intercepter session 2 avant click sur detail
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
              "users": [3],
              "createdAt": "2025-04-07T16:56:30",
              "updatedAt": "2025-04-07T16:56:30"
          }).as('detail');
     cy.contains('button span', 'Detail').click();

    cy.get('h1, .session-title').should('be.visible');
        
      //intercepter le rafraichissement de la session avant l'appel de session et teacher pour observer le changement
    cy.intercept('Delete', 'api/session/2/participate/3', {}).as('participate');

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
            "updatedAt": "2025-04-23T11:44:42"
        }).as('update');
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
       cy.contains('button','Do not participate').click();
      
       cy.contains('Participate').should('exist');
    });
  });
  