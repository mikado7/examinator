import type {Answer} from "@/db/domain/answer";

export interface Question {

  id?: number;
  examId?: number;
  sequence: number;
  content: string;
  answers : Answer[]

}

export interface QuestionAnswers {
  question : Question,
  answers : Answer[]
}
