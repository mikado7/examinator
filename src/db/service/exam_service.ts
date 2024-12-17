import { openDB } from 'idb'
import { ANSWER_STORE_NAME, EXAM_STORE_NAME, QUESTION_STORE_NAME, openDBInstance } from '@/db/db';
import type {Exam} from "@/db/domain/exam";
import type {Question, QuestionAnswers} from "@/db/domain/question";

export async function saveExam(exam: Exam): Promise<Exam> {
  const db = await openDBInstance();
  const id = await db.put(EXAM_STORE_NAME, exam);
  return loadExam(id);
}

export async function loadExams(): Promise<Exam[]> {
  const db = await openDBInstance();
  return db.getAll(EXAM_STORE_NAME);
}

export async function loadExam(id: IDBValidKey): Promise<Exam> {
  const db = await openDBInstance();
  const exam = await db.get(EXAM_STORE_NAME, id);
  if (!exam) throw new Error('Exam not found');
  return exam;
}

export async function loadExamWithQuestions(id: number): Promise<{ exam: Exam, questions : Question[] }> {
  const db = await openDBInstance();
  const exam = await db.get(EXAM_STORE_NAME, id);
  if (!exam) throw new Error('Exam not found');

  const questions = await db.getAllFromIndex(QUESTION_STORE_NAME, 'examId', id);
  const questionPromises = questions.map(async (question) => {
    const questionReader = new FileReader();
    question.content= await new Promise((resolve, reject) => {
      questionReader.onload = () => resolve(questionReader.result as string);
      questionReader.onerror = () => reject(questionReader.error);
      questionReader.readAsText(question.content);
    });

    const answers = await db.getAllFromIndex(ANSWER_STORE_NAME, 'questionId', question.id);
    const answerPromises = answers.map(async (answer) => {
      const answerReader = new FileReader();
      answer.content = await new Promise((resolve, reject) => {
        answerReader.onload = () => resolve(answerReader.result as string);
        answerReader.onerror = () => reject(answerReader.error);
        answerReader.readAsText(answer.content);
      });
      return answer;
    });

    question.answers = await Promise.all(answerPromises);
    return question;
  });

  const resolvedQuestions = await Promise.all(questionPromises);
  return { exam, questions: resolvedQuestions };
}
