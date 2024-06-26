package com.amplify.posttask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amplify.posttask.dto.PostTaskRequestDto;
import com.amplify.posttask.dto.PostTaskResponseDto;
import com.amplify.posttask.entity.PostTask;
import com.amplify.posttask.service.PostTaskService;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
  public ResponseEntity<?> addPost(@RequestPart(value = "file", required = false) MultipartFile file,
      @RequestPart("data") PostTaskRequestDto postTaskDto) {
    if (!file.isEmpty()) {
      log.info("Controller : Creating Post");
    }
    return ResponseEntity.ok(this.postTaskService.createPost(postTaskDto));

  }
}
