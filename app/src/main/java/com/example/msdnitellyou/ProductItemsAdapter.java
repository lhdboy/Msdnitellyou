package com.example.msdnitellyou;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msdnitellyou.model.Project;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ProductItemsAdapter extends RecyclerView.Adapter<ProductItemsAdapter.ProductItemsViewHolder> {
    private List<Project> mList;
    private Context context;

    public ProductItemsAdapter(Context context,List<Project> list){
        mList = list;
        this.context = context;
    }

    @Override
    public ProductItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_product_item,parent,false);
        final ProductItemsViewHolder viewHolder = new ProductItemsViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager manager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clipData = ClipData.newPlainText("text",mList.get(viewHolder.getAdapterPosition()).getUrl());
                manager.setPrimaryClip(clipData);

                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductItemsViewHolder holder, int position) {
        //TODO 赋值
        holder.productName.setText(mList.get(position).getName());
        holder.productUrl.setText(mList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ProductItemsViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView productName;
        TextView productUrl;
        public ProductItemsViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            productName = (TextView)itemView.findViewById(R.id.product_item_name);
            productUrl = (TextView)itemView.findViewById(R.id.product_item_url);
        }
    }
}
