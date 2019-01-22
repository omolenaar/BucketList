package com.example.olgam.bucketlist;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BucketListItemAdapter.ItemClickListener{

    private List<BucketListItem> mItems;
    private EditText mNewItemTitle;
    private EditText mNewItemText;
    private BucketListItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNewItemTitle = findViewById(R.id.newTitle);
        mNewItemText = findViewById(R.id.newItem);
        mItems = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mAdapter = new BucketListItemAdapter(mItems);
        //mRecyclerView.setAdapter(mAdapter);

        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getmItems().observe(this, new Observer<List<BucketListItem>>() {
            @Override
            public void onChanged(@Nullable List<BucketListItem> items) {
                updateUI();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mNewItemTitle.getText().toString();
                String text = mNewItemText.getText().toString();
                boolean crossedOff =false;
                mNewItemTitle.setText("");
                mNewItemText.setText("");
                BucketListItem newItem = new BucketListItem(title, text, crossedOff);
                if (!(TextUtils.isEmpty(text))) {
                    mMainViewModel.insert(newItem);
                    mItems.add(newItem);
                    updateUI();
                }
            }
        });
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = (viewHolder.getAdapterPosition());
                        mMainViewModel.delete(mItems.get(position));
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BucketListItemAdapter(mItems, this);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.swapList(mItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemOnClick(int i) {
        BucketListItem updatedItem = mItems.get(i);
        mMainViewModel.update(updatedItem);
    }
}
