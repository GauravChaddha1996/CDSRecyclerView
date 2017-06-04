# Click-Drag-Swipe (CDS) RecyclerView
Recycler View Library with built-in Item click listeners,drag and swipe functionality.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Click--Drag--Swipe%20(CDS)%20RecyclerView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4897)
## Description

CDS Recycler View is a recycler view with item click, item long press, drag and swipe functionality.
It provides setter functions for item clicks, item long press, drag complete listeners( listener called when drag of 
an item completes),swipe complete listeners( listeners called when an item is swiped off).It also provide add and remove
built-in functions for the recycler view adapter.

## Gradle Usage

Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
	  }
}
```

Step 2. Add the dependency

```
dependencies {
        compile 'com.github.GauravChaddha1996:CDSRecyclerView:1.0'
}
```

## Usage
  
  *Drag and swipe functionality is disabled by defualt.Use the enable functions to add them after recycler view initialization*
  
  *Without extending CdsRecyclerViewAdapter the drag and swipe functionality won't be present*
  --------------------------------------------------------------------------------------------
  
  
  **Include the CdsRecyclerView in your layout:**

  ```
  <com.gaurav.cdsrecyclerview.CdsRecyclerView
      android:id="@+id/recyclerView"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

  </com.gaurav.cdsrecyclerview.CdsRecyclerView>
  ```
  
  **Create a recycler view adapter extending CdsRecyclerViewAdapter to get add, remove, drag and swipe functionality.**
  
  *(Note: Without extending CdsRecyclerViewAdapter the drag and swipe functionality won't be present)*
  
  ```
    public class RecyclerViewAdapter extends CdsRecyclerViewAdapter<String, RecyclerViewAdapter.MyViewHolder> {
    
    private Context mContext;
    
    public RecyclerViewAdapter(Context context, List<String> list) {
        super(context, list);
        mContext = context;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.your_item_layout, parent, false));
    }
    
    // note that we override the bindHolder custom function to get back the
    // custom view holder instead of the usual onBindViewHolder()
    @Override
    public void bindHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(getList().get(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_recycler_view_text);
        }
    }
  }
  ```

  **Initialize the Cdsrecycler view as you would for a normal recycler view**

  ```
  mRecyclerView = new CdsRecyclerView(this);
  mRecyclerView = (CdsRecyclerView) findViewById(R.id.recyclerView);
  mRecyclerView.setHasFixedSize(true);
  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  //or any other layout manager
  ```
  
  **Set the Adapter**
  
  ```
  mRecyclerViewAdapter = new RecyclerViewAdapter(this, getData());
  mRecyclerView.setAdapter(mRecyclerViewAdapter);
  ```
  
  **Setting on item click,long press,swipe and drag functionality**

  ```
  mRecyclerView.setItemClickListener(new CdsRecyclerView.ItemClickListener() {
          @Override
          public void onItemClick(int position) {
          
              Toast.makeText(MainActivity.this, "Item Clicked:" +
                      mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
          
          }
      });
      
  mRecyclerView.setItemLongPressListener(new CdsRecyclerView.ItemLongPressListener() {
          @Override
          public void onItemLongClick(int position) {
      
              Toast.makeText(MainActivity.this, "Item Long Clicked:" +
                      mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
      
          }
      });
      
  mRecyclerView.enableItemSwipe();
  mRecyclerView.enableItemDrag();
  ```
## Features and functions

**1.Set and disable item click listeners**

  + Function to set:
  ```
  mRecyclerView.setItemClickListener(new CdsRecyclerView.ItemClickListener() {
          @Override
          public void onItemClick(int position) {
          
              Toast.makeText(MainActivity.this, "Item Clicked:" +
                      mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
          
          }
      });
  ```
  
  + Function to disable :
  ```
  mRecyclerView.disableItemClick();
  ```
  
**2.Set and disable item long press**

  + Function to set:
  ```
  mRecyclerView.setItemLongPressListener(new CdsRecyclerView.ItemLongPressListener() {
          @Override
          public void onItemLongClick(int position) {
      
              Toast.makeText(MainActivity.this, "Item Long Clicked:" +
                      mRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
      
          }
      });
  ```
  
  + Function to disable :
  ```
  mRecyclerView.disableItemLongPress();
  ```

**3.Enable and disable drag**

  + Enable drag:
  
    ```
      mRecyclerView.enableItemDrag();
    ```
  + Disable drag:
  
    ```
    mRecyclerView.disableItemDrag();  
    ```
    
**4.Enable and disable swipe**

  + Enable swipe:
  
    ```
    mRecyclerView.enableItemSwipe();
    ```
  + Disable swipe:
  
    ```
    mRecyclerView.disableItemSwipe()
    ```

**5.Set and remove drag complete listener**

  + Set drag complete listener:
  
    ```
     mRecyclerView.setItemDragCompleteListener( new CdsItemTouchCallback.ItemDragCompleteListener() {
          @Override
          public void onItemDragComplete(int fromPosition, int toPosition) {
              Toast.makeText(MainActivity.this, "Item dragged from " + fromPosition +
                      " to " + toPosition, Toast.LENGTH_SHORT).show();
          }
      });
    ```
  + Remove drag complete listener:
  
    ```
     mRecyclerView.removeItemDragCompleteListener();
    ```


**6.Enable and disable swipe complete listener**

  + Set swipe complete listener:
  
    ```
      mRecyclerView.setItemSwipeCompleteListener(new CdsItemTouchCallback.ItemSwipeCompleteListener() {
          @Override
          public void onItemSwipeComplete(int position) {
              Toast.makeText(MainActivity.this, "Item was swiped:" + position,
                      Toast.LENGTH_SHORT).show();
          }
      });
    ```
  + Remove swipe complete listener:
  
    ```
    mRecyclerView.removeItemSwipeCompleteListener();
    ```
    
## License

``` 
Copyright 2016-present Gaurav Chaddha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
