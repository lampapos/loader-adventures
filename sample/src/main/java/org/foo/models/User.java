package org.foo.models;

/**
 * Github user POJO.
 */
public class User {
  private String login;

  public User(String login) {
    this.login = login;
  }

  public User() { }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
