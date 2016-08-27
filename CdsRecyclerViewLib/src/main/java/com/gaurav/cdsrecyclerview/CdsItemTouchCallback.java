package com.gaurav.cdsrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by gaurav on 27/8/16.
 */
public class CdsItemTouchCallback extends ItemTouchHelper.SimpleCallback {
    //Recycler View Adpater to handle the drag and swipe cases
    private CdsRecyclerViewAdapter adapter;
    //Boolean to maintain if the item drag is enabled or not
    private boolean itemDragEnabled = false;
    //Boolean to maintain if the item swipe is enabled or not
    private boolean itemSwipeEnabled = false;
    //Listener for after drag complete
    private ItemDragCompleteListener mItemDragCompleteListener = null;
    //Listener for after swipe complete
    private ItemSwipeCompleteListener mItemSwipeCompleteListener = null;

    public CdsItemTouchCallback(CdsRecyclerViewAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mItemDragCompleteListener != null) {
            mItemDragCompleteListener.onItemDragComplete(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        adapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mItemSwipeCompleteListener != null) {
            mItemSwipeCompleteListener.onItemSwipeComplete(viewHolder.getAdapterPosition());
        }
        adapter.removeItem(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return itemDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return itemSwipeEnabled;
    }

    public void setItemDragCompleteListener(ItemDragCompleteListener itemDragCompleteListener) {
        mItemDragCompleteListener = itemDragCompleteListener;
    }

    public void setItemSwipeCompleteListener(ItemSwipeCompleteListener itemSwipeCompleteListener) {
        mItemSwipeCompleteListener = itemSwipeCompleteListener;
    }

    public boolean isItemDragEnabled() {
        return itemDragEnabled;
    }

    public void setItemDragEnabled(boolean itemDragEnabled) {
        this.itemDragEnabled = itemDragEnabled;
    }

    public boolean isItemSwipeEnabled() {
        return itemSwipeEnabled;
    }

    public void setItemSwipeEnabled(boolean itemSwipeEnabled) {
        this.itemSwipeEnabled = itemSwipeEnabled;
    }


    public interface ItemDragCompleteListener {
        public void onItemDragComplete(int fromPosition, int toPosition);
    }

    public interface ItemSwipeCompleteListener {
        public void onItemSwipeComplete(int position);
    }
}
