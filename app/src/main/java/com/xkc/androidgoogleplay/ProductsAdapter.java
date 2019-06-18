package com.xkc.androidgoogleplay;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.billingclient.api.SkuDetails;


import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private OnCallListener onCallListener;
    private List<SkuDetails> list;
    interface  OnCallListener{
        void call (SkuDetails skuDetails);
    }
    public ProductsAdapter(List<SkuDetails> list, OnCallListener onCallListener ){
        this.list = list;
        this.onCallListener = onCallListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,final int i) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        ViewHolder viewHolder  =  new ViewHolder(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallListener.call(list.get(i));
            }
        });
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(list.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

           private TextView mTextView;
            ViewHolder(TextView textView) {
                super(textView);
                mTextView = textView;
            }
        }
}
