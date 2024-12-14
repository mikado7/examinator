import type {Question} from "@/question";

export async function saveQuestion(question: Question): Promise<Question> {
  const response: Response = await fetch('http://localhost:8080/api/question', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(question),
  }).then(response => {
    if (!response.ok) {
      throw new Error('Failed to save question');
    }
    return response;
  });
  return await response.json();
}

export async function getQuestion(id: number): Promise<Question> {
  const response: Response = await fetch(`http://localhost:8080/api/question/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  }).then(response => {
    if (!response.ok) {
      throw new Error('Failed to save question');
    }
    return response;
  });

  return await response.json();
}

export async function deleteQuestion(id: number): Promise<void> {
  const response: Response = await fetch(`http://localhost:8080/api/question/${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
  }).then(response => {
    if (!response.ok) {
      throw new Error('Failed to delete question');
    }
    return response;
  });
}
