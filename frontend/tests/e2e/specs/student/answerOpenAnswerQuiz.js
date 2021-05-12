describe('Student walkthrough', () => {
    before( () => {
        cy.demoTeacherLogin();
        cy.route('GET', '/courses/*/questions').as('getQuestions');
        cy.route('GET', '/courses/*/topics').as('getTopics');
        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="questionsTeacherMenuButton"]').click();

        cy.get('button').contains('New Question').click();

        cy.get(
            '[data-cy="questionTitleTextArea"]'
        ).type('Cypress Question Example - 01',
            {force: true});
        cy.get(
            '[data-cy="questionQuestionTextArea"]'
        ).type(
            'Cypress Question Example - Content - 01',
            {force: true}
        );

        cy.get(
            '[data-cy="questionTypeInput"]'
        ).type('open_answer', {force: true})
            .click({force: true});

        cy.wait(1000);

        cy.get(
            '[data-cy="correctAnswerTextArea"]'
        ).type(
            'Cypress Question Example - Correct Answer - 01',
            {force: true,}
        );

        cy.route('POST', '/courses/*/questions/').as('postQuestion');

        cy.get('button').contains('Save').click();

        cy.wait(1000);

        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="quizzesTeacherMenuButton"]').click();

        cy.get('button').contains('New Quiz').click();

        cy.get(
            '[data-cy="quizTitleTextArea"]'
        ).type('Cypress Quiz Example',
            {force: true});

        cy.get('[id="availableDateInput-input"]').click();

        cy.get('button').contains('Now').click();

        cy.contains('Cypress Question Example - 01')
            .parent()
            .parent()
            .children()
            .find('[data-cy="addToQuizButton"]')
            .click();

        cy.get('[data-cy="saveQuizButton"]').click();

        cy.wait(1000);

        cy.logout();
    });

    after(() => {
        cy.cleanOpenAnswerQuizByName('Cypress Quiz Example');
        cy.cleanOpenAnswerQuestionsByName('Cypress Question Example');
    });

    beforeEach(() => {
        cy.visit('/');
        cy.get('[data-cy="demoNewStudentLoginButton"]').click();

        cy.get('[data-cy="quizzesStudentMenuButton"]').click();
        cy.contains('Available').click();

        cy.contains('Cypress Quiz Example').click();
    });

    afterEach(() => {
        cy.logout();
    });

    it('Student answers correctly', function () {
        cy.get('p').should('contain', 'Cypress Question Example - Content - 01');

        cy.get('[data-cy="answerTextArea"]').type('Cypress Question Example - Correct Answer - 01');

        cy.get('[data-cy="endQuizButton"]').click();
        cy.get('[data-cy="confirmationButton"]').click();

        cy.wait(5000);

        cy.get('[data-cy="studentAnswer"]').should('contain', 'Cypress Question Example - Correct Answer - 01');

        cy.get('[data-cy="correctAnswer"]').should('contain', 'Cypress Question Example - Correct Answer - 01');
    });

    it('Student answers incorrectly', function () {
        cy.get('p').should('contain', 'Cypress Question Example - Content - 01');

        cy.get('[data-cy="answerTextArea"]').type('Cypress Question Example - Incorrect Answer - 01');

        cy.get('[data-cy="endQuizButton"]').click();
        cy.get('[data-cy="confirmationButton"]').click();

        cy.wait(5000);

        cy.get('[data-cy="studentAnswer"]').should('contain', 'Cypress Question Example - Incorrect Answer - 01');

        cy.get('[data-cy="correctAnswer"]').should('contain', 'Cypress Question Example - Correct Answer - 01');
    });

});