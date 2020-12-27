package com.hznu.transactionofidlegoods.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hznu.transactionofidlegoods.R;

public class MyItemGroup extends FrameLayout {

    // 组合控件的布局
    private LinearLayout itemGroupLayout;
    private TextView titleTextView;

    public void setContentTextViewText(String text) {
        this.contentTextView.setText(text);
    }

    public String getContentTextViewText() {
        return this.contentTextView.getText().toString();
    }

    private TextView contentTextView;
    private ImageView jtRightImageView;

    public TextView getContentTextView() {
        return contentTextView;
    }

    public MyItemGroup(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MyItemGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public MyItemGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_item_group, null);
        itemGroupLayout = (LinearLayout) view.findViewById(R.id.item_group_layout);
        titleTextView = (TextView) view.findViewById(R.id.title_tv);
        contentTextView = (TextView) view.findViewById(R.id.content_edt);
        jtRightImageView = (ImageView) view.findViewById(R.id.jt_right_iv);
        // 把自定义的这个组合控件的布局加入到当前FramLayout
        addView(view);
    }

    /**
     * 初始化相关属性，引入相关属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        // 标题的默认字体颜色
        int defaultTitleColor = context.getResources().getColor(R.color.gray3);
        // 输入框的默认字体颜色
        int defaultEdtColor = context.getResources().getColor(R.color.black0);
        // 输入框的默认的提示内容的字体颜色
        int defaultHintColor = context.getResources().getColor(R.color.gray9);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroup);
        String title = typedArray.getString(R.styleable.ItemGroup_title);
        float paddingLeft = typedArray.getDimension(R.styleable.ItemGroup_paddingLeft, 15);
        float paddingRight = typedArray.getDimension(R.styleable.ItemGroup_paddingRight, 15);
        float paddingTop = typedArray.getDimension(R.styleable.ItemGroup_paddingTop, 5);
        float paddingBottom = typedArray.getDimension(R.styleable.ItemGroup_paddingTop, 5);
        float titleSize = typedArray.getDimension(R.styleable.ItemGroup_title_size, 15);
        int titleColor = typedArray.getColor(R.styleable.ItemGroup_title_color, defaultTitleColor);
        String content = typedArray.getString(R.styleable.ItemGroup_edt_content);
        float contentSize = typedArray.getDimension(R.styleable.ItemGroup_edt_text_size, 13);
        int contentColor = typedArray.getColor(R.styleable.ItemGroup_edt_text_color, defaultEdtColor);
        String hintContent = typedArray.getString(R.styleable.ItemGroup_edt_hint_content);
        int hintColor = typedArray.getColor(R.styleable.ItemGroup_edt_hint_text_color, defaultHintColor);
        // 默认输入框可以编辑
        boolean isEditable = typedArray.getBoolean(R.styleable.ItemGroup_isEditable, true);
        // 向右的箭头图标是否可见，默认可见
        boolean showJtIcon = typedArray.getBoolean(R.styleable.ItemGroup_jt_visible, true);
        typedArray.recycle();

        // 设置数据
        // 设置item的内边距
        itemGroupLayout.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        titleTextView.setText(title);
        titleTextView.setTextSize(titleSize);
        titleTextView.setTextColor(titleColor);

        contentTextView.setText(content);
        contentTextView.setTextSize(contentSize);
        contentTextView.setTextColor(contentColor);
        contentTextView.setHint(hintContent);
        contentTextView.setHintTextColor(hintColor);
//        contentEdt.setFocusable(isEditable); //设置输入框是否可以编辑
        jtRightImageView.setVisibility(showJtIcon ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
    }
}
