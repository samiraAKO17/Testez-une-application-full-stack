describe('Login spec', () => {
  it('Login successfull Admin', () => {
    cy.Adminlogin();
    cy.url().should('include', '/sessions')
  })
  it('Login successfull user', () => {
    cy.login();
    cy.url().should('include', '/sessions')
  })
});