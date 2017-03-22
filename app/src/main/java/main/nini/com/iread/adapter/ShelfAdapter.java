package main.nini.com.iread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import main.nini.com.iread.R;
import main.nini.com.iread.my_util.inter.OnRecyclerClickListener;
import main.nini.com.iread.response.Collection;

/**
 * Created by zyf on 2017/2/25.
 */

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfHolder> {

    public List<Collection> data;
    public Context context;
    public OnRecyclerClickListener recyclerClickListener;

    public ShelfAdapter(Context context) {
        this.context = context;
    }

    public ShelfAdapter(List<Collection> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<Collection> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addAll(List<Collection> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public Collection getItem(int pos){
        return data.get(pos);
    }

    public void setRecyclerClickListener(OnRecyclerClickListener listener){
        this.recyclerClickListener = listener;
    }

    @Override
    public ShelfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shelf_recycler,parent,false);
        ShelfHolder holder = new ShelfHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShelfHolder holder, final int position) {
        Collection collection = data.get(position);
        holder.titleTv.setText(collection.getBookName());
        Glide.with(context).load(collection.getBookIconUrl()).error(R.mipmap.icon_break).into(holder.iconIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ShelfHolder extends RecyclerView.ViewHolder{

        ImageView iconIv;
        TextView titleTv;

        public ShelfHolder(View itemView) {
            super(itemView);

            iconIv = (ImageView) itemView.findViewById(R.id.item_shelf_icon_iv);
            titleTv = (TextView) itemView.findViewById(R.id.item_shelf_title_tv);
        }
    }
}
