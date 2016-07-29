package net.huadong.pd.android.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mobstat.StatService;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import net.huadong.pd.android.R;
import net.huadong.pd.android.constant.Constants;
import java.util.HashMap;


public class MainActivity extends Activity implements OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    //登陆常量
    public int isLogin;
    private SliderLayout mDemoSlider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.main_getwork_img).setOnClickListener(this);
        findViewById(R.id.main_getmoney_img).setOnClickListener(this);
        findViewById(R.id.main_order_img).setOnClickListener(this);
        findViewById(R.id.main_me_img).setOnClickListener(this);
        findViewById(R.id.call_customer).setOnClickListener(this);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
//        可以使用本地图片
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("01", R.drawable.turn_01);
//        file_maps.put("02", R.drawable.turn_02);
//        file_maps.put("03", R.drawable.turn_03);
//        for(String name : file_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(this);
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//
//            //添加参数
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            mDemoSlider.addSlider(textSliderView);
//        }
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("01", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("02", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("03", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //添加参数，实例中的弹出提示
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            mDemoSlider.addSlider(textSliderView);
        }
        //设置翻页动画效果
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        //设置页面标识所在位置
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //标题文字动画
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        //翻页间隔时间
        mDemoSlider.setDuration(8000);
        mDemoSlider.addOnPageChangeListener(this);

//     //版本更新
//     UpdateManager manager = new UpdateManager(this);
//     //检查软件更新
//     manager.checkUpdate(false);
//     透明状态栏
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "jV32XjFPhHjlBKAbVokU0iOT");
    }

    @Override
    public void onClick(View v) {
        SharedPreferences spread = this.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        isLogin = spread.getInt("isLogin", 0);
        Intent i = null;
        switch (v.getId()) {
            case R.id.main_getwork_img:
                if (isLogin == 0) {
                    Toast.makeText(this, "您未登录，请先登录！", Toast.LENGTH_SHORT).show();
                    i = new Intent();
                    i.putExtra("goto", "0");
                    i.setClass(this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoomin,
                            R.anim.zoomout);

                } else {
                    i = new Intent();
                    i.putExtra("index", 0);
                    i.setClass(this, TabHostActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.bigin,
                            R.anim.zoomout);
                }

                break;
            case R.id.main_getmoney_img:
                if (isLogin == 0) {
                    Toast.makeText(this, "您未登录，请先登录！", Toast.LENGTH_SHORT).show();
                    i = new Intent();
                    i.putExtra("goto", "2");
                    i.setClass(this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoomin,
                            R.anim.zoomout);
                } else {
                    i = new Intent();
                    i.putExtra("index", 2);
                    i.setClass(this, TabHostActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.bigin,
                            R.anim.zoomout);
                }
                break;
            case R.id.main_order_img:
                if (isLogin == 0) {
                    Toast.makeText(this, "您未登录，请先登录！", Toast.LENGTH_SHORT).show();
                    i = new Intent();
                    i.putExtra("goto", "3");
                    i.setClass(this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoomin,
                            R.anim.zoomout);

                } else {
                    i = new Intent();
                    i.putExtra("index", 3);
                    i.putExtra("token", getIntent().getStringExtra("token"));
                    i.putExtra("uid", getIntent().getStringExtra("uid"));
                    i.setClass(this, TabHostActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.bigin,
                            R.anim.zoomout);
                }
                break;
            case R.id.main_me_img:
                if (isLogin == 0) {
                    Toast.makeText(this, "您未登录，请先登录！", Toast.LENGTH_SHORT).show();
                    i = new Intent();
                    i.putExtra("goto", "1");
                    i.setClass(this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoomin,
                            R.anim.zoomout);
                } else {
                    i = new Intent();
                    i.putExtra("index", 1);
                    i.setClass(this, TabHostActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.bigin,
                            R.anim.zoomout);
                }
                break;
            case R.id.call_customer:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + Constants.CUS_PHONE));
                //启动
                startActivity(phoneIntent);
                break;
            default:
                break;
        }

    }

    long KeyDataTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - KeyDataTime > 2000) {
                    Toast.makeText(this, "再按一次退出拖柜宝", Toast.LENGTH_SHORT).show();
                    KeyDataTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return false;

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //取出传递的参数并toast提示
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
