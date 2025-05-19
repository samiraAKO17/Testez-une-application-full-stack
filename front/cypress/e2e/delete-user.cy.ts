describe('Suppression du compte utilisateur', () => {
    beforeEach(() => {
      cy.login();
    });
  
    it('should delete user acccount and redirect to /', () => {
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
      cy.intercept('DELETE', '/api/user/3', {
        statusCode: 204,
        }).as('deleteAccount');
      cy.contains('button','Detail').click();
      cy.url().should('include', '/');
      cy.contains('Your account has been deleted !').should('exist');
    });
  });
  