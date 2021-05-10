import StatementQuestionDetails from '@/models/statement/questions/StatementQuestionDetails';
import { QuestionTypes } from '@/services/QuestionHelpers';

export default class OpenAnswerStatementQuestionDetails extends StatementQuestionDetails {
  correctAnswer: string | null = null;

  constructor(jsonObj?: OpenAnswerStatementQuestionDetails) {
    super(QuestionTypes.OpenAnswer);
    if (jsonObj) {
      this.correctAnswer = jsonObj.correctAnswer || this.correctAnswer;
    }
  }
}
