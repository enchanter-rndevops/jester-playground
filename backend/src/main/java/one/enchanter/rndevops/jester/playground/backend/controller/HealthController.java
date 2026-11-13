package one.enchanter.rndevops.jester.playground.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;

@Getter
@RequiredArgsConstructor
class TestRequest1 {
    private final String name;
    private final TestRequest2 testRequest2;
}

@Getter
@RequiredArgsConstructor
class TestRequest2 {
    private final String name;
}

@Getter
@RequiredArgsConstructor
class TestResponseData1 {
    private final String name;
    private final Map<String, String> map;
    private final List<String> list;
    private final TestResponseData2 testResponseData2;
}

@Getter
@RequiredArgsConstructor
class TestResponseData2 {
    private final String name;
}

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<Void> health() {
        log.info("/health");

        return new ApiResponse<Void>(Severity.SUCCESS, "Running.");
    }

    /**
     * 
     * @param request
     * @return
     */
    @GetMapping("/test")
    public ApiResponse<TestResponseData1> test(@RequestBody TestRequest1 request) {
        log.info("/test");

        return new ApiResponse<TestResponseData1>(Severity.SUCCESS, "Running.", null, null);

    }

}
