package com.enorkus.delishio.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enorkus.delishio.adapter.PictureListAdapter;
import com.enorkus.delishio.entity.Picture;
import com.enorkus.delishio.util.AsyncTaskResponse;
import com.enorkus.delishio.util.ConnectionUtils;
import com.enorkus.delishio.R;
import com.enorkus.delishio.asynctask.ImageSearchTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMealImageActivity extends AppCompatActivity implements AsyncTaskResponse {

    @BindView(R.id.picturesRecyclerView)
    protected RecyclerView picturesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal_image);
        ButterKnife.bind(this);
        ImageSearchTask task = new ImageSearchTask(this);
        task.execute(ConnectionUtils.buildImageSearchURL("cupcake"));
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        RecyclerView.Adapter adapter = new PictureListAdapter(this, (List<Picture>)response);
        picturesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        picturesRecyclerView.setAdapter(adapter);
    }
}
