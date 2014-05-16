package org.foo.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.foo.R;
import org.foo.loaders.CommentsLoader;
import org.foo.models.Comment;
import org.foo.models.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Activity with list of Github repo comments.
 */
public class MainActivity extends Activity {

  public static final int COMMENTS_LOADER_ID = 1;
  public static final String BODY = "body";
  public static final String TIME = "time";
  public static final String AUTHOR = "author";

  @InjectView(R.id.comments_list)
  ListView commentsList;

  private LoaderManager.LoaderCallbacks<List<Comment>> commentsLoaderCallbacks =
  new LoaderManager.LoaderCallbacks<List<Comment>>() {
    @Override
    public Loader<List<Comment>> onCreateLoader(int id, Bundle args) {
      return new CommentsLoader(MainActivity.this, "lampapos", "loader-adventures");
    }

    @Override
    public void onLoadFinished(Loader<List<Comment>> loader, List<Comment> comments) {
      setComments(comments);
    }

    @Override
    public void onLoaderReset(Loader<List<Comment>> loader) {
    }
  };

  public void setComments(final List<Comment> comments) {
    if (comments != null) {
      final SimpleAdapter commentsAdapter = new SimpleAdapter(MainActivity.this, convert(comments), R.layout.comment,
          new String[]{BODY, TIME, AUTHOR},
          new int[]{R.id.comment_body, R.id.comment_time, R.id.comment_author});

      commentsList.setAdapter(commentsAdapter);
    }
  }

  private List<? extends Map<String, ?>> convert(List<Comment> data) {
    final List<Map<String, String>> convertedData = new LinkedList<>();

    for (Comment comment : data) {
      final Map<String, String> item = new HashMap<>();
      item.put(BODY, comment.getBody());
      item.put(TIME, comment.getTime());
      item.put(AUTHOR, comment.getUser().getLogin());

      convertedData.add(item);
    }

    return convertedData;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    LoaderManager.enableDebugLogging(true);
    loadComments();
  }

  void setLoadingStub() {
    setComments(Collections.singletonList(new Comment("Loading...", new User(""), "")));
  }

  private void loadComments() {
    setLoadingStub();
    getLoaderManager().initLoader(COMMENTS_LOADER_ID, null, commentsLoaderCallbacks);
  }

  @SuppressWarnings("UnusedDeclaration")
  @OnClick(R.id.refresh_button)
  void reloadComments() {
    setLoadingStub();
    getLoaderManager().restartLoader(COMMENTS_LOADER_ID, null, commentsLoaderCallbacks);
  }


}
