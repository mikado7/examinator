import {openDBInstance, ANSWER_STORE_NAME, QUESTION_STORE_NAME} from '@/db/db';
import { loadQuestion } from '@/db/service/question_service';
import type { Answer } from '@/db/domain/answer';

export async function saveAnswer(answer: Answer): Promise<Answer> {
  const db = await openDBInstance();
  const blob = new Blob([answer.content], { type: 'text/html' });
  const id = await db.put(ANSWER_STORE_NAME, { ...answer, content: blob });
  return loadAnswer(id);
}

export async function loadAnswer(id: IDBValidKey): Promise<Answer> {
  const db = await openDBInstance();
  const answer = await db.get(ANSWER_STORE_NAME, id);
  if (!answer) throw new Error('Answer not found');
  const reader = new FileReader();
  return new Promise((resolve, reject) => {
    reader.onload = () => {
      answer.content = reader.result as string;
      resolve(answer);
    };
    reader.onerror = () => reject(reader.error);
    reader.readAsText(answer.content);
  });
}

export async function loadAllAnswersForQuestion(questionId: IDBValidKey): Promise<Answer[]> {
  const db = await openDBInstance();
  const answers = await db.getAllFromIndex(ANSWER_STORE_NAME, 'questionId', questionId);

  const answerPromises = answers.map(async (answer) => {
    const reader = new FileReader();
    answer.content = await new Promise((resolve, reject) => {
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = () => reject(reader.error);
      reader.readAsText(answer.content as Blob);
    });
    return answer;
  });

  return Promise.all(answerPromises);
}

export async function deleteAnswer(id: number): Promise<void> {
  const db = await openDBInstance();
  await db.delete(ANSWER_STORE_NAME, id);
}
