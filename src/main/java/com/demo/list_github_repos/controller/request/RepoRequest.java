package com.demo.list_github_repos.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RepoRequest(@NotBlank(message = "Field username is mandatory") String username) {
}
