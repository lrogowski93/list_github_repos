package com.demo.list_github_repos.controller;

import com.demo.list_github_repos.controller.request.RepoRequest;
import com.demo.list_github_repos.controller.response.ErrorResponse;
import com.demo.list_github_repos.controller.response.RepoResponse;
import com.demo.list_github_repos.service.RepoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/repos")
    public ResponseEntity<?> getRepos(@Valid @RequestBody RepoRequest request){
        RepoResponse repoResponse = repoService.getRepos(request.username());

        if(repoResponse.status()==HttpStatus.OK.value())
            return ResponseEntity.ok().body(repoResponse.repoList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(repoResponse.message())
                .status(repoResponse.status())
                .build();

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
}
