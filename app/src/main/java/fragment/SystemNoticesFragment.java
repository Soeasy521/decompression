package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.decompression.NoticeAdapter;
import com.example.decompression.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SystemNoticesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> notices = new ArrayList<>();
    private NoticeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system_notices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化 Toolbar 和设置返回按钮
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        // 初始化 RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_notices);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NoticeAdapter(notices);
        recyclerView.setAdapter(adapter);

        // 添加一些示例数据
        notices.add("2023-11-26: 更新了隐私政策");
        notices.add("2023-10-25: 新功能发布");
        notices.add("2023-09-09: 新功能发布");
        notices.add("2023-08-25: 新功能发布");
        notices.add("2023-05-25: 新功能发布");
        notices.add("2022-12-05: 新功能发布");
        notices.add("2022-01-25: 新功能发布");
        notices.add("2019-07-25: 新功能发布");
        notices.add("2018-07-25: 新功能发布");
        notices.add("2017-07-25: 新功能发布");
        adapter.notifyDataSetChanged();
    }
}