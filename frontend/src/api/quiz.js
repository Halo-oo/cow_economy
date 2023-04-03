import { apiInstance } from "./index.js";

const api = apiInstance();

// [GET] 오늘의 Quiz 도전 가능/불가능 여부 조회
async function checkTodayQuizDone(success, fail) {
  await api.get(`/quiz/check`).then(success).catch(fail);
}

// [POST] 오늘의 Quiz로 출제할 경제 단어 가져오기
async function getQuizWords(success, fail) {
  await api.post(`/quiz`).then(success).catch(fail);
}

// [POST] chatGPT에게 유사 경제단어 질문
async function sendMessageWord(info, success, fail) {
  await api
    .post(`/chatGPT/ask-word`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}

// [POST] Quiz 결과 저장 & 성공 시 경험치 획득
async function setQuizResult(info, success, fail) {
  await api
    .post(`/quiz/set-result`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}

export { checkTodayQuizDone, getQuizWords, sendMessageWord, setQuizResult };
