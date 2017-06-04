package com.gaurav.cdsrecyclerviewsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gaurav.cdsrecyclerview.CdsItemTouchCallback;
import com.gaurav.cdsrecyclerview.CdsRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CdsRecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    //Listeners
    private CdsRecyclerView.ItemClickListener mItemClickListener;
    private CdsRecyclerView.ItemLongPressListener mItemLongPressListener;
    private CdsItemTouchCallback.ItemDragCompleteListener mItemDragCompleteListener;
    private CdsItemTouchCallback.ItemSwipeCompleteListener mItemSwipeCompleteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListeners();
        //Initialize the Cdsrecycler view as you would for a normal recycler view
        mRecyclerView = new CdsRecyclerView(this);
        mRecyclerView = (CdsRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Use the Adapter which extends CdsRecyclerViewAdapter to get add,remove,drag and swipe functionality
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, initData());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //Setting on item click,long press,swipe and drag functionality
        mRecyclerView.setItemClickListener(mItemClickListener);
        mRecyclerView.setItemLongPressListener(mItemLongPressListener);
        mRecyclerView.enableItemSwipe();
        mRecyclerView.enableItemDrag();
        mRecyclerView.setOnLoadMoreListener(new CdsRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // add a progress view and start the loading, I'm adding a
                // a string saying loading.
                mRecyclerViewAdapter.addItem("Loading", mRecyclerViewAdapter.getItemCount());
                // I'm using a handler to delay to imitate loading time.
                // so when loading is actually done, remove the loading string or
                // progress bar and set the recycler view setLoading as false
                // and add the items that you want to load.
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mRecyclerViewAdapter.removeItem(mRecyclerViewAdapter.getItemCount()-1);
                        mRecyclerView.setLoading(false);
                        // add new items here.
                        //You can remove the load more listener like this
                        mRecyclerView.removeOnLoadMoreListener();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }, 5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.enableitemclick:
                mRecyclerView.setItemClickListener(mItemClickListener);
                break;
            case R.id.enableitemlongclick:
                mRecyclerView.setItemLongPressListener(mItemLongPressListener);
                break;
            case R.id.disableitemclick:
                mRecyclerView.disableItemClick();
                break;
            case R.id.disableitemlongclick:
                mRecyclerView.disableItemLongPress();
                break;
            case R.id.enableDrag:
                mRecyclerView.enableItemDrag();
                break;
            case R.id.enableSwipe:
                mRecyclerView.enableItemSwipe();
                break;
            case R.id.disableDrag:
                mRecyclerView.disableItemDrag();
                break;
            case R.id.disableSwipe:
                mRecyclerView.disableItemSwipe();
                break;
            case R.id.enableDragCompleteListener:
                mRecyclerView.setItemDragCompleteListener(mItemDragCompleteListener);
                break;
            case R.id.enableSwipeCompleteListener:
                mRecyclerView.setItemSwipeCompleteListener(mItemSwipeCompleteListener);
                break;
            case R.id.disableDragCompleteListener:
                mRecyclerView.removeItemDragCompleteListener();
                break;
            case R.id.disableSwipeCompleteListener:
                mRecyclerView.removeItemSwipeCompleteListener();
                break;
        }
        return false;
    }

    private void initListeners() {
        mItemClickListener = new CdsRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "Item Clicked:" +
                        mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        };
        mItemLongPressListener = new CdsRecyclerView.ItemLongPressListener() {
            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(MainActivity.this, "Item Long Clicked:" +
                        mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        };
        mItemDragCompleteListener = new CdsItemTouchCallback.ItemDragCompleteListener() {
            @Override
            public void onItemDragComplete(int fromPosition, int toPosition) {
                Toast.makeText(MainActivity.this, "Item dragged from " + fromPosition +
                        " to " + toPosition, Toast.LENGTH_SHORT).show();
            }
        };
        mItemSwipeCompleteListener = new CdsItemTouchCallback.ItemSwipeCompleteListener() {
            @Override
            public void onItemSwipeComplete(int position) {
                Toast.makeText(MainActivity.this, "Item was swiped:" + position,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Life");
            list.add("Is Simple.");
            list.add("Are you Happy?");
            list.add("Yes? Keep Going.");
            list.add("No? Change Something.");
        }
        return list;
    }
}

