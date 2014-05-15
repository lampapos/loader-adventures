package org.foo.models;

public class Comment {
  private String body;
  private User user;
  private String createdAt;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getTime() {
    return createdAt;
  }

  public void setTime(String time) {
    this.createdAt = time;
  }
}
