package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.decompression.PublishFragment;
import com.example.decompression.R;
import com.example.mysql.DBUtils;

import java.util.List;

public class CommunityFragment extends Fragment {

    private TextView tv_article_title;
    private TextView tv_article_content;
    private TextView tv_article_date;
    private TextView tv_article_likes;
    private TextView tv_article_comments;
    private ImageView iv_like;
    private ListView lv_comments;
    private EditText et_comment_content;
    private Button btn_add_comment;
    private Button btn_publish;

    private int articleId = 1; // 示例中使用固定的articleId，实际情况请根据需要获取
    private int userId = 1; // 示例中使用固定的用户ID，实际情况请根据需要获取

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // 初始化控件
        tv_article_title = view.findViewById(R.id.tv_article_title);
        tv_article_content = view.findViewById(R.id.tv_article_content);
        tv_article_date = view.findViewById(R.id.tv_article_date);
        tv_article_likes = view.findViewById(R.id.tv_article_likes);
        tv_article_comments = view.findViewById(R.id.tv_article_comments);
        iv_like = view.findViewById(R.id.iv_like);
        lv_comments = view.findViewById(R.id.lv_comments);
        et_comment_content = view.findViewById(R.id.et_comment_content);
        btn_add_comment = view.findViewById(R.id.btn_add_comment);
        btn_publish = view.findViewById(R.id.btn_publish); // 新增发布按钮初始化

        // 设置文章详情
        setArticleDetails();

        // 设置点赞功能
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeArticle();
            }
        });

        // 设置评论功能
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = et_comment_content.getText().toString();

                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    addComment(commentContent);
                }
            }
        });

        // 新增发布按钮点击事件
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPublishActivity();
            }
        });

        // 加载评论列表
        loadComments();

        return view;
    }

    private void setArticleDetails() {
        // 示例数据，实际情况请从数据库加载
        String articleTitle = "示例文章标题";
        String articleContent = "这是文章的内容，可以在这里详细介绍文章的主题。";
        String articleDate = "2024-09-18 14:10";
        int articleLikes = 0;
        int articleComments = 0;

        tv_article_title.setText(articleTitle);
        tv_article_content.setText(articleContent);
        tv_article_date.setText(articleDate);
        tv_article_likes.setText(String.valueOf(articleLikes));
        tv_article_comments.setText(String.valueOf(articleComments));
    }

    private void likeArticle() {
        if (DBUtils.likeArticle(getContext(), articleId)) {
            int currentLikes = Integer.parseInt(tv_article_likes.getText().toString());
            tv_article_likes.setText(String.valueOf(currentLikes + 1));
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "点赞失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void addComment(String commentContent) {
        if (DBUtils.addComment(getContext(), userId, articleId, commentContent)) {
            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
            et_comment_content.setText(""); // 清空输入框
            loadComments(); // 重新加载评论列表
        } else {
            Toast.makeText(getContext(), "评论失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToPublishActivity() {
        // 启动新的活动（PublishActivity）
        startActivity(new Intent(getActivity(), PublishFragment.class));
    }

    private void loadComments() {
        List<DBUtils.Comment> comments = DBUtils.getComments(getContext(), articleId);
        CommentAdapter adapter = new CommentAdapter(getContext(), comments);
        lv_comments.setAdapter(adapter);
    }

    // 内部类：评论适配器
    private static class CommentAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<DBUtils.Comment> comments;

        public CommentAdapter(Context context, List<DBUtils.Comment> comments) {
            this.inflater = LayoutInflater.from(context);
            this.comments = comments;
        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_comment, parent, false);
                holder = new ViewHolder();
                holder.tvCommenter = convertView.findViewById(R.id.tv_commenter);
                holder.tvCommentContent = convertView.findViewById(R.id.tv_comment_content);
                holder.tvCommentDate = convertView.findViewById(R.id.tv_comment_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DBUtils.Comment comment = comments.get(position);
            holder.tvCommenter.setText("用户 " + comment.getUserId());
            holder.tvCommentContent.setText(comment.getCommentContent());
            holder.tvCommentDate.setText(comment.getCommentDate());

            return convertView;
        }

        private static class ViewHolder {
            TextView tvCommenter;
            TextView tvCommentContent;
            TextView tvCommentDate;
        }
    }
}