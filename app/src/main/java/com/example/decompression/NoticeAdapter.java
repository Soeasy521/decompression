package com.example.decompression;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private final List<String> notices;

    public NoticeAdapter(List<String> notices) {
        this.notices = notices;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice, parent, false); // 更正布局文件名
        return new NoticeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        String noticeText = notices.get(position);
        holder.notice.setText(noticeText); // 设置文本
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    static class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView notice;

        NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            notice = itemView.findViewById(R.id.notice); // 确保 ID 正确
        }
    }
}