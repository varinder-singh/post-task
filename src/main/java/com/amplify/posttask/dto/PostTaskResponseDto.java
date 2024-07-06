package com.amplify.posttask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class PostTaskResponseDto {
  String postTaskId; //TODO: make it a UUID
  String targetPlatformPostUrl;
  Boolean isSuggestedPost;
  Boolean isPriorityPost;
  String category;
  String targetPlatform;
  Map<String, Integer> points;
  Date expirationTime;
}
