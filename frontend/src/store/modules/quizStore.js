import { getQuizWords } from "@/api/quiz";
import { sendMessageWord } from "@/api/chatGPT";

import store from "@/store/index.js";

const quizStore = {
  namespaced: true,
  state: {
    questions: [], // Quiz 문제
    similarityWord: [], // 경제단어와 유사한 단어 3개
    todayQuizFlag: false, // 금일 오늘의 Quiz 진행 여부
    index: 0, // Quiz index
    isPass: false, // Quiz 통과 여부
    experience: 0, // 사용자 경험치
    correctCount: 0, // 맞은 Quiz 개수
  },
  getters: {
    getQuestions: (state) => {
      return state.questions;
    },
    // #21#
    getSimilarityWord: (state) => {
      return state.similarityWord;
    },
    getIndex: (state) => {
      return state.index;
    },
    getExperience: (state) => {
      return state.experience;
    },
  },
  mutations: {
    SET_QUESTIONS: (state, questions) => {
      state.questions = questions;

      // # for. quiz Loading 창
      store.commit("QUIZ_LOADING_STATUS", false);
    },
    SET_SIMILARITY_WORD: (state, similarityWord) => {
      state.similarityWord = similarityWord;
    },
    SET_INDEX: (state, index) => {
      state.index = index;
    },
    SET_TODAY_QUIZ_FLAG: (state, todayQuizFlag) => {
      state.todayQuizFlag = todayQuizFlag;
    },
    SET_ISPASS: (state, isPass) => {
      state.isPass = isPass;
    },
    SET_EXPERIENCE: (state, experience) => {
      state.experience = experience;
    },
    SET_CORRECTCOUNT: (state, correctCount) => {
      state.correctCount = correctCount;
    },
  },
  actions: {
    // [@Method] Quiz 문제 출제
    async setExamQuestions({ commit, state }) {
      // !FIX! 나중에 로그인 완료되면 현 login ID 붙이기
      const info = {
        userId: 1,
      };

      await getQuizWords(
        info,
        async ({ data }) => {
          // console.log("#21# getQuizWords 실행결과: ", data);
          // i) 성공
          if (data.statusCode == 200) {
            // console.log("#21# Quiz 단어 가져오기 성공: ", data);
            // console.log("#21# Quiz 단어 가져오기 성공: ", data.data[0]);

            // Quiz 제작
            const quiz = []; // Quiz
            // 1) 가져온 경제용어로 문제 만들기
            for (const word of data.data) {
              const quizItem = new Object();
              const answers = new Object();
              const randomNum = Math.floor(Math.random() * (102 - 98) + 97); // 97-100 중 Random 숫자 뽑기 (for. 정답 자리), [a:97, b:98, c:99, d:100]

              // i) 문제, 정답 번호 setting
              quizItem.question = word.wordExpl;
              quizItem.correctAnswer = String.fromCharCode(randomNum);
              // ii) 4지선다
              // - [호출] chatGPT로 유사한 단어 3개 가져오기
              await store.dispatch("quizStore/excuteSendMessage", word.word, {
                root: true,
              });
              // console.log(
              //   "#21# store에 있는 유사단어 확인: ",
              //   state.similarityWord
              // );
              // - 4지선다 setting
              let cnt = 0;
              for (let i = 97; i <= 100; i++) {
                if (i == randomNum) {
                  answers[String.fromCharCode(randomNum)] = word.word;
                } else {
                  answers[String.fromCharCode(i)] = state.similarityWord[cnt];
                  cnt++;
                }
              }
              quizItem.answers = answers;
              quiz.push(quizItem);
            }
            // console.log("#21# quiz 확인: ", quiz);
            await commit("SET_QUESTIONS", quiz);
            // 이후 TodayQuizInfo 페이지에서 TodayQuiz 페이지로 이동
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] chatGPT에게 해당 경제 단어와 유사한 단어 3개 조회 질문
    async excuteSendMessage({ commit }, word) {
      // # for. quiz Loading 창
      store.commit("QUIZ_LOADING_STATUS", true);

      // console.log("#21# chatGPT 질문 동작 word: ", word);
      const message =
        "경제용어 " +
        word +
        "와 유사한 경제용어 3개 설명없이 단어만 1, 2, 3으로 출력해줘";

      await sendMessageWord(
        message,
        async ({ data }) => {
          // console.log(
          //   "#21# chatGPT 질문 실행결과: ",
          //   data.choices[0].message.content
          // );
          // 경제단어 추출 [정규식 사용]
          // const regex = /(?:\d\. )(.+?)(?=\(\n\d|\n|$)/g; // 영어 포함 버전
          const regex = /(?:\d\. )(.+?)(?=\(|\n|$)/g;
          var similarityWord = [];
          let match;
          while (
            (match = regex.exec(data.choices[0].message.content)) !== null
          ) {
            // console.log("#21# 단어 추출 확인: ", match[1]);
            similarityWord.push(match[1]);
          }
          commit("SET_SIMILARITY_WORD", similarityWord);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] index 증가 (Quiz index)
    increaseIndex({ commit }, value) {
      //   console.log("#21# index 확인: ", value);
      commit("SET_INDEX", value + 1);
    },
    // [@Method] Quiz 통과 여부 반영
    setQuizResult({ commit }, correctAnswerCount) {
      //   console.log("#21# Quiz 통과 여부 확인: ", correctAnswerCount);
      commit("SET_CORRECTCOUNT", correctAnswerCount);

      if (correctAnswerCount >= 5) {
        commit("SET_ISPASS", true);
      } else {
        commit("SET_ISPASS", false);
      }
    },
    // [@Method] Quiz 끝 + 초기화
    initQuiz({ commit }) {
      commit("SET_QUESTIONS", []);
      commit("SET_INDEX", 0);
      commit("SET_TODAY_QUIZ_FLAG", true);
      commit("SET_ISPASS", false);
      commit("SET_EXPERIENCE", 0);
      commit("SET_CORRECTCOUNT", 0);
    },
  },
  modules: {},
};

export default quizStore;