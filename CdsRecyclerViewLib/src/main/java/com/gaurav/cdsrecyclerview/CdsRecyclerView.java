package com.gaurav.cdsrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by gaurav on 27/8/16.
 */
public class CdsRecyclerView extends RecyclerView implements ItemClickSupport.OnItemClickListener, ItemClickSupport.OnItemLongClickListener {
    private Context mContext;
    // Listener for item click
    private ItemClickListener mItemClickListener = null;
    //Listener for long presses on items
    private ItemLongPressListener mItemLongPressListener = null;
    //Callback for drag and swipe cases
    private CdsItemTouchCallback mCdsItemTouchCallback = null;
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean isLoading = false;
    private LoadMoreListener loadMoreListener;
    private OnScrollListener scrollListener;
    private int visibleThreshold;

    public CdsRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public CdsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CdsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        init();
    }

    //Initializing the click,long press,drag and swipe functionality but not setting the acualt listeners
    public void init() {
        ItemClickSupport.addTo(this)
                .setOnItemClickListener(this)
                .setOnItemLongClickListener(this);
        mCdsItemTouchCallback = new CdsItemTouchCallback((CdsRecyclerViewAdapter) getAdapter());
        mCdsItemTouchCallback.setItemDragEnabled(false);
        mCdsItemTouchCallback.setItemSwipeEnabled(false);
        new ItemTouchHelper(mCdsItemTouchCallback).attachToRecyclerView(this);
        scrollListener = new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalItemCount = getLayoutManager().getItemCount();
                lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        };
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(position);
        }
    }

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        if (mItemLongPressListener != null) {
            mItemLongPressListener.onItemLongClick(position);
            return true;
        }
        return false;
    }

    //Sets Recycler View item clicks
    public void setItemClickListener(ItemClickListener ItemClickListener) {
        mItemClickListener = ItemClickListener;
    }

    //Sets Recycler View item long presses
    public void setItemLongPressListener(ItemLongPressListener ItemLongPressListener) {
        mItemLongPressListener = ItemLongPressListener;
    }

    //Disables Recycler View item clicks
    public void disableItemClick() {
        mItemClickListener = null;
    }

    //Disables Recycler View item long press
    public void disableItemLongPress() {
        mItemLongPressListener = null;
    }

    //Enables Recycler View item drag functionality
    public void enableItemDrag() {
        mCdsItemTouchCallback.setItemDragEnabled(true);
    }

    //Enables Recycler View item swipe functionality
    public void enableItemSwipe() {
        mCdsItemTouchCallback.setItemSwipeEnabled(true);
    }

    //Disables Recycler View item drag functionality
    public void disableItemDrag() {
        mCdsItemTouchCallback.setItemDragEnabled(false);
    }

    //Disables Recycler View item swipe functionality
    public void disableItemSwipe() {
        mCdsItemTouchCallback.setItemSwipeEnabled(false);
    }

    //Sets the callback called after item drag completes
    public void setItemDragCompleteListener(CdsItemTouchCallback.ItemDragCompleteListener itemDragCompleteListener) {
        if (!mCdsItemTouchCallback.isItemDragEnabled()) {
            Log.e("CdsRecyclerView", "Drag is disabled.Not setting Drag Complete Listener.Please Enable Drag.");
        }
        mCdsItemTouchCallback.setItemDragCompleteListener(itemDragCompleteListener);
    }

    //Sets the callback called after item swipe completes
    public void setItemSwipeCompleteListener(CdsItemTouchCallback.ItemSwipeCompleteListener itemSwipeCompleteListener) {
        if (!mCdsItemTouchCallback.isItemSwipeEnabled()) {
            Log.e("CdsRecyclerView", "Swipe is disabled.Not setting Swipe Complete Listener.Please Enable Swipe.");
        }
        mCdsItemTouchCallback.setItemSwipeCompleteListener(itemSwipeCompleteListener);
    }

    //Removes the callback called after item drag completes
    public void removeItemDragCompleteListener() {
        mCdsItemTouchCallback.setItemDragCompleteListener(null);
    }

    //Removes the callback called after item swipe completes
    public void removeItemSwipeCompleteListener() {
        mCdsItemTouchCallback.setItemSwipeCompleteListener(null);
    }

    /**
     * Only works with linear layout manager.
     */
    public void setOnLoadMoreListener(LoadMoreListener loadMoreListener, int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
        this.loadMoreListener = loadMoreListener;
        this.addOnScrollListener(scrollListener);
    }

    public void removeOnLoadMoreListener() {
        this.loadMoreListener = null;
        this.removeOnScrollListener(scrollListener);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public interface ItemClickListener {
        public void onItemClick(int position);
    }

    public interface ItemLongPressListener {
        public void onItemLongClick(int position);
    }

    public interface LoadMoreListener {
        public void onLoadMore();
    }

}
