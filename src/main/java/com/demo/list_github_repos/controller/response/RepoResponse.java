package com.demo.list_github_repos.controller.response;

import com.demo.list_github_repos.model.Repo;
import lombok.Builder;

import java.util.List;

@Builder
public record RepoResponse(int status, String message, List<Repo> repoList) {
}
