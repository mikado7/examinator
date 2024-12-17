import { openDB } from 'idb';

export const DB_NAME = 'examinatorDB';
export const DB_VERSION = 1;
export const EXAM_STORE_NAME = 'exams';
export const QUESTION_STORE_NAME = 'questions';
export const ANSWER_STORE_NAME = 'answers';

export const openDBInstance = async () => {
  return openDB(DB_NAME, DB_VERSION, {
    upgrade(db) {
      if (!db.objectStoreNames.contains(EXAM_STORE_NAME)) {
        db.createObjectStore(EXAM_STORE_NAME, { keyPath: 'id', autoIncrement: true });
      }
      if (!db.objectStoreNames.contains(QUESTION_STORE_NAME)) {
        const questionStore = db.createObjectStore(QUESTION_STORE_NAME, { keyPath: 'id', autoIncrement: true });
        questionStore.createIndex('examId', 'examId', { unique: false });
      }
      if (!db.objectStoreNames.contains(ANSWER_STORE_NAME)) {
        const answerStore = db.createObjectStore(ANSWER_STORE_NAME, { keyPath: 'id', autoIncrement: true });
        answerStore.createIndex('questionId', 'questionId', { unique: false });
      }
    },
  });
};
