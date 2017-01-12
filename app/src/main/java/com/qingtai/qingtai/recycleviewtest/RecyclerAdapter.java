package com.qingtai.qingtai.recycleviewtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<String> mData;

    public RecyclerAdapter(List<String> mData) {
        this.mData = mData;
    }


    public OnItemClickListener itemClickListener;


    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView;

        public MyViewHolder(View itemView){
            super(itemView);
            textView= (TextView)itemView;
            textView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(view,getPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mData.get(position) + position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
