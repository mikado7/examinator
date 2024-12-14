import type {Answer} from "@/answer";

export interface Question {

  id?: number;
  examId: number;
  sequence?: number;
  content: string;
  answers: Answer[];
}
