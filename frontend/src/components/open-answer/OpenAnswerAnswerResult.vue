<template>
  <div class="open-answer-answer-result">
    <p></p>
    <p class="open-answer-header text-left">Your Answer:</p>
    <v-card class="answer text-left" outlined tile>
      <div>
        <span
          data-cy="studentAnswer"
          style="white-space: pre-line"
          v-html="answerDetails.answer" />
      </div>
    </v-card>
    <p></p>
    <p></p>
    <p class="open-answer-correct-answer-header text-left">Correct Answer:</p>
    <v-card class="open-answer-correct-answer text-left" outlined tile>
      <div>
        <span
          data-cy="correctAnswer"
          style="white-space: pre-line"
          v-html="correctAnswerDetails.correctAnswer"
        />
      </div>
    </v-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { convertMarkDown } from '@/services/ConvertMarkdownService';
import Image from '@/models/management/Image';
import OpenAnswerStatementAnswerDetails from '@/models/statement/questions/OpenAnswerStatementAnswerDetails';
import OpenAnswerStatementCorrectAnswerDetails from '@/models/statement/questions/OpenAnswerStatementCorrectAnswerDetails';

@Component
export default class OpenAnswerAnswerResult extends Vue {
  @Prop(OpenAnswerStatementAnswerDetails)
  readonly answerDetails!: OpenAnswerStatementAnswerDetails;
  @Prop(OpenAnswerStatementCorrectAnswerDetails)
  readonly correctAnswerDetails?: OpenAnswerStatementCorrectAnswerDetails;

  convertMarkDown(text: string, image: Image | null = null): string {
    return convertMarkDown(text, image);
  }
}
</script>
