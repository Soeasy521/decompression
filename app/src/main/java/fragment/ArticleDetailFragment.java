package fragment;

import android.content.Context;
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

import com.example.decompression.R;
import com.example.mysql.DBUtils;

import java.util.List;

public class ArticleDetailFragment extends Fragment {

    private TextView tv_article_title;
    private TextView tv_article_content;
    private TextView tv_article_date;
    private TextView tv_article_likes;
    private TextView tv_article_comments;
    private ImageView iv_like;
    private ListView lv_comments;
    private EditText et_comment_content;
    private Button btn_add_comment;

    private int articleId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

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

        // 获取传递过来的文章ID
        articleId = getArguments().getInt("articleId");

        // 设置文章详情
        //setArticleDetails();

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

        // 加载评论列表
        loadComments();

        return view;
    }

//    private void setArticleDetails() {
//        DBUtils.Article article = DBUtils.getArticleById(getContext(), articleId);
//        if (article != null) {
//            tv_article_title.setText(article.getArticleTitle());
//            tv_article_content.setText(article.getArticleContent());
//            tv_article_date.setText(article.getArticleDate().toString());
//            tv_article_likes.setText(String.valueOf(article.getArticleLike()));
//            tv_article_comments.setText(String.valueOf(article.getArticleComment()));
//        }
//    }

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
        int userId = 1; // 示例中使用固定的用户ID，实际情况请根据需要获取
        if (DBUtils.addComment(getContext(), userId, articleId, commentContent)) {
            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
            et_comment_content.setText(""); // 清空输入框
            loadComments(); // 重新加载评论列表
        } else {
            Toast.makeText(getContext(), "评论失败，请重试", Toast.LENGTH_SHORT).show();
        }
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
            holder.tvCommentDate.setText(comment.getCommentDate().toString());

            return convertView;
        }

        private static class ViewHolder {
            TextView tvCommenter;
            TextView tvCommentContent;
            TextView tvCommentDate;
        }
    }
}