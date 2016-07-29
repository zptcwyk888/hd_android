package net.huadong.pd.android.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.slidingmenu.lib.SlidingMenu;
import net.huadong.pd.android.R;
import net.huadong.pd.android.activity.base.LoginActivity;
import net.huadong.pd.android.listbuddies.ui.MainActivity;
import net.huadong.pd.android.adapter.Example1Adapter;
import net.huadong.pd.android.eneity.examEneity;
import net.huadong.pd.android.util.UpdateManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ExampleActivity1 extends ListActivity implements OnClickListener {
    private SlidingMenu right_menu;
    private PullToRefreshListView myRefreshListview;
    private TextView init_text;
    private ProgressBar init_progree;
    private ListView mlistview;
    public int page = 1;
    private int pageCount;
    private int total;
    private ArrayList<examEneity> examList;
    private Example1Adapter madpter;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    myRefreshListview.onRefreshComplete();
                    break;
                case 2:
                    myRefreshListview.getLoadingLayoutProxy().setRefreshingLabel("正在加载数据...");
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.example_layout1);
        //注册id及监听事件
        init();
        examList=new ArrayList<examEneity>();
        madpter=new Example1Adapter(examList,this);
        mlistview.setAdapter(madpter);
        GetDataTask getdada=new GetDataTask();
        getdada.execute();
        myRefreshListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 显示上次刷新的时间
                String label = DateUtils. formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL) ;
                refreshView . getLoadingLayoutProxy ( )
                        . setLastUpdatedLabel ( label ) ;
                page = 1;
                GetDataTask getdada=new GetDataTask();
                getdada.execute();
