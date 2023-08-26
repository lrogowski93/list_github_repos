package com.demo.list_github_repos.service;

import com.demo.list_github_repos.config.RestTemplateConfig;
import com.demo.list_github_repos.controller.response.RepoResponse;
import com.demo.list_github_repos.model.Branch;
import com.demo.list_github_repos.model.Commit;
import com.demo.list_github_repos.model.Repo;
import com.demo.list_github_repos.model.RepoOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepoServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RestTemplateConfig restTemplateConfig;
    @InjectMocks
    private RepoService repoService;


    @BeforeEach
    void setUp(){
        when(restTemplateConfig.restTemplate()).thenReturn(restTemplate);
    }

    @Test
    void shouldGetHttpStatus() {
        //given
        ResponseEntity<Repo[]> responseEntity = new ResponseEntity<>(new Repo[]{}, HttpStatus.OK);
        when(restTemplateConfig.restTemplate().exchange(anyString(), eq(HttpMethod.GET), ArgumentMatchers.any(), eq(Repo[].class), anyString())).thenReturn(responseEntity);
        //when
        RepoResponse repoResponse = repoService.getRepos("testuser");
        //then
        assertEquals(HttpStatus.OK.value(),repoResponse.status());
    }

    @Test
    void shouldNotPutForkInResponse(){
        //given
        ResponseEntity<Repo[]> responseEntity =new ResponseEntity<>(
                new Repo[]{
                        new Repo("repo3", new RepoOwner("testuser"), true, null),
                }, HttpStatus.OK
        );
        Branch[] branchList = {new Branch("main", new Commit("commitHash1"))};
        when(restTemplateConfig.restTemplate().exchange(anyString(), eq(HttpMethod.GET), ArgumentMatchers.any(), eq(Repo[].class), anyString())).thenReturn(responseEntity);
        when(restTemplateConfig.restTemplate().getForObject(anyString(),eq(Branch[].class),anyString(),anyString() )).thenReturn(branchList);

        //when
        RepoResponse repoResponse = repoService.getRepos("testuser");
        List<Repo> repoList = repoResponse.repoList();

        //then
        assertEquals(0,repoList.size());
    }

    @Test
    void shouldGetBranchesForRepo(){
        //given
        ResponseEntity<Repo[]> responseEntity =new ResponseEntity<>(
                new Repo[]{
                        new Repo("repo3", new RepoOwner("testuser"), false, null),
                }, HttpStatus.OK
        );
        Branch[] branchList = {new Branch("main", new Commit("commitHash1"))};
        when(restTemplateConfig.restTemplate().exchange(anyString(), eq(HttpMethod.GET), ArgumentMatchers.any(), eq(Repo[].class), anyString())).thenReturn(responseEntity);
        when(restTemplateConfig.restTemplate().getForObject(anyString(),eq(Branch[].class),anyString(),anyString() )).thenReturn(branchList);

        //when
        RepoResponse repoResponse = repoService.getRepos("testuser");
        List<Repo> repoList = repoResponse.repoList();

        //then
        assertEquals(1,repoList.get(0).getBranches().size());
        assertEquals("commitHash1", repoList.get(0).getBranches().get(0).commit().sha());
    }

}