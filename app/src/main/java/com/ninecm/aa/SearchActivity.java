package com.ninecm.aa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.ninecm.aa.adapter.SearchItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private List<Cosmetic> cosmetics = new ArrayList<>();
    private SearchItemAdapter searchItemAdapter;
    private TextView searchCancel;

    private SearchView searchView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        setUp();

        viewModel.getAll().observe(this, dbCosmetics -> {
            cosmetics = dbCosmetics;
        });

        // RecyclerView Adapter 생성 및 Cosmetic List 전달
        searchItemAdapter = new SearchItemAdapter(cosmetics, this);
        // RecyclerView Manager를 LinearLayout으로 설정
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // RecyclerView Adapter 설정
        searchRecyclerView.setAdapter(searchItemAdapter);
    }

    private void init() {
        searchRecyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        searchCancel = (TextView) findViewById(R.id.search_cancel);
        searchView = findViewById(R.id.search_view);
    }

    private void setUp() {
        searchCancel.setOnClickListener(goBackPage);        // 검색 취소 액션 리스너 등록
        searchView.setOnClickListener(ClickAllArea);
        searchView.setOnQueryTextListener(searchQuery);

        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.getApplication()))
                .get(MainViewModel.class);
    }

    View.OnClickListener goBackPage = v -> {
        SearchActivity.this.finish();
        overridePendingTransition(R.anim.anim_slide_out_left, R.anim.anim_slide_in_right);
    };

    View.OnClickListener ClickAllArea = v -> {
        searchView.setIconified(false);
    };

    SearchView.OnQueryTextListener searchQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {    //키보드에서 검색 버튼 누르면 호출
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")) {
                searchItemAdapter.setCosmetics(new ArrayList<>());
            } else {
                searchItemAdapter.setCosmetics(searchCosmetics(newText));
            }
            return true;
        }
    };

    private List<Cosmetic> searchCosmetics(String text) {
        List<Cosmetic> cosmeticList = new ArrayList<>();
        for (Cosmetic cosmetic : cosmetics) {
            String title = cosmetic.getTitle().replaceAll(" ", "").toLowerCase();
            String search = text.replaceAll(" ", "").toLowerCase();
            if (title.contains(search)) {
                cosmeticList.add(cosmetic);
            }
        }
        return cosmeticList;
    }
}