package com.amplify.posttask.dto;

import java.util.Map;
import java.util.Date;

import com.amplify.posttask.enums.PostTaskActivityEnum;
import com.amplify.posttask.enums.PostTaskCategoryEnum;
import com.amplify.posttask.enums.PostTaskTargetPlatformEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class PostTaskRequestDto {
  // private String postTaskId;
  private String content;
  private Boolean isSuggestedPost;
  private Boolean isPriorityPost;
  private PostTaskCategoryEnum category;
  private PostTaskTargetPlatformEnum targetPlatform;
  private Map<PostTaskActivityEnum, Integer> points;
  private Date expirationTS;
}
