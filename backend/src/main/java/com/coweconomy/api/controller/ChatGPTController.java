package com.coweconomy.api.controller;

import com.coweconomy.api.request.ChatGPTRequest;
import com.coweconomy.api.request.QuizResultRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import com.coweconomy.service.MyChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {

    private static final Logger logger = LoggerFactory.getLogger(ChatGPTController.class);

    private final MyChatGPTService myChatGPTService;

    @Autowired
    public ChatGPTController(MyChatGPTService myChatGPTService) {
        this.myChatGPTService = myChatGPTService;
    }

    /**
     * 해당 경제용어와 유사한 경제용어 3개 조회
     * - Quiz 문제 출제 시 사용
     */
    @PostMapping("/ask-word")
    public BaseResponse<?> generateCompletion(@RequestBody ChatGPTRequest quizWord) {
//        logger.info("#[Gpt3Controller]# 해당 경제용어와 유사한 경제용어 3개 조회 동작 - ChatGPTRequest: {}", quizWord);

        try {
            List<String> similarityWordList = new ArrayList<>();

            for (String qw: quizWord.getWordList()) {
                for (String simiWord: myChatGPTService.getChatResponse("퀴즈 선택지를 만들건데 정답은 경제용어 " + qw + "야 그래서 나머지 선택지를 경제용어 " + qw + "와 유사한 경제용어 3개를 설명없이 단어만 1, 2, 3으로 출력해줘 단! " + qw + "는 출력해주면 안돼 다른 걸로 출력해줘")) {
                    similarityWordList.add(simiWord);
                }
            }
//        logger.info("#21# 유사단어 확인: {} - 총 개수: {}", similarityWordList, similarityWordList.size());
            return BaseResponse.success(similarityWordList);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return BaseResponse.fail();
        }
    }

}
