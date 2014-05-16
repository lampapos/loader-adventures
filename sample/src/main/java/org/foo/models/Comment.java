package org.foo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Github repo comment POJO.
 */
public class Comment {
  private String body;
  private User user;

  @SerializedName("created_at")
  private String createdAt;

  public Comment(String body, User user, String createdAt) {
    this.body = body;
    this.user = user;
    this.createdAt = createdAt;
  }

  public Comment() { }

  public String getBody() {
    return body;
  }

  public User getUser() {
    return user;
  }

  public String getTime() {
    return createdAt;
  }
}
