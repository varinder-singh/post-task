package com.amplify.posttask.dto;

import com.amplify.posttask.enums.PostTaskActivityEnum;
import com.amplify.posttask.enums.PostTaskCategoryEnum;
import com.amplify.posttask.enums.PostTaskTargetPlatformEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class PostTaskResponseDto {
  String postTaskId;
  String content;
  Boolean isSuggestedPost;
  Boolean isPriorityPost;
  String category;
  String targetPlatform;
  Map<String, Integer> points;
  Date expirationTime;
}
