package main.nini.com.iread.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import main.nini.com.iread.R;
import main.nini.com.iread.my_util.ColorUtil;
import main.nini.com.iread.my_util.inter.OnRecyclerClickListener;
import main.nini.com.iread.my_util.sql.NativeBookTable;

/**
 * Created by zyf on 2017/3/12.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksHolder> {

    private List<NativeBookTable> data;
    private Context context;
    private Random random;
    private OnRecyclerClickListener listener;

    public BooksAdapter(List<NativeBookTable> data, Context context) {
        this.data = data;
        this.context = context;
        random = new Random();
    }

    public void setListener(OnRecyclerClickListener listener){
        this.listener = listener;
    }

    public void addAll(List<NativeBookTable> data){
        this.data.addAll(data);
    }

    public void add(NativeBookTable bean){
        this.data.add(bean);
    }



    @Override
    public BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_native_book_recycler,parent,false);
        BooksHolder booksHolder = new BooksHolder(view);
        return booksHolder;
    }

    @Override
    public void onBindViewHolder(BooksHolder holder, final int position) {
        NativeBookTable bean = data.get(position);
        holder.nameTv.setText(bean.getBookName());
        CardView cardView = (CardView) holder.itemView;
        cardView.setCardBackgroundColor(ColorUtil.getRandomColor(random));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BooksHolder extends RecyclerView.ViewHolder{

        TextView nameTv;

        public BooksHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.native_name_tv);
        }
    }
}
