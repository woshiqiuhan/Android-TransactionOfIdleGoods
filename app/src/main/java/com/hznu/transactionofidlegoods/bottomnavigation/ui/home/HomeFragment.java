package com.hznu.transactionofidlegoods.bottomnavigation.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.utils.FilePersistenceUtil;
import com.hznu.transactionofidlegoods.utils.SoftKeyBoardListener;

import java.util.List;

public class HomeFragment extends Fragment {

    // 数据的列表，即搜索下拉框列表元素，后从数据库获取
    private List<String> searchRecords;

    private SearchView homeFragmentSearchView;
    private ListPopupWindow searchRecordsListPopupWindow;
    private Toolbar homeFragmentHeadToolbar;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //获取搜索记录
        searchRecords = homeViewModel.getSearchRecords(getContext());

        //根据id获取控件
        homeFragmentSearchView = (SearchView) root.findViewById(R.id.searchView_homeSearch);
        homeFragmentSearchView.setSubmitButtonEnabled(true); //设置右端搜索键显示
        homeFragmentHeadToolbar = (Toolbar) root.findViewById(R.id.toolbar_homeSearch);
        //创建下拉列表
        searchRecordsListPopupWindow = new ListPopupWindow(getContext());
        //创建下拉列表数据项
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, searchRecords);
        //绑定下拉列表数据
        searchRecordsListPopupWindow.setAdapter(adapter);
        //绑定锚点，从什么控件下开始展开
        searchRecordsListPopupWindow.setAnchorView(homeFragmentSearchView);
        //为每项数据项绑定事件
        searchRecordsListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchRecordsListPopupWindow.dismiss();  //隐藏下拉框
                //设置searchViewHome内部text
                homeFragmentSearchView.setQuery(searchRecords.get(position), true);
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
                homeFragmentSearchView.clearFocus(); //去除搜索框焦点
//                Toast.makeText(getContext(), "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });

        //监听搜索框被选中，即force监听
        homeFragmentSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchRecordsListPopupWindow.show();

                    /**
                     * 设置长按删除记录功能
                     * 1、首先获取ListPopupWindow中的ListView
                     * 2、然后添加OnItemLongClickListener事件
                     */
                    ListView listView = searchRecordsListPopupWindow.getListView();
                    if (listView != null) {
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                String record = searchRecords.get(position);
                                //创建弹窗
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                dialog.setTitle("提示");
                                dialog.setMessage("删除" + record + "搜索记录？");
//                                dialog.setCancelable(false);
                                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //点击确定后删除文件记录及更新当前ListPopupWindow
                                        try {
                                            homeViewModel.removeSearchRecord(record);
                                            adapter.notifyDataSetChanged();
                                            FilePersistenceUtil.remove(getContext(), record, "searchrecords");

                                            homeFragmentSearchView.setQuery("", false);
                                            Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                                        } catch (Exception ex) {
                                            Toast.makeText(getContext(), "删除失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                /*dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });*/

                                /*dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });*/
                                dialog.show();
                                return true; //返回true表示长按过后的震动效果
                            }
                        });
                    }
                } else {
                    searchRecordsListPopupWindow.dismiss();
                }
            }
        });

        homeFragmentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {  //输入关键字后按下搜索键触发
                homeFragmentSearchView.clearFocus();
                if (query.length() > 0) {
                    Toast.makeText(getContext(), "搜索内容：" + query, Toast.LENGTH_SHORT).show();
                    //添加记录至本地文件
                    boolean flag = FilePersistenceUtil.addHeadDistinct(getContext(), query, FilePersistenceUtil.SREARCH_RECORDS_FILE_NAME);

                    //若文件中存在当前搜索记录
                    if (!flag) {
                        //先将记录移除
                        homeViewModel.removeSearchRecord(query);
                    }
                    //再将记录添加到头部
                    homeViewModel.addSearchRecord(query);
                    adapter.notifyDataSetChanged();

                    return true;
                }
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