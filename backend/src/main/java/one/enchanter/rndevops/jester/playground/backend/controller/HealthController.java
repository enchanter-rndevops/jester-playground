package one.enchanter.rndevops.jester.playground.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<Void> health() {

        log.info("/health");

        return new ApiResponse<Void>(Severity.SUCCESS, "Running.");
    }
}
