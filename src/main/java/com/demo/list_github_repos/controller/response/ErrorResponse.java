package com.demo.list_github_repos.controller.response;

import lombok.Builder;

@Builder
public record ErrorResponse(int status, String message) {
}
