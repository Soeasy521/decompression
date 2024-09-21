package fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.decompression.R;
import com.example.mysql.DBUtils;

import org.chromium.base.Log;

import java.util.List;

public class CommunityFragment extends Fragment {

    private ListView lv_articles;
    private Button btn_publish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // 初始化控件
        lv_articles = view.findViewById(R.id.lv_articles);
        btn_publish = view.findViewById(R.id.btn_publish);

        // 设置发表文章按钮点击事件
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPublishFragment();
            }
        });

        // 加载文章列表
        loadArticles();

        return view;
    }

    private void navigateToPublishFragment() {
        Log.d("CommunityFragment", "navigateToPublishFragment called");
        // 启动新的Fragment（PublishFragment）
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 隐藏当前Fragment
        transaction.hide(this);

        // 添加新的Fragment
        PublishFragment publishFragment = new PublishFragment();
        transaction.add(R.id.fragment_container, publishFragment, "PUBLISH_FRAGMENT")
                .addToBackStack(null) // 添加到回退栈
                .commit(); // 提交事务
    }

    private void loadArticles() {
        List<DBUtils.Article> articles = DBUtils.getArticles(getContext());
        ArticleAdapter adapter = new ArticleAdapter(getContext(), articles);
        lv_articles.setAdapter(adapter);
    }

    // 内部类：文章适配器
    private class ArticleAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<DBUtils.Article> articles;

        public ArticleAdapter(Context context, List<DBUtils.Article> articles) {
            this.inflater = LayoutInflater.from(context);
            this.articles = articles;
        }

        @Override
        public int getCount() {
            return articles.size();
        }

        @Override
        public Object getItem(int position) {
            return articles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_article, parent, false);
                holder = new ViewHolder();
                holder.tvArticleTitle = convertView.findViewById(R.id.tv_article_title);
                holder.tvArticleContent = convertView.findViewById(R.id.tv_article_content);
                holder.tvArticleDate = convertView.findViewById(R.id.tv_article_date);
                holder.tvArticleLikes = convertView.findViewById(R.id.tv_article_likes);
                holder.tvArticleComments = convertView.findViewById(R.id.tv_article_comments);
                holder.ivLike = convertView.findViewById(R.id.iv_like);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final DBUtils.Article article = articles.get(position);
            holder.tvArticleTitle.setText(article.getArticleTitle());
            holder.tvArticleContent.setText(article.getArticleContent());
            holder.tvArticleDate.setText(article.getArticleDate());
            holder.tvArticleLikes.setText(String.valueOf(article.getArticleLike()));
            holder.tvArticleComments.setText(String.valueOf(article.getArticleComment()));

            // 设置点赞功能
            holder.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DBUtils.likeArticle(v.getContext(), article.getArticleId())) {
                        int currentLikes = Integer.parseInt(holder.tvArticleLikes.getText().toString());
                        holder.tvArticleLikes.setText(String.valueOf(currentLikes + 1));
                        Toast.makeText(v.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "点赞失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // 设置点击文章进入详情页
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToArticleDetail(article.getArticleId());
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView tvArticleTitle;
            TextView tvArticleContent;
            TextView tvArticleDate;
            TextView tvArticleLikes;
            TextView tvArticleComments;
            ImageView ivLike;
        }
    }

    private void navigateToArticleDetail(int articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putInt("articleId", articleId);
        fragment.setArguments(args);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 隐藏当前Fragment
        transaction.hide(this);

        // 添加新的Fragment
        transaction.add(R.id.fragment_container, fragment, "ARTICLE_DETAIL_FRAGMENT")
                .addToBackStack(null) // 添加到回退栈
                .commit(); // 提交事务
    }
}