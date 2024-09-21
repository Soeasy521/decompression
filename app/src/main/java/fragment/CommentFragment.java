package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.decompression.R;
import com.example.mysql.DBUtils;

import java.util.List;

public class CommentFragment extends AppCompatActivity {

    private int articleId;
    private ListView lv_comments;
    private TextView tvNoComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 获取传递过来的文章ID
        articleId = getIntent().getIntExtra("articleId", -1);

        if (articleId == -1) {
            Toast.makeText(this, "无效的文章ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 初始化控件
        lv_comments = findViewById(R.id.lv_comments);
        tvNoComments = findViewById(R.id.tv_no_comments);

        // 加载评论列表
        loadComments();
    }

    private void loadComments() {
        List<DBUtils.Comment> comments = DBUtils.getComments(this, articleId);
        if (comments.isEmpty()) {
            tvNoComments.setVisibility(View.VISIBLE);
            lv_comments.setVisibility(View.GONE);
        } else {
            tvNoComments.setVisibility(View.GONE);
            lv_comments.setVisibility(View.VISIBLE);
            CommentAdapter adapter = new CommentAdapter(this, comments);
            lv_comments.setAdapter(adapter);
        }
    }

    // 内部类：评论适配器
    private class CommentAdapter extends BaseAdapter {

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
                holder.tvCommentUser = convertView.findViewById(R.id.tv_comment_user);
                holder.tvCommentContent = convertView.findViewById(R.id.tv_comment_content);
                holder.tvCommentDate = convertView.findViewById(R.id.tv_comment_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DBUtils.Comment comment = comments.get(position);
            holder.tvCommentUser.setText("用户 " + comment.getUserId());
            holder.tvCommentContent.setText(comment.getCommentContent());
            holder.tvCommentDate.setText(comment.getCommentDate().toString());

            return convertView;
        }

        private class ViewHolder {
            TextView tvCommentUser;
            TextView tvCommentContent;
            TextView tvCommentDate;
        }
    }
}