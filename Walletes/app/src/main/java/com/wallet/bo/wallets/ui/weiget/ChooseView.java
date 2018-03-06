package com.wallet.bo.wallets.ui.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wallet.bo.wallets.R;

import java.util.HashMap;
import java.util.Map;


/**
 * @author pu xiaobo
 * @version 1.0
 * @serial 实现单选/多选功能菜单
 */
public class ChooseView extends LinearLayout {

    private TextView cvTitle;//选择框标题
    private RecyclerView recyclerView;
    private boolean model;//true:多选
    private boolean orientation; //true:横向
    private String[] strings;//按键名
    private Map<Integer, Boolean> isChoose; //记录按键选中
    private Integer[] ck;//预选
    private ChooseAdapter chooseAdapter;
    private OnItemClickListener itemClickListener;
    private final int limit = 5;
    private boolean spinner;
    private Integer[] clickables;//多选必须选项

    /**
     * @description: 传入要预选的下标
     */
    public ChooseView setInitCk(Integer[] initCk) {
        this.initCk = initCk;
        return this;
    }

    private Integer[] initCk;


    public ChooseView(Context context) {
        this(context, null);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ChooseView setSpinner(boolean spinner) {
        this.spinner = spinner;
        return this;
    }

    public ChooseView setClickables(Integer[] clickables) {
        this.clickables = clickables;
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.chooseview, this);
        cvTitle = (TextView) findViewById(R.id.cv_title);
        recyclerView = (RecyclerView) findViewById(R.id.cv_rv);

        parseStyle(context, attrs);
    }


    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChooseView);
            String title = ta.getString(R.styleable.ChooseView_title);
            int titleColor = ta.getColor(R.styleable.ChooseView_titleColor, Color.parseColor("#888888"));
            float titleSize = ta.getFloat(R.styleable.ChooseView_titleSize, 10);
            spinner = ta.getBoolean(R.styleable.ChooseView_spinner, false);
            cvTitle.setText(title);
            cvTitle.setTextColor(titleColor);
            cvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
            ta.recycle();
        }
    }

    /**
     * @deprecated 此方法用 setInitChoose替代
     */
    private void initChoose() {//预选
        if (ck != null)
            for (int position = 0; position < ck.length; position++) {
                if (ck[position] == 1) {
                    isChoose.put(position, true);
                }
            }
    }


    private void setInitChoose() {//预选
        if (initCk != null) {
            if (!model)//单选
                isChoose.put(initCk[0], true);
            else
                for (Integer index : initCk) {
                    isChoose.put(index, true);
                }
        }
    }

    public void show() {
        chooseAdapter = new ChooseAdapter();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = null;

        if (orientation)
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        else
            layoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chooseAdapter);
        reset();
    }

    public void reset() {//重置
        isChoose.clear();
        initChoose();
        setInitChoose();
        chooseAdapter.notifyDataSetChanged();
    }


    public ChooseView setModel(boolean model) {
        this.model = model;
        return this;
    }

    public ChooseView setOrientation(boolean orientation) {
        this.orientation = orientation;
        return this;
    }

    public ChooseView setStrings(String[] strings) {
        this.strings = strings;
        return this;
    }

    /**
     * @description: 要预选的下标置1，否则置0
     * @deprecated 此方法用 setInitCk替代
     */
    public ChooseView setCk(Integer[] ck) {
        this.ck = ck;
        return this;
    }

    class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

        public ChooseAdapter() {
            isChoose = new HashMap<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chooseview_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            if (clickables != null)
                if (clickables[position] == 1) {
                    holder.textView.setClickable(true);
                    ((View) holder.textView.getParent()).setClickable(true);
                } else {
                    holder.textView.setClickable(false);
                    ((View) holder.textView.getParent()).setClickable(false);
                }
            if (isChoose.containsKey(position))
                holder.textView.setSelected(true);
            else
                holder.textView.setSelected(false);
            holder.textView.setText(strings[position]);

        }

        @Override
        public int getItemCount() {
            return strings.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.cv_item);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                if (model) {//多选
                    if (isChoose.containsKey(getLayoutPosition())) {
                        isChoose.remove(getLayoutPosition());
                    } else {
                        isChoose.put(getLayoutPosition(), true);
                    }
                } else {//单选
                    for (Integer key : isChoose.keySet()) {
                        if (key == getLayoutPosition())
                            continue;
                        isChoose.remove(key);
                    }
                    if (isChoose.containsKey(getLayoutPosition())) {
                        if (!spinner)
                            isChoose.remove(getLayoutPosition());
                    } else {
                        isChoose.put(getLayoutPosition(), true);
                    }
                }
                notifyDataSetChanged();
                if (itemClickListener != null)
                    itemClickListener.onItemClick(isChoose);
            }
        }

    }

    float mDownX;
    float mDownY;

    public Map<Integer, Boolean> getIsChoose() {
        return isChoose;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (orientation) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getX();
                    mDownY = event.getY();
                    //ACTION_DOWN的时候，赶紧把事件hold住
                    break;
                case MotionEvent.ACTION_MOVE:
                    //焦点在view上且不在竖直方向上滑动，才处理此view事件
                    if (isViewUnder(recyclerView, mDownX, mDownY) && Math.abs(event.getY() - mDownY) < limit) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
            }

        }
        return super.onInterceptTouchEvent(event);
    }

    public boolean isViewUnder(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        return x >= view.getLeft()
                && x < view.getRight()
                && y >= view.getTop()
                && y < view.getBottom();
    }


    public interface OnItemClickListener {
        void onItemClick(Map<Integer, Boolean> isChoose);
    }
}
