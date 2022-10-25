package com.my.github.controller;


import com.my.github.ConfDto;
import com.my.github.TransferRequest;
import com.my.github.service.GitHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/_github/v0", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@EnableScheduling
public class GithubController {
    private final GitHubService service;

    @Autowired
    public GithubController(GitHubService service) {
        this.service = service;
    }

    private static String owner;
    private static String new_owner;
    private static String token;


    @PostMapping("/start")
    public String start(@RequestBody ConfDto dto) {
        if (StringUtils.hasLength(dto.getOwner())) owner = dto.getOwner().trim();
        if (StringUtils.hasLength(dto.getNew_owner())) new_owner = dto.getNew_owner().trim();
        if (StringUtils.hasLength(dto.getToken())) token = dto.getToken().trim();

        return "設置成功";
    }


    @Scheduled(cron = "*/20 * * * * *")
    public void start() {
        log.info("開始移轉repo-start");
        if (StringUtils.hasLength(owner) && StringUtils.hasLength(token))
            service.transfer(new TransferRequest(owner, new_owner), token);

    }
}

