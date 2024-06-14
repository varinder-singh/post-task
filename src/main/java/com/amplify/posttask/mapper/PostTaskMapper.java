package com.amplify.posttask.mapper;

import org.springframework.stereotype.Component;

import com.amplify.posttask.dto.PostTaskResponseDto;
import com.amplify.posttask.entity.PostTask;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;
import java.util.Map;

@Component
@Slf4j
public class PostTaskMapper {

  public PostTaskResponseDto toPostTaskDto(PostTask postTask) {
    if (postTask == null) {
      return null;
    }
    return PostTaskResponseDto.builder()
        .postTaskId(postTask.getPostTaskId())
        .content(postTask.getContent())
        .isSuggestedPost(postTask.getIsSuggestedPost())
        .isPriorityPost(postTask.getIsPriorityPost())
        .category(postTask.getPostTaskCategory().toString())
        .targetPlatform(postTask.getPostTaskTargetPlatform().toString())
        .points(postTask.getPoints())
        .expirationTime(postTask.getExpirationTS())
        .build();
  }
}
