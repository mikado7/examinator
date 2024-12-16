import type {Answer} from "@/components/answer/answer";

export interface Question {

  id?: number;
  examId: number;
  sequence: number;
  content: string;
  answers: Answer[];
}
