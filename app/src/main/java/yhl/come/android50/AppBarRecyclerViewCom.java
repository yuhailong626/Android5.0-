package yhl.come.android50;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by yuhailong on 15/11/29.
 */
public class AppBarRecyclerViewCom extends AppCompatActivity {
    public static final String KEY_TYPE="type";
    public static final String TYPE_COLLASPING="type1";
    public static final String TYPE_APPBAR="type2";


    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        String type = getIntent().getStringExtra(KEY_TYPE);
        if (TYPE_COLLASPING.equals(type)) {
            setContentView(R.layout.collapsingtoobarlayout_recyclerview);
        }else{
            setContentView(R.layout.app_bar_recyclerview);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initRecyclerView();
        initTabs();

    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(new RecylerViewAdapter(this));

//        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
    }

    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        tabLayout.addTab(tabLayout.newTab().setText("线性布局").setTag(1));
        tabLayout.addTab(tabLayout.newTab().setText("九宫格").setTag(2));
        tabLayout.addTab(tabLayout.newTab().setText("瀑布流").setTag(3));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tag = (int) tab.getTag();
                if (tag == 1) {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(AppBarRecyclerViewCom.this));
                } else if (tag == 2) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(AppBarRecyclerViewCom.this, 2));//这里用线性宫格显示 类似于grid view
                } else if (tag == 3) {
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
