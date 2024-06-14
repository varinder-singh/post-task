package com.amplify.posttask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amplify.posttask.dto.PostTaskRequestDto;
import com.amplify.posttask.dto.PostTaskResponseDto;
import com.amplify.posttask.entity.PostTask;
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
    postTask.setContent(postTaskDto.getContent()); // later change this to AWS S3 location
    postTask.setIsSuggestedPost(postTaskDto.getIsSuggestedPost());
    postTask.setIsPriorityPost(postTaskDto.getIsPriorityPost());
    postTask.setPostTaskCategory(postTaskDto.getCategory());
    postTask.setPostTaskTargetPlatform(postTaskDto.getTargetPlatform());
    postTask.setPoints(postTaskDto.getPoints().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue)));
    postTask.setExpirationTS(postTaskDto.getExpirationTS());
    this.postTaskRepositoryImpl.savePost(postTask);

    return "Task created successfully";
  }

  public List<PostTaskResponseDto> getAllTasks() {
    List<PostTask> postTaskList = this.postTaskRepositoryImpl.getPost();
    List<PostTaskResponseDto> retVal = postTaskList.stream()
        .map(e -> postTaskMapper.toPostTaskDto(e))
        .toList();
    return retVal;
  }
}
