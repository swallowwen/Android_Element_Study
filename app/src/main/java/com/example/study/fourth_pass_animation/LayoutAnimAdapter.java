package com.example.study.fourth_pass_animation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.study.R;

/**
 * Created by swallow on 2018/12/18.
 */

public class LayoutAnimAdapter extends RecyclerView.Adapter<LayoutAnimAdapter.Holder> {

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_layout_anim, null);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.tv.setText("这是第" + (i + 1) + "项");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
