package main.nini.com.iread.my_util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zyf on 2017/3/12.
 */

public class BooksDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public BooksDecoration(int space) {
        this.space = space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;

        //如果是第二个view的话，就在该view的上方给一个间距
        //因为第一个View是头布局，不需要加间距
//        if (parent.getChildAdapterPosition(view) == 1) {
//            outRect.top = space;
//        }

    }


}
