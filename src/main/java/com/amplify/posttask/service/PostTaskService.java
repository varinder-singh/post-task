package com.amplify.posttask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amplify.posttask.dto.PostTaskRequestDto;
import com.amplify.posttask.dto.PostTaskResponseDto;
import com.amplify.posttask.entity.PostTask;
import com.amplify.posttask.exception.PostTaskException;
import com.amplify.posttask.mapper.PostTaskMapper;
import com.amplify.posttask.repository.PostTaskRepositoryImpl;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@Getter
@Service
@Slf4j
public class PostTaskService {

  /**
   * The service shall create post for an admin and get posts for users
   */
  @Autowired
  private PostTaskRepositoryImpl postTaskRepositoryImpl;

  @Autowired
  private PostTaskMapper postTaskMapper;

  public String createPost(PostTaskRequestDto postTaskDto) {
    PostTask postTask = new PostTask();
    postTask.setTargetPlatformPostUrl(postTaskDto.getTargetPlatformPostUrl());
    postTask.setContent(postTaskDto.getContent());
    postTask.setIsSuggestedPost(postTaskDto.getIsSuggestedPost());
    postTask.setIsPriorityPost(postTaskDto.getIsPriorityPost());
    postTask.setPostTaskCategory(postTaskDto.getCategory());
    postTask.setPostTaskTargetPlatform(postTaskDto.getTargetPlatform());
    postTask.setPoints(postTaskDto.getPoints().entrySet().stream()
        .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue)));
    postTask.setExpirationTS(postTaskDto.getExpirationTS());
    try {
      this.postTaskRepositoryImpl.savePost(postTask);
    } catch (Exception ex) { // TODO: add specific catch exceptions and throw accordingly
      throw new PostTaskException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while saving Post in DB");
    }
    return "Task created successfully";
  }

  public List<PostTaskResponseDto> getAllTasks() {
    List<PostTask> postTaskList = null;
    try {
      postTaskList = this.postTaskRepositoryImpl.getPost();
    } catch (Exception ex) {
      throw new PostTaskException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching Post(s) from DB");
    }
    List<PostTaskResponseDto> retVal = postTaskList.stream()
        .map(e -> postTaskMapper.toPostTaskDto(e))
        .toList();
    return retVal;
  }

  public PostTaskResponseDto getPostTaskById(String postTaskId) {
    // TODO Auto-generated method stub
    return postTaskMapper.toPostTaskDto(this.postTaskRepositoryImpl.getPost(postTaskId).get(0));
  }
}
