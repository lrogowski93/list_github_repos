package com.demo.list_github_repos.controller;

import com.demo.list_github_repos.controller.response.ErrorResponse;
import com.demo.list_github_repos.controller.response.RepoResponse;
import com.demo.list_github_repos.model.Repo;
import com.demo.list_github_repos.model.RepoOwner;
import com.demo.list_github_repos.service.RepoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepoControllerTest {

    @Mock
    RepoService repoService;
    @InjectMocks
    RepoController repoController;

    @Test
    void shouldGetRepos() {
        //given
        RepoResponse repoResponse = RepoResponse.builder()
                .status(HttpStatus.OK.value())
                .repoList(Arrays.asList(new Repo("repo1", new RepoOwner("testuser"), true, null),new Repo("repo2", new RepoOwner("testuser"), false, null)))
                .build();

        when(repoService.getRepos("testuser")).thenReturn(repoResponse);

        //when
        ResponseEntity<?> responseEntity = repoController.getRepos("testuser");

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(((List<Repo>) responseEntity.getBody()).get(0).isFork());

        verify(repoService, times(1)).getRepos("testuser");
    }

    @Test
    void shouldGetErrorResponse(){
        //given
        RepoResponse repoResponse = RepoResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("error message")
                .repoList(null)
                .build();

        when(repoService.getRepos("testuser")).thenReturn(repoResponse);

        //when
        ResponseEntity<?> responseEntity = repoController.getRepos("testuser");

        //then
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals(((ErrorResponse) responseEntity.getBody()).message(),"error message");
        assertEquals(((ErrorResponse) responseEntity.getBody()).status(),HttpStatus.NOT_FOUND.value());

        verify(repoService, times(1)).getRepos("testuser");
    }

}