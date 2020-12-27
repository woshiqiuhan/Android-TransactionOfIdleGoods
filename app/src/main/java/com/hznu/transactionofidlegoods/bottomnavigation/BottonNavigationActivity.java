package com.hznu.transactionofidlegoods.bottomnavigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.bottomnavigation.ui.add.AddFragment;
import com.hznu.transactionofidlegoods.bottomnavigation.ui.home.HomeFragment;
import com.hznu.transactionofidlegoods.bottomnavigation.ui.message.MessageFragment;
import com.hznu.transactionofidlegoods.bottomnavigation.ui.my.MyFragment;
import com.hznu.transactionofidlegoods.myview.FixFragmentNavigator;
import com.hznu.transactionofidlegoods.utils.BaseActivity;

public class BottonNavigationActivity extends BaseActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_navigation);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // 用跳转目的地fragment构建配置类
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_message, R.id.navigation_my)
                .build();

        // 用fragment容器构建导航控制器
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // 为导航控制器设置配置类
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // 关联NavigationView和导航控制器
        NavigationUI.setupWithNavController(navView, navController);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        //获取页面容器NavHostFragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //获取导航控制器
        NavController navController = NavHostFragment.findNavController(fragment);
        //创建自定义的Fragment导航器
        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
        //获取导航器提供者
        NavigatorProvider provider = navController.getNavigatorProvider();
        //把自定义的Fragment导航器添加进去
        provider.addNavigator(fragmentNavigator);
        //手动创建导航图
        NavGraph navGraph = initNavGraph(provider, fragmentNavigator);
        //设置导航图
        navController.setGraph(navGraph);
        //底部导航设置点击事件
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navController.navigate(item.getItemId());
//                Toast.makeText(BottonNavigationActivity.this, "You clicked me!" + item.getItemId(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    //手动创建导航图，把4个目的地添加进来
    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地
        FragmentNavigator.Destination homeFragment = fragmentNavigator.createDestination();
        homeFragment.setId(R.id.navigation_home);
        homeFragment.setClassName(HomeFragment.class.getCanonicalName());
        homeFragment.setLabel(getResources().getString(R.string.title_home));
        navGraph.addDestination(homeFragment);

        FragmentNavigator.Destination addFragment = fragmentNavigator.createDestination();
        addFragment.setId(R.id.navigation_add);
        addFragment.setClassName(AddFragment.class.getCanonicalName());
        addFragment.setLabel(getResources().getString(R.string.title_add));
        navGraph.addDestination(addFragment);

        FragmentNavigator.Destination messageFragment = fragmentNavigator.createDestination();
        messageFragment.setId(R.id.navigation_message);
        messageFragment.setClassName(MessageFragment.class.getCanonicalName());
        messageFragment.setLabel(getResources().getString(R.string.title_message));
        navGraph.addDestination(messageFragment);

        FragmentNavigator.Destination myFragment = fragmentNavigator.createDestination();
        myFragment.setId(R.id.navigation_my);
        myFragment.setClassName(MyFragment.class.getCanonicalName());
        myFragment.setLabel(getResources().getString(R.string.title_my));
        navGraph.addDestination(myFragment);

        navGraph.setStartDestination(R.id.navigation_home);
        return navGraph;
    }


}