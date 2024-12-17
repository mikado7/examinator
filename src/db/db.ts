import {openDB} from 'idb';
import {saveQuestion} from "@/db/service/question_service";
import {saveAnswer} from "@/db/service/answer_service";

export const DB_NAME = 'examinatorDB';
export const DB_VERSION = 1;
export const EXAM_STORE_NAME = 'exams';
export const QUESTION_STORE_NAME = 'questions';
export const ANSWER_STORE_NAME = 'answers';

export const openDBInstance = async () => {
  return openDB(DB_NAME, DB_VERSION, {
    upgrade(db) {
      if (!db.objectStoreNames.contains(EXAM_STORE_NAME)) {
        db.createObjectStore(EXAM_STORE_NAME, {keyPath: 'id', autoIncrement: true});
      }
      if (!db.objectStoreNames.contains(QUESTION_STORE_NAME)) {
        const questionStore = db.createObjectStore(QUESTION_STORE_NAME, {keyPath: 'id', autoIncrement: true});
        questionStore.createIndex('examId', 'examId', {unique: false});
      }
      if (!db.objectStoreNames.contains(ANSWER_STORE_NAME)) {
        const answerStore = db.createObjectStore(ANSWER_STORE_NAME, {keyPath: 'id', autoIncrement: true});
        answerStore.createIndex('questionId', 'questionId', {unique: false});
      }
    },
  });
};

