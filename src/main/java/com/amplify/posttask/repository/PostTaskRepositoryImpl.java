package com.amplify.posttask.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amplify.posttask.entity.PostTask;

import lombok.extern.slf4j.Slf4j;

/**
 * Repository wrapper for PostTask
 */
@Repository
@Slf4j
public class PostTaskRepositoryImpl {

  // instance variables
  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  public void savePost(PostTask postTask) {
    this.dynamoDBMapper.save(postTask);
  }

  public List<PostTask> getPost() {
    log.info("Get All Posts");
    // Map<String, AttributeValue> keyObj = new HashMap<String, AttributeValue>();
    // // keyObj.put(":email", new AttributeValue().withS(email));
    // DynamoDBQueryExpression<PostTask> queryExpression = new
    // DynamoDBQueryExpression<PostTask>()
    // // .withKeyConditionExpression("email= :email")
    // .withExpressionAttributeValues(keyObj);

    // List<PostTask> postTaskList = dynamoDBMapper.query(PostTask.class,
    // queryExpression);
    List<PostTask> postTaskList = dynamoDBMapper.scanPage(PostTask.class, new DynamoDBScanExpression()).getResults();
    return postTaskList;
  }

  public List<PostTask> getPost(String targetPlatformPostUrl) {
    Map<String, AttributeValue> keyObj = new HashMap<String, AttributeValue>();
    keyObj.put(":targetPlatformPostUrl", new AttributeValue().withS(targetPlatformPostUrl));
    DynamoDBQueryExpression<PostTask> queryExpression = new DynamoDBQueryExpression<PostTask>()
        .withKeyConditionExpression("targetPlatformPostUrl= :targetPlatformPostUrl")
        .withExpressionAttributeValues(keyObj);
    //TODO: ensure that only one record is fetched in below query operation
    return dynamoDBMapper.query(PostTask.class, queryExpression);
  }

}
