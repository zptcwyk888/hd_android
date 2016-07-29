package net.huadong.pd.android.activity.base;

import java.util.ArrayList;

import net.huadong.pd.android.R;
import net.huadong.pd.android.adapter.ViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.baidu.mobstat.StatService;

public class ViewPagerActivity extends Activity {

    private int[] imgs = {R.drawable.help_01,
            R.drawable.help_02,
            R.drawable.help_03};
    ImageView view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        ArrayList<View> alist = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            view = new ImageView(this);
            view.setLayoutParams(new LayoutParams
                    (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            view.setScaleType(ScaleType.FIT_XY);
            view.setImageResource(imgs[i]);
            alist.add(view);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(alist);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                if (i == (imgs.length - 1)) {
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
                            intent.putExtra("flag", "login");
                            startActivity(intent);
                            finish();
                        }
                    });
                }


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
//				if(arg0==(imgs.length-1)){
////					try {
////						Thread.sleep(2000);
////					} catch (InterruptedException e) {
////						e.printStackTrace();
////					}
//					Intent intent=new Intent(ViewPagerActivity.this,MainActivity.class);
//					//intent.putExtra("flag", "login");
//					startActivity(intent);
//					finish();
//				}
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            System.exit(0);
        }
        return true;
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
}