export const addExampleData = async () => {
  const db = await openDBInstance();

  const exampleExam = {
    id: 1,
    name: 'Example Exam',
  };

  const exampleQuestions = [
    {
      id: 1,
      examId: 1,
      content: '<p>Question 1 content?</p>',
      sequence: 1,
      answers: []
    },
    {
      id: 2,
      examId: 1,
      content: '<p>Question 2 content?</p>',
      sequence: 2,
      answers: []
    },
    {
      id: 3,
      examId: 1,
      content: '<p>Question 3 content?</p>',
      sequence: 3,
      answers: []
    },
    {
      id: 4,
      examId: 1,
      content: '<p>Question 4 content?</p>',
      sequence: 4,
      answers: []
    },
    {
      id: 5,
      examId: 1,
      content: '<p>Question 5 content?</p>',
      sequence: 5,
      answers: []
    },
    {
      id: 6,
      examId: 1,
      content: '<p>Question 6 content?</p>',
      sequence: 6,
      answers: []
    },
    {
      id: 7,
      examId: 1,
      content: '<p>Question 7 content?</p>',
      sequence: 7,
      answers: []
    },
    {
      id: 8,
      examId: 1,
      content: '<p>Question 8 content?</p>',
      sequence: 8,
      answers: []
    },
    {
      id: 9,
      examId: 1,
      content: '<p>Question 9 content?</p>',
      sequence: 9,
      answers: []
    },
    {
      id: 10,
      examId: 1,
      content: '<p>Question 10 content?</p>',
      sequence: 10,
      answers: []
    },
    {
      id: 11,
      examId: 1,
      content: '<p>Question 11 content?</p>',
      sequence: 11,
      answers: []
    },
    {
      id: 12,
      examId: 1,
      content: '<p>Question 12 content?</p>',
      sequence: 12,
      answers: []
    },
    {
      id: 13,
      examId: 1,
      content: '<p>Question 13 content?</p>',
      sequence: 13,
      answers: []
    },
    {
      id: 14,
      examId: 1,
      content: '<p>Question 14 content?</p>',
      sequence: 14,
      answers: []
    },
    {
      id: 15,
      examId: 1,
      content: '<p>Question 15 content?</p>',
      sequence: 15,
      answers: []
    },
  ];

  const exampleAnswers = [
    {id: 1, questionId: 1, content: '<p>Answer 1 for Question 1</p>', isCorrect: true},
    {id: 2, questionId: 2, content: '<p>Answer 1 for Question 2</p>', isCorrect: false},
    {id: 3, questionId: 2, content: '<p>Answer 2 for Question 2</p>', isCorrect: true},
    {id: 4, questionId: 3, content: '<p>Answer 1 for Question 3</p>', isCorrect: false},
    {id: 5, questionId: 3, content: '<p>Answer 2 for Question 3</p>', isCorrect: false},
    {id: 6, questionId: 3, content: '<p>Answer 3 for Question 3</p>', isCorrect: true},
    {id: 7, questionId: 4, content: '<p>Answer 1 for Question 4</p>', isCorrect: true},
    {id: 8, questionId: 4, content: '<p>Answer 2 for Question 4</p>', isCorrect: false},
    {id: 9, questionId: 4, content: '<p>Answer 3 for Question 4</p>', isCorrect: false},
    {id: 10, questionId: 5, content: '<p>Answer 1 for Question 5</p>', isCorrect: false},
    {id: 11, questionId: 5, content: '<p>Answer 2 for Question 5</p>', isCorrect: true},
    {id: 12, questionId: 5, content: '<p>Answer 3 for Question 5</p>', isCorrect: false},
    {id: 13, questionId: 6, content: '<p>Answer 1 for Question 6</p>', isCorrect: false},
    {id: 14, questionId: 6, content: '<p>Answer 2 for Question 6</p>', isCorrect: true},
    {id: 15, questionId: 6, content: '<p>Answer 3 for Question 6</p>', isCorrect: false},
    {id: 16, questionId: 7, content: '<p>Answer 1 for Question 7</p>', isCorrect: true},
    {id: 17, questionId: 7, content: '<p>Answer 2 for Question 7</p>', isCorrect: false},
    {id: 18, questionId: 7, content: '<p>Answer 3 for Question 7</p>', isCorrect: false},
    {id: 19, questionId: 8, content: '<p>Answer 1 for Question 8</p>', isCorrect: false},
    {id: 20, questionId: 8, content: '<p>Answer 2 for Question 8</p>', isCorrect: true},
    {id: 21, questionId: 8, content: '<p>Answer 3 for Question 8</p>', isCorrect: false},
    {id: 22, questionId: 9, content: '<p>Answer 1 for Question 9</p>', isCorrect: false},
    {id: 23, questionId: 9, content: '<p>Answer 2 for Question 9</p>', isCorrect: true},
    {id: 24, questionId: 9, content: '<p>Answer 3 for Question 9</p>', isCorrect: false},
    {id: 25, questionId: 10, content: '<p>Answer 1 for Question 10</p>', isCorrect: true},
    {id: 26, questionId: 10, content: '<p>Answer 2 for Question 10</p>', isCorrect: false},
    {id: 27, questionId: 10, content: '<p>Answer 3 for Question 10</p>', isCorrect: false},
    {id: 28, questionId: 11, content: '<p>Answer 1 for Question 11</p>', isCorrect: false},
    {id: 29, questionId: 11, content: '<p>Answer 2 for Question 11</p>', isCorrect: true},
    {id: 30, questionId: 11, content: '<p>Answer 3 for Question 11</p>', isCorrect: false},
    {id: 31, questionId: 12, content: '<p>Answer 1 for Question 12</p>', isCorrect: false},
    {id: 32, questionId: 12, content: '<p>Answer 2 for Question 12</p>', isCorrect: true},
    {id: 33, questionId: 12, content: '<p>Answer 3 for Question 12</p>', isCorrect: false},
    {id: 34, questionId: 13, content: '<p>Answer 1 for Question 13</p>', isCorrect: true},
    {id: 35, questionId: 13, content: '<p>Answer 2 for Question 13</p>', isCorrect: false},
    {id: 36, questionId: 13, content: '<p>Answer 3 for Question 13</p>', isCorrect: false},
    {id: 37, questionId: 14, content: '<p>Answer 1 for Question 14</p>', isCorrect: false},
    {id: 38, questionId: 14, content: '<p>Answer 2 for Question 14</p>', isCorrect: true},
    {id: 39, questionId: 14, content: '<p>Answer 3 for Question 14</p>', isCorrect: false},
    {id: 40, questionId: 15, content: '<p>Answer 1 for Question 15</p>', isCorrect: false},
    {id: 41, questionId: 15, content: '<p>Answer 2 for Question 15</p>', isCorrect: true},
    {id: 42, questionId: 15, content: '<p>Answer 3 for Question 15</p>', isCorrect: false},
    {id: 43, questionId: 1, content: '<p>Answer 2 for Question 1</p>', isCorrect: false},
    {id: 44, questionId: 1, content: '<p>Answer 3 for Question 1</p>', isCorrect: false},
    {id: 45, questionId: 2, content: '<p>Answer 3 for Question 2</p>', isCorrect: false},
    {id: 46, questionId: 3, content: '<p>Answer 4 for Question 3</p>', isCorrect: true},
    {id: 47, questionId: 4, content: '<p>Answer 4 for Question 4</p>', isCorrect: false},
    {id: 48, questionId: 5, content: '<p>Answer 4 for Question 5</p>', isCorrect: true},
    {id: 49, questionId: 6, content: '<p>Answer 4 for Question 6</p>', isCorrect: false},
    {id: 50, questionId: 7, content: '<p>Answer 4 for Question 7</p>', isCorrect: true},
    {id: 51, questionId: 8, content: '<p>Answer 4 for Question 8</p>', isCorrect: false},
    {id: 52, questionId: 9, content: '<p>Answer 4 for Question 9</p>', isCorrect: true},
    {id: 53, questionId: 10, content: '<p>Answer 4 for Question 10</p>', isCorrect: false},
    {id: 54, questionId: 11, content: '<p>Answer 4 for Question 11</p>', isCorrect: true},
    {id: 55, questionId: 12, content: '<p>Answer 4 for Question 12</p>', isCorrect: false},
    {id: 56, questionId: 13, content: '<p>Answer 4 for Question 13</p>', isCorrect: true},
    {id: 57, questionId: 14, content: '<p>Answer 4 for Question 14</p>', isCorrect: false},
    {id: 58, questionId: 15, content: '<p>Answer 4 for Question 15</p>', isCorrect: true},
    {id: 59, questionId: 1, content: '<p>Answer 4 for Question 1</p>', isCorrect: false},
    {id: 60, questionId: 2, content: '<p>Answer 4 for Question 2</p>', isCorrect: true},
    {id: 61, questionId: 3, content: '<p>Answer 5 for Question 3</p>', isCorrect: false},
    {id: 62, questionId: 4, content: '<p>Answer 5 for Question 4</p>', isCorrect: true},
    {id: 63, questionId: 5, content: '<p>Answer 5 for Question 5</p>', isCorrect: false},
    {id: 64, questionId: 6, content: '<p>Answer 5 for Question 6</p>', isCorrect: true},
    {id: 65, questionId: 7, content: '<p>Answer 5 for Question 7</p>', isCorrect: false},
    {id: 66, questionId: 8, content: '<p>Answer 5 for Question 8</p>', isCorrect: true},
    {id: 67, questionId: 9, content: '<p>Answer 5 for Question 9</p>', isCorrect: false},
    {id: 68, questionId: 10, content: '<p>Answer 5 for Question 10</p>', isCorrect: true},
    {id: 69, questionId: 11, content: '<p>Answer 5 for Question 11</p>', isCorrect: false},
    {id: 70, questionId: 12, content: '<p>Answer 5 for Question 12</p>', isCorrect: true},
    {id: 71, questionId: 13, content: '<p>Answer 5 for Question 13</p>', isCorrect: false},
    {id: 72, questionId: 14, content: '<p>Answer 5 for Question 14</p>', isCorrect: true},
    {id: 73, questionId: 15, content: '<p>Answer 5 for Question 15</p>', isCorrect: false},
  ];

  const tx = db.transaction([EXAM_STORE_NAME, QUESTION_STORE_NAME, ANSWER_STORE_NAME], 'readwrite');
  const examStore = tx.objectStore(EXAM_STORE_NAME);
  const questionStore = tx.objectStore(QUESTION_STORE_NAME);
  const answerStore = tx.objectStore(ANSWER_STORE_NAME);

  await examStore.put(exampleExam);
  for (const question of exampleQuestions) {
    await saveQuestion(question);
  }
  for (const answer of exampleAnswers) {
    await saveAnswer(answer);
  }

  await tx.done;
};
