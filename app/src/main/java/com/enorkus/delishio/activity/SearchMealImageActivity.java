package com.enorkus.delishio.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.enorkus.delishio.R;
import com.enorkus.delishio.adapter.PictureListAdapter;
import com.enorkus.delishio.asynctask.ImageSearchTask;
import com.enorkus.delishio.entity.Picture;
import com.enorkus.delishio.util.AsyncTaskResponse;
import com.enorkus.delishio.util.ConnectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMealImageActivity extends AppCompatActivity implements AsyncTaskResponse, SearchView.OnQueryTextListener {

    public static final String EXTRA_MEAL_PICTURE_URL = SearchMealImageActivity.class.getSimpleName() + "_EXTRA_MEAL_PICTURE_URL";

    @BindView(R.id.picturesRecyclerView)
    protected RecyclerView picturesRecyclerView;

    private ImageSearchTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal_image);
        setSupportActionBar((Toolbar) findViewById(R.id.pictureSearchToolbar));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_picture_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_example_cupcakes));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        RecyclerView.Adapter adapter = new PictureListAdapter(this, (List<Picture>)response);
        picturesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        picturesRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        task = new ImageSearchTask(this);
        task.execute(ConnectionUtils.buildImageSearchURL(query));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
