package main.nini.com.iread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import main.nini.com.iread.R;
import main.nini.com.iread.bean.BDirectBean;

/**
 * Created by zyf on 2017/2/12.
 */

public class ContentDirectAdapter extends BaseAdapter {

    private Context context;
    private List<BDirectBean.ChaptersBean> data;

    public ContentDirectAdapter(Context context, List<BDirectBean.ChaptersBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DirectHolder holder = null;
        BDirectBean.ChaptersBean bean = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_content_direct, parent, false);
            holder = new DirectHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (DirectHolder) convertView.getTag();
        }

        holder.vipIv.setVisibility(View.INVISIBLE);
        holder.titleTv.setText(bean.getTitle());
        holder.idTv.setText(String.valueOf(position + 1));
        if(bean.isIsVip()){
            holder.vipIv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class DirectHolder {
        TextView idTv, titleTv;
        ImageView vipIv;

        public DirectHolder(View view) {
            idTv = (TextView) view.findViewById(R.id.item_direct_id_tv);
            titleTv = (TextView) view.findViewById(R.id.item_direct_title_tv);
            vipIv = (ImageView) view.findViewById(R.id.item_vip_iv);
        }
    }
}
