import {searchNews} from '@/api/modules/article.js';
const newsStore = {
  namespaced: true,
  state: {
    beforeSearch: false,
    searchText: "",
    searched: false,
    news: [],
    categoryLast: {
      finance: Number.MAX_SAFE_INTEGER + 1,
      stock: Number.MAX_SAFE_INTEGER + 1,
      industry: Number.MAX_SAFE_INTEGER + 1,
      venture: Number.MAX_SAFE_INTEGER + 1,
      estate: Number.MAX_SAFE_INTEGER + 1,
      worldwide: Number.MAX_SAFE_INTEGER + 1,
      life: Number.MAX_SAFE_INTEGER + 1,
      common: Number.MAX_SAFE_INTEGER + 1,
    }
  },
  mutations: {
    SET_BEFORE_SEARCH(state, payload) {
      state.beforeSearch = payload;
    },
    SET_SEARCH_TEXT(state, payload) {
      console.log(payload);
      state.searchText = payload;
    },
    SET_SEARCHED(state, payload) {
      state.searched = payload;
    },
    SET_NEWS(state, news) {
      state.news = news;
    },
    SET_CATEGORYLAST(state, categoryLast) {
      // console.log("경제: "+categoryLast[0]);
      // console.log("증권: "+categoryLast[1]);
      // console.log("산업: "+categoryLast[2]);
      // console.log("벤처: "+categoryLast[3]);
      // console.log("부동산: "+categoryLast[4]);
      // console.log("글로벌: "+categoryLast[5]);
      // console.log("생활: "+categoryLast[6]);
      // console.log("일반: "+categoryLast[7]);

      state.categoryLast.finance = categoryLast[0];
      state.categoryLast.stock = categoryLast[1];
      state.categoryLast.industry = categoryLast[2];
      state.categoryLast.venture = categoryLast[3];
      state.categoryLast.estate = categoryLast[4];
      state.categoryLast.global = categoryLast[5];
      state.categoryLast.life = categoryLast[6];
      state.categoryLast.common = categoryLast[7];
    },
  },
  actions: {
    init({commit}){
      commit("SET_NEWS", []); 
      commit("SET_SEARCH_TEXT", "");
      commit("SET_SEARCHED", false);
      commit("SET_BEFORE_SEARCH", false);
    },
    setSearchText({ commit }, payload) {
      commit("SET_SEARCH_TEXT", payload);
    },
    setSearched({ commit }, payload) {
      commit("SET_SEARCHED", payload);
    },
    setBeforeSearch({ commit }, payload) {
      commit("SET_BEFORE_SEARCH", payload);
    },
    async setNews({commit}, param){
      await searchNews(param,
        async({data}) => {
          if(data.data.articles.length>0){
            commit("SET_SEARCHED", true);
          }
          commit("SET_BEFORE_SEARCH", true);
          commit("SET_NEWS",data.data.articles);
          commit("SET_CATEGORYLAST", data.data.categoryLast);
        }
      ),
      (error) => {
        console.log(error);
      }
    }
  },
  getters: {
    allNews(state) {
      return state.news;
    },
    searchNews(state) {
      if (!state.searchText || state.news === []) {
        return [];
      }
      return state.news;
    },
  },
};

export default newsStore;
