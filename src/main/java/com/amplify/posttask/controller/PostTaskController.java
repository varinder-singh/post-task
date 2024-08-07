package com.amplify.posttask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amplify.posttask.dto.AuthenticatedUserDetailsDto;
import com.amplify.posttask.dto.PostTaskRequestDto;
import com.amplify.posttask.dto.PostTaskResponseDto;
import com.amplify.posttask.service.PostTaskService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "http://localhost:3000", "https://viralpush.vercel.app" })
@NoArgsConstructor
@RestController
@Slf4j
public class PostTaskController {

  // instance variables
  @Autowired
  private PostTaskService postTaskService;

  @GetMapping("/post-tasks")
  public ResponseEntity<List<PostTaskResponseDto>> getAllTasks() {
    log.info("Recieved Get All Tasks Request");
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(postTaskService.getAllTasks());
  }

  @PostMapping("/post-tasks")
  public ResponseEntity<?> addPost(@RequestBody PostTaskRequestDto postTaskDto, HttpServletRequest request) {
    log.info("Saving post with data {}", postTaskDto);
    AuthenticatedUserDetailsDto authenticatedUserDetailsDto = (AuthenticatedUserDetailsDto) request
        .getAttribute("user");
    if (!authenticatedUserDetailsDto.getRole().equals("ADMIN")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"Not an ADMIN user.\"}");
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(this.postTaskService.createPost(postTaskDto));
  }

  @GetMapping("/post-tasks/{id}")
  public ResponseEntity<PostTaskResponseDto> getPostTaskById(
      @PathVariable("id") String postTaskId, HttpServletRequest request) {
    AuthenticatedUserDetailsDto authenticatedUserDetailsDto = (AuthenticatedUserDetailsDto) request
        .getAttribute("user");
    log.info("fetching post by PostTaskId : {} for user {}", postTaskId, authenticatedUserDetailsDto.getRole());
    return ResponseEntity.ok().body(this.postTaskService.getPostTaskById(postTaskId));
  }

  // TODO: add more HTTP methods if used in application
  @RequestMapping(value = "/post-tasks", method = RequestMethod.OPTIONS)
  public ResponseEntity<String> optionResponse(@RequestHeader("Authorization") String accessToken) {
    return ResponseEntity
        .ok()
        .allow(HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.GET)
        .build();
  }
}
