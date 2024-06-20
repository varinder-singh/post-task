package com.amplify.posttask.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amplify.posttask.PostTaskApplication;
import com.amplify.posttask.exception.PostTaskException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null) {
      String[] authElements = header.split(" ");
      log.info("Static value from main class "+PostTaskApplication.proxyEndpoint);
      if (authElements.length == 2
          && "Bearer".equals(authElements[0])) {
        try {
          if ("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())) {
            // TODO: make rest call here to authenticate
            RestClient customClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl("http://localhost:8080")
                .defaultHeader("Authorization", header)
                .build();
            ResponseEntity<String> responseEntity = customClient.post()
                .uri("http://localhost:8080/authenticate") // TODO: need to read the proxy url from the property
                .retrieve()
                .toEntity(String.class);
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
              // TODO: respond http 403 from here
              log.info("User Authentication failed!"); // TODO: check why other than OK none error codes are captured
              throw new PostTaskException(HttpStatus.FORBIDDEN, "Invalid JWT Token");
            }
            log.info("successfully validated the user");
          }
        } catch (RuntimeException e) {
          throw e;
        }
      }
    }

    filterChain.doFilter(request, response);
  }
}
