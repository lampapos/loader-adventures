package org.foo.api;

import org.foo.models.Comment;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Retrofit interface for Github API.
 */
public interface GithubRepoCommentsRequst {
  @GET("/repos/{owner}/{repo}/comments")
  List<Comment> getCommentsForRepo(@Path("owner") String owner, @Path("repo") String repo);
}
