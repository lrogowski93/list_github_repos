package com.demo.list_github_repos.service;

import com.demo.list_github_repos.config.RestTemplateConfig;
import com.demo.list_github_repos.controller.response.RepoResponse;
import com.demo.list_github_repos.model.Branch;
import com.demo.list_github_repos.model.Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.function.Predicate.*;

@Service
@RequiredArgsConstructor
public class RepoService {

    private final RestTemplateConfig restTemplateConfig;

    public RepoResponse getRepos(String username) {

        ResponseEntity<Repo[]> responseEntity = restTemplateConfig.restTemplate()
                .exchange("https://api.github.com/users/{username}/repos", HttpMethod.GET, null, Repo[].class, username);

        List<Repo> repoList = new ArrayList<>(Arrays.asList(responseEntity.getBody()));

        setBranches(username,repoList);

        return RepoResponse.builder()
                .status(responseEntity.getStatusCode().value())
                .repoList(repoList.stream().filter(not(Repo::isFork))
                        .collect(Collectors.toList()))
                .build();
    }

    private List<Branch> getBranches(String username, String repo){
        return Arrays.asList(Objects.requireNonNull(restTemplateConfig.restTemplate()
                .getForObject("https://api.github.com/repos/{username}/{repo}/branches", Branch[].class, username, repo)));
    }

    private void setBranches(String username, List<Repo> repoList){
        repoList.stream().forEach(
                repo->repo.setBranches(getBranches(username,repo.getName()))
        );
    }

}
