package main.nini.com.iread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import main.nini.com.iread.R;
import main.nini.com.iread.bean.FuzzySearchBean;
import main.nini.com.iread.my_util.MySpUtil;
import main.nini.com.iread.my_util.event.DeleteEvaluateEvent;
import main.nini.com.iread.response.Evaluate;

import static android.R.attr.author;

/**
 * Created by zyf on 2017/2/12.
 */

public class EvaluateAdapter extends BaseAdapter {

    private Context context;
    private List<Evaluate> evaluates;

    public EvaluateAdapter(Context context, List<Evaluate> evaluates) {
        this.context = context;
        this.evaluates = evaluates;
    }

    @Override
    public int getCount() {
        return evaluates.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        EvaluateHolder holder = null;
        final Evaluate bean= evaluates.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_evaluate,parent,false);
            holder = new EvaluateHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (EvaluateHolder) convertView.getTag();
        }
        String content = bean.getContent();
        String userName = bean.getUserName();
        String nickName = bean.getNickName();
        String userIconUrl = bean.getUserIconUrl();
        String date = bean.getDate();

        holder.nameTv.setText(nickName);
        holder.dateTv.setText(date);
        holder.contentTv.setText(content);
        holder.deleteIv.setVisibility(View.GONE);
        if(!userIconUrl.equals("")){
            Glide.with(context).load(userIconUrl).error(R.mipmap.default_head_icon).into(holder.iconIv);
        }else {
            holder.iconIv.setImageResource(R.mipmap.default_head_icon);
        }
        if(bean.getUserId().equals(MySpUtil.getObjectId())){
            holder.deleteIv.setVisibility(View.VISIBLE);
        }
        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteEvaluateEvent deleteEvaluateEvent = new DeleteEvaluateEvent(position,bean);
                EventBus.getDefault().post(deleteEvaluateEvent);
            }
        });
        return convertView;
    }

    class EvaluateHolder{
        TextView nameTv,dateTv,contentTv;
        CircleImageView iconIv;
        ImageView deleteIv;

        public EvaluateHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_evaluate_name_tv);
            dateTv = (TextView) view.findViewById(R.id.item_evaluate_date_tv);
            contentTv = (TextView) view.findViewById(R.id.item_evaluate_content_tv);

            iconIv = (CircleImageView) view.findViewById(R.id.item_evaluate_icon);
            deleteIv = (ImageView) view.findViewById(R.id.item_evaluate_delete_iv);
        }
    }
}
