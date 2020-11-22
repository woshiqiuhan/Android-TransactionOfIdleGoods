package com.hznu.transactionofidlegoods.bottomnavigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.utils.SoftKeyBoardListener;

public class HomeFragment extends Fragment {

    // 数据的列表，即搜索下拉框列表元素，后从数据库获取
    private final String[] mStrings = {"张三", "李四", "王五", "赵六"};
    private SearchView searchViewHome;
    private ListPopupWindow listPopupWindow;
    private Toolbar toolbar;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //根据id获取控件
        searchViewHome = (SearchView) root.findViewById(R.id.searchView_homeSearch);
        searchViewHome.setSubmitButtonEnabled(true); //设置右端搜索键显示
        toolbar = (Toolbar) root.findViewById(R.id.toolbar_homeSearch);
        //创建下拉列表
        listPopupWindow = new ListPopupWindow(getContext());
        //创建下拉列表数据项
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mStrings);
        //绑定下拉列表数据
        listPopupWindow.setAdapter(adapter);
        //绑定锚点，从什么控件下开始展开
        listPopupWindow.setAnchorView(toolbar);
        //为每项数据项绑定事件
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();  //隐藏下拉框
                //设置searchViewHome内部text
                searchViewHome.setQuery(mStrings[position], true);
            }
        });
        //监听小键盘开启和隐藏
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
//                Toast.makeText(getContext(), "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
                searchViewHome.clearFocus(); //去除搜索框焦点
//                Toast.makeText(getContext(), "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });

        //监听搜索框被选中，即force监听
        searchViewHome.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    listPopupWindow.show();
                } else {
                    listPopupWindow.dismiss();
                }
            }
        });

        searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {  //输入关键字后按下搜索键触发
                searchViewHome.clearFocus();
                Toast.makeText(getContext(), "搜索内容：" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {  //可用于进行关键字筛选
                return true;
            }
        });
        return root;
    }
}