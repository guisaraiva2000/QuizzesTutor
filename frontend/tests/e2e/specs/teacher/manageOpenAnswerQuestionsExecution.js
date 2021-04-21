describe('Manage Open Answer Questions Walk-through', () => {
    function validateQuestion(title, content, correctAnswer) {
        cy.get('[data-cy="showQuestionDialog"]')
            .should('be.visible')
            .within(($ls) => {
                cy.get('.headline').should('contain', title);
                cy.get('span > p').should('contain', content);
                cy.get('span > p').should('contain', correctAnswer);
            });
    }

    function validateQuestionFull(title, content, correctAnswer) {
        cy.log('Validate question with show dialog.');

        cy.get('[data-cy="questionTitleGrid"]').first().click();

        validateQuestion(title, content, correctAnswer);

        cy.get('button').contains('close').click();
    }

    before(() => {
        cy.cleanCodeFillInQuestionsByName('Cypress Question Example');
        cy.cleanMultipleChoiceQuestionsByName('Cypress Question Example');
        cy.cleanOpenAnswerQuestionsByName('Cypress Question Example');
    });
    after(() => {
        cy.cleanOpenAnswerQuestionsByName('Cypress Question Example');
    });

    beforeEach(() => {
        cy.demoTeacherLogin();
        cy.route('GET', '/courses/*/questions').as('getQuestions');
        cy.route('GET', '/courses/*/topics').as('getTopics');
        cy.get('[data-cy="managementMenuButton"]').click();
        cy.get('[data-cy="questionsTeacherMenuButton"]').click();

        cy.wait('@getQuestions').its('status').should('eq', 200);

        cy.wait('@getTopics').its('status').should('eq', 200);
    });

    afterEach(() => {
        cy.logout();
    });

    it('Creates a new open answer question', function () {
        cy.get('button').contains('New Question').click();

        cy.get('[data-cy="createOrEditQuestionDialog"]')
            .parent()
            .should('be.visible');

        cy.get('span.headline').should('contain', 'New Question');

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

        cy.wait('@postQuestion').its('status').should('eq', 200);

        cy.get('[data-cy="questionTitleGrid"]')
            .first()
            .should('contain', 'Cypress Question Example - 01');

        validateQuestionFull(
            'Cypress Question Example - 01',
            'Cypress Question Example - Content - 01',
            'Cypress Question Example - Correct Answer - 01'
        );
    });
});
