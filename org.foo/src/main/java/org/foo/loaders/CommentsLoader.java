package org.foo.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.foo.api.GithubRepoCommentsRequst;
import org.foo.models.Comment;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class CommentsLoader extends AsyncTaskLoader<List<Comment>> {

  private List<Comment> comments;

  public CommentsLoader(Context context) {
    super(context);
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
  protected void onStopLoading() {
    cancelLoad();
  }

  @Override
  public List<Comment> loadInBackground() {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    final RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://api.github.com")
        .build();

    try {
      return restAdapter.create(GithubRepoCommentsRequst.class).getCommentsForRepo("lampapos", "PZKSLabs");
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
    onStopLoading();
    comments = null;
  }
}
