import { apiInstance } from "../index";
import { getReferenceHTML } from "@/common/function/textSelection.js";

// api instance 가져오기
const api = apiInstance();

// [GET /user/info] 기사 상세정보 조회
async function getUserInfo() {
  var result = null;
  await api.get(`/user/info`).then((res) => {
    if (res.data.statusCode == 200) {
      result = res.data.data;

      const articleList = result.memoDtoList;
      if (articleList) {
        for (var i = 0; i < articleList.length; i++) {
          var memoList = articleList[i].memoList;
          if (memoList) {
            for (var j = 0; j < memoList.length; j++) {
              //
              const target = memoList[j];
              const startRange = target.memoStartRange;
              const endRange = target.memoEndRange;
              const startIndex = target.memoStartIndex;
              const endIndex = target.memoEndIndex;

              if (
                startRange == 0 &&
                endRange == 0 &&
                startIndex == 0 &&
                endIndex == 0
              )
                continue;
              result.memoDtoList[i].memoList[j].referenceText =
                getReferenceHTML(
                  startRange,
                  endRange,
                  startIndex,
                  endIndex,
                  articleList[i].articleContent
                ).replace("@@@", "");
            }
          }
        }
      }
    }
  });

  return Promise.resolve(result);
}

// [GET /user/graph/article 경제 용어 카테고리
// async function getReadCategory(year) {
//   try {
//     const response = await api.get(`user/graph/article?year=${year}`);
//     if (response.data.statusCode === 200) {
//       return response.data;
//     } else {
//       throw new Error("Failed to fetch read category data");
//     }
//   } catch (error) {
//     console.error("Error in getReadCategory:", error);
//     throw error;
//   }
// }

export { getUserInfo };
