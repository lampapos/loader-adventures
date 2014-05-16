package org.foo.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.foo.api.GithubRepoCommentsRequst;
import org.foo.models.Comment;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Github repo comments loader.
 */
public class CommentsLoader extends AsyncTaskLoader<List<Comment>> {

  private List<Comment> comments;

  private String owner;
  private String repo;

  public CommentsLoader(Context context, String owner, String repo) {
    super(context);
    this.owner = owner;
    this.repo = repo;
  }

  @Override
  protected void onStartLoading() {
    if (comments != null) {
      deliverResult(comments);
    }

    if (takeContentChanged() || comments == null) {
      forceLoad();
    }
  }

  @Override
  public List<Comment> loadInBackground() {
    final RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://api.github.com")
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .build();

    try {
      return restAdapter.create(GithubRepoCommentsRequst.class).getCommentsForRepo(owner, repo);
    } catch (RetrofitError e) {
      return null;
    }
  }

  @Override
  public void deliverResult(List<Comment> data) {
    if (isReset()) {
      comments = null;
      return;
    }

    comments = data;

    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override
  protected void onReset() {
    comments = null;
  }
}