//              getdata(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                int result = total % 10;
                if (result == 0) {
                    pageCount = total / 10;
                } else {
                    pageCount = total / 10 + 1;
                }
                if (page <= pageCount) {
                    GetDataTask getdada=new GetDataTask();
                    getdada.execute();
//                    getdata(page);
                } else {
                    page--;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        });
        //透明状态栏
        RelativeLayout getwork_phonebar = (RelativeLayout) findViewById(R.id.getwork_phonebar);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getwork_phonebar.setVisibility(View.GONE);
        }
        initSlidingMenu();
        //用于解决侧边栏和透明状态栏（沉浸式状态栏）冲突
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.red);
        }
    }
    public void init(){
        init_text = (TextView) findViewById(R.id.init_text);
        myRefreshListview = (PullToRefreshListView) findViewById(R.id.mpullToRefreshListView);
        myRefreshListview.setMode(PullToRefreshBase.Mode.BOTH);
        mlistview = (ListView) myRefreshListview.getRefreshableView();
        init_progree=(ProgressBar)findViewById(R.id.init_progree);
        findViewById(R.id.getwork_back).setOnClickListener(this);
        findViewById(R.id.topRightText).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getwork_back:
                finish();
                break;
            case R.id.topRightText:
                Intent intent =new Intent(ExampleActivity1.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }




    /**
     * 设置侧边栏
     */
    public void initSlidingMenu() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        right_menu = new SlidingMenu(getApplicationContext());
        right_menu.setMode(SlidingMenu.RIGHT);// 设置右侧边栏
        right_menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
        right_menu.setBehindWidth(width - 100);//设置SlidingMenu菜单的宽度
//      right_menu.setBehindOffsetRes(R.dimen.left_menu);// 划出时主页面显示的剩余宽度
        right_menu.setFadeDegree(0.35f);// SlidingMenu滑动时的渐变程度
        right_menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        right_menu.setSlidingEnabled(true);
        right_menu.setMenu(R.layout.f_right_drawle_setting);
        //取消
        right_menu.findViewById(R.id.cancel_text).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                right_menu.toggle();
            }
        });
        //修改密码
        right_menu.findViewById(R.id.edit_password).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //系统升级
        right_menu.findViewById(R.id.updata_version).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager manager = new UpdateManager(ExampleActivity1.this);
                manager.checkUpdate(true);
            }
        });
        //退出登录
        right_menu.findViewById(R.id.out_login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spread = ExampleActivity1.this.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=spread.edit();
                editor.putInt("isLogin",0);
                editor.commit();
                Intent intent=new Intent(ExampleActivity1.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //正式加载数据
    public void getdata(int page) {
        HttpUtils http = new HttpUtils();
        if (page == 1) {
            examList.clear();
        }
        String getAllPalletUrl = "";
        http.send(HttpRequest.HttpMethod.GET, getAllPalletUrl, new RequestCallBack<String>() {
            @Override
            public void onLoading(long total, long current,
                                  boolean isUploading) {
            }
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject obj;
                JSONArray jsonarray;
                try {
                    obj = new JSONObject(responseInfo.result);
                    //状态1-正常，0-登陆过期，-1-失败
                    if (obj.getString("status").equals("1")) {
                        JSONObject o = new JSONObject(obj.getString("data"));
                        total = o.getInt("allnum");
                        jsonarray = new JSONArray(o.getString("list"));
                        init_progree.setVisibility(View.GONE);
                        madpter.notifyDataSetChanged();
                        myRefreshListview.onRefreshComplete();
                        if (examList.size() == 0) {
                          init_text.setText("休息休息一下!");
                          init_text.setVisibility(View.VISIBLE);
                        } else {
                            init_text.setVisibility(View.GONE);
                        }
                    } else if (obj.getString("status").equals("0")) {

                            Toast.makeText(ExampleActivity1.this, obj.getString("info"), Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = ExampleActivity1.this.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("uid", "");
                            editor.putInt("isLogin", 0);
                            editor.putString("token", "");
                            editor.commit();
                            startActivity(new Intent(ExampleActivity1.this, LoginActivity.class));
                            overridePendingTransition(R.anim.zoomin,
                                    R.anim.zoomout);
                             finish();
                        } else {
                            Toast.makeText(ExampleActivity1.this,obj.getString("info"),Toast.LENGTH_LONG).show();
                            init_progree.setVisibility(View.GONE);
                            init_text.setText("休息休息一下!");
                            init_text.setVisibility(View.VISIBLE);
                            madpter.notifyDataSetChanged();
                            myRefreshListview.onRefreshComplete();

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    init_progree.setVisibility(View.GONE);
                    init_text.setText("数据异常，请联系客服!");
                    init_text.setVisibility(View.VISIBLE);
                    madpter.notifyDataSetChanged();
                    myRefreshListview.onRefreshComplete();
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(ExampleActivity1.this,"网络错误！",Toast.LENGTH_LONG).show();
                myRefreshListview.onRefreshComplete();
                madpter.notifyDataSetChanged();
                init_progree.setVisibility(View.GONE);
                init_text.setText("网络错误，请检查网络!");
                init_text.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    //模拟加载数据
    private int mItemCount = 0 ;
    private class GetDataTask extends AsyncTask< Void , Void , examEneity >
    {
        @Override
        protected examEneity doInBackground(Void... params) {
            try
            {
                Thread . sleep ( 2000 ) ;
            } catch ( InterruptedException e )
            {
            }
            examEneity exam=new examEneity();
            exam.setText("" + ( mItemCount ++ ));
            if(mItemCount%3==0){
                exam.setImgUrl("http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            }else if(mItemCount%3==1){
                exam.setImgUrl("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            }else if(mItemCount%3==2){
                exam.setImgUrl("http://cdn3.nflximg.net/images/3093/2043093.jpg");
            }
            return exam ;
        }

        @ Override
        protected void onPostExecute ( examEneity exam )
        {
            examList.add( exam ) ;
            madpter.notifyDataSetChanged() ;
            myRefreshListview.onRefreshComplete();
            init_progree.setVisibility(View.GONE);
            super.onPostExecute(exam);
        }
    }
}



