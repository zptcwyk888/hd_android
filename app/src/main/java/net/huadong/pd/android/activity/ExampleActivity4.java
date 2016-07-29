package net.huadong.pd.android.activity;


import net.huadong.pd.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class ExampleActivity4 extends Activity implements OnClickListener {
	private PullToRefreshScrollView mPullToRefreshScrollView;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					mPullToRefreshScrollView.onRefreshComplete();
					break;
				case 2:
					mPullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在加载数据...");
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
		setContentView(R.layout.example_layout4);
		init();
		mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// 显示上次更新的时间
				String label = DateUtils. formatDateTime(
						getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL) ;
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel (label) ;
				Toast.makeText(ExampleActivity4.this,"下拉刷新数据！",Toast.LENGTH_LONG).show();
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		});
		//透明状态栏
		RelativeLayout me_phonebar=(RelativeLayout)findViewById(R.id.me_phonebar);
		if(android.os.Build.VERSION.SDK_INT >= 19) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else {
			me_phonebar.setVisibility(View.GONE);
		}

	}
	public void init(){
		findViewById(R.id.setting).setOnClickListener(this);
		mPullToRefreshScrollView=(PullToRefreshScrollView)findViewById(R.id.mpullToRefreshScrollView);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.setting:
				Toast.makeText(ExampleActivity4.this,"您点击了设置！",Toast.LENGTH_LONG).show();
				break;
			default:
				break;
		}

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
	public void onRestart(){
		super.onRestart();
	}

}
