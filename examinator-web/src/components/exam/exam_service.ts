import type {Exam} from "@/components/exam/exam";

export async function saveExam(exam : Exam): Promise<Exam> {
  const response: Response = await fetch('http://localhost:8080/api/exam', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(exam),
  }).then(response => {
    if (!response.ok) {
      throw new Error('Failed to save question');
    }
    return response;
  });
  return response.json();
}

export async function loadExams(): Promise<Exam[]> {
  const response: Response = await fetch('http://localhost:8080/api/exam').then(response => {
    if (!response.ok) {
      throw new Error('Failed to load exams');
    }
    return response;
  });
  return response.json();
}

export async function loadExam(id: number): Promise<Exam> {
  const response: Response = await fetch(`http://localhost:8080/api/exam/${id}`).then(response => {
    if (!response.ok) {
      throw new Error('Failed to load exam');
    }
    return response;
  });
  return response.json();
}
