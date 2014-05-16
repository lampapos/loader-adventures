package org.foo.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.foo.R;
import org.foo.models.Comment;
import org.foo.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

  public static final String TEST = "test1";
  public static final String USER = "user1";
  public static final String CREATED_AT = "12:00";

  @Test
  public void testCommentsAdapter() throws Exception {
    final MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().get();

    final List<Comment> comments = new LinkedList<>();
    comments.add(new Comment(TEST, new User(USER), CREATED_AT));
    comments.add(new Comment(TEST, new User(USER), CREATED_AT));

    mainActivity.setComments(comments);

    final ListView commentsList = (ListView) mainActivity.findViewById(R.id.comments_list);
    assertThat(commentsList.getAdapter()).hasCount(2);

    final View view = commentsList.getAdapter().getView(0, null, new FrameLayout(Robolectric.application));
    assertThat((TextView) view.findViewById(R.id.comment_author)).containsText(USER);
    assertThat((TextView) view.findViewById(R.id.comment_body)).containsText(TEST);
    assertThat((TextView) view.findViewById(R.id.comment_time)).containsText(CREATED_AT);
  }
}