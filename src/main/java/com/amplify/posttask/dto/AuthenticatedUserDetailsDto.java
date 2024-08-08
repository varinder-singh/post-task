package com.amplify.posttask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedUserDetailsDto {
  String role;
  String organisation;
  String department;
}
