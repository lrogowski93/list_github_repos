package com.demo.list_github_repos.controller;

import com.demo.list_github_repos.controller.response.ErrorResponse;
import com.demo.list_github_repos.controller.response.RepoResponse;
import com.demo.list_github_repos.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping(value = "/repos/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        RepoResponse repoResponse = repoService.getRepos(username);

        if (repoResponse.status() == HttpStatus.OK.value())
            return ResponseEntity.ok().body(repoResponse.repoList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(repoResponse.message())
                .status(repoResponse.status())
                .build();

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
}
