describe('Student walkthrough', () => {
  beforeEach(() => {
    cy.demoStudentLogin();
    cy.seeTournamentsLists('All');
  });

  afterEach(() => {
    cy.logout();
  });

  it('login sees open tournaments', () => {
    cy.seeTournamentsLists('Open');
  });

  it('login sees closed tournaments', () => {
    cy.seeTournamentsLists('Closed');
  });

  it('login sees my tournaments', () => {
    cy.seeTournamentsLists('My');
  });

  it('login creates a tournament', () => {
    cy.createTournament('3');
  });

  it('login creates private tournament', () => {
    cy.createPrivateTournament('3');
  });
});
