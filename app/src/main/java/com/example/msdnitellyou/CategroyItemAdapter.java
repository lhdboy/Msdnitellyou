package com.example.msdnitellyou;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.msdnitellyou.model.ItemModel;
import java.util.List;

public class CategroyItemAdapter extends RecyclerView.Adapter<CategroyItemAdapter.CategroyItemHolder> {
    private List<ItemModel> mCategroyList;
    private Context mContext;
    private IItemClickListener clickListener;

    public CategroyItemAdapter(Context context,List<ItemModel> itemModels){
        this.mContext = context;
        this.mCategroyList = itemModels;
    }

    @Override
    public CategroyItemAdapter.CategroyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.categroy_item,parent,false);
        final CategroyItemHolder categroyItemHolder = new CategroyItemHolder(view);
        categroyItemHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view,mCategroyList.get(categroyItemHolder.getAdapterPosition()));
            }
        });
        return categroyItemHolder;
    }

    public void setOnItemClick(IItemClickListener click){
        this.clickListener = click;
    }

    @Override
    public void onBindViewHolder(CategroyItemHolder holder, int position) {
        holder.tv.setText(mCategroyList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCategroyList.size();
    }

    class CategroyItemHolder extends RecyclerView.ViewHolder{

        View view;
        TextView tv;
        public CategroyItemHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv = (TextView) itemView.findViewById(R.id.categroy_item_textview);
        }
    }
}
