package com.qingtai.qingtai.recycleviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter adapter ;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner mSpinner;
    private List<String> mData = new ArrayList<>();
    private Button add;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        mSpinner = (Spinner)findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }else if( i== 1){
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mData.add("Recycler");
        mData.add("Recycler");
        mData.add("Recycler");
        adapter = new RecyclerAdapter(mData);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecycler(view);
            }
        });
        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delRecycler(view);
            }
        });


        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
                int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                Collections.swap(mData,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                mData.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

    }





    public void addRecycler(View view){
        mData.add("Recycler");
        int position = mData.size();
        if(position > 0){
            adapter.notifyDataSetChanged();
        }
    }
    public void delRecycler(View view){
        int position =mData.size();
        if(position > 0){
            mData.remove(position - 1);
            adapter.notifyDataSetChanged();
        }
    }
}
