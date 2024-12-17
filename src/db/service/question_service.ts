import type {Question} from '@/db/domain/question';
import {openDBInstance, QUESTION_STORE_NAME} from '@/db/db';
import {loadAllAnswersForQuestion, saveAnswer} from "@/db/service/answer_service";
import type {Answer} from "@/db/domain/answer";
import {useRouter} from "vue-router";

const router = useRouter()

export async function saveQuestion(question: Question): Promise<Question> {
  const {answers, content, ...questionWithoutAnswers} = question;
  const db = await openDBInstance();
  const blob = new Blob([question.content], {type: 'text/html'});
  const id = await db.put(QUESTION_STORE_NAME, {...questionWithoutAnswers, content: blob});

  const answerPromises = answers.map(async (answer: Answer) => {
    answer.questionId = id as number;
    return saveAnswer(answer);
  });
  await Promise.all(answerPromises);
  return loadQuestion(id);
}

export async function loadQuestion(id: IDBValidKey): Promise<Question> {
  const db = await openDBInstance();
  const question = await db.get(QUESTION_STORE_NAME, id);
  if (!question) throw new Error('Question not found');

  const reader = new FileReader();
  question.content = await new Promise((resolve, reject) => {
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = () => reject(reader.error);
    reader.readAsText(question.content as Blob);
  });

  question.answers = await loadAllAnswersForQuestion(id);

  return question;
}

export async function deleteQuestion(id: number): Promise<void> {
  const db = await openDBInstance();
  await db.delete(QUESTION_STORE_NAME, id);
}
