import type {Question} from "@/question";

export interface Exam {
  id?: number;
  name: string;
  questions?: Question[];
}
