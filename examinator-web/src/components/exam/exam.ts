import type {Question} from "@/components/question/question";

export interface Exam {
  id?: number;
  name: string;
  questions?: Question[];
}
