package net.huadong.pd.android.activity.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.baidu.mobstat.StatService;

import net.huadong.pd.android.R;

public class WelcomeActivity extends Activity {

	private Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
//		ImageView img=(ImageView)findViewById(R.id.welcome_img);
//		Animation animation=AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
//		img.startAnimation(animation);
//		animation.setAnimationListener(new AnimationListener() {
//			public void onAnimationStart(Animation arg0) {
//			}
//
//			public void onAnimationRepeat(Animation arg0) {
//			}
//
//			public void onAnimationEnd(Animation arg0) {
//				SharedPreferences sp = WelcomeActivity.this.getSharedPreferences("loginXml", Context.MODE_PRIVATE);
//				Intent intent = null;
//				String str = sp.getString("isFrist", "true");
//				if ("true".equals(str)) {
//					Editor editor = sp.edit();
//					editor.putString("isFrist", "false");
//					editor.commit();
//					intent = new Intent(WelcomeActivity.this,
//							ViewPagerActivity.class);
//				} else {
//					intent = new Intent(WelcomeActivity.this,
//							MainActivity.class);
//					overridePendingTransition(R.anim.zoomin,
//							R.anim.zoomout);
//				}
//				startActivity(intent);
//				finish();
//			}
//		});
		new Handler().postDelayed(new Runnable() {
			public void run() {
				SharedPreferences sp = WelcomeActivity.this.getSharedPreferences("loginXml", Context.MODE_PRIVATE);
				Intent intent = null;
				String str = sp.getString("isFrist", "true");
				if ("true".equals(str)) {
					Editor editor = sp.edit();
					editor.putString("isFrist", "false");
					editor.commit();
					intent = new Intent(WelcomeActivity.this,
							ViewPagerActivity.class);
				} else {
					intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					overridePendingTransition(R.anim.zoomin,
							R.anim.zoomout);
				}
				startActivity(intent);
				finish();
			}
		}, 2000);

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
	public void onStop() {
		super.onStop();

	}
}
