package net.huadong.pd.android.activity.base;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import net.huadong.pd.android.R;

public class LoginActivity extends Activity {
	private EditText phoneNum;
	private EditText passWord;
	private TextView registerTextView, pwdTextView;
	private Button loginBt;
	private ScrollView mScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		registerTextView = (TextView) findViewById(R.id.user_register);
		pwdTextView = (TextView) findViewById(R.id.user_forget_pwd);
		loginBt = (Button) findViewById(R.id.user_login_button);
		phoneNum = (EditText) findViewById(R.id.user_phone_edit);
		passWord = (EditText) findViewById(R.id.user_pwd_edit);
		mScrollView=(ScrollView)findViewById(R.id.test);
		final View decorview=getWindow().getDecorView();
		decorview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Rect rect=new Rect();
				decorview.getWindowVisibleDisplayFrame(rect);
				int mHeight=decorview.getRootView().getHeight();
				int diffHeight=mHeight-rect.bottom;
				LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)mScrollView.getLayoutParams();
				params.setMargins(0,0,0,diffHeight);
				mScrollView.requestLayout();
			}
		});
		registerTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		loginBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone = phoneNum.getText().toString();
				if (phone.equals("")) {
					Toast.makeText(LoginActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				String pwd = passWord.getText().toString();
				if (pwd.equals("")) {
					Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();

					return;
				}

				HttpUtils http = new HttpUtils();
				http.send(HttpRequest.HttpMethod.GET,
						"http://192.3.19.133:8080?ac=1002&mobile="+phone+"&password="+pwd,
						new RequestCallBack<String>() {
							@Override
							public void onLoading(long total, long current, boolean isUploading) {
								//testTextView.setText(current + "/" + total);
							}

							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								//textView.setText(responseInfo.result);
								Log.d("jsonTag", responseInfo.result);
							}

							@Override
							public void onStart() {
							}

							@Override
							public void onFailure(HttpException error, String msg) {
							}



						});

				/*SharedPreferences spread = LoginActivity.this.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor=spread.edit();
				editor.putInt("isLogin",1);
				editor.commit();
				Intent intent=new Intent();
				String go_to=LoginActivity.this.getIntent().getStringExtra("goto");
				if("3".equals(go_to)){
					intent.putExtra("index", 3);
					intent.setClass(LoginActivity.this,
							TabHostActivity.class);
				}else if("2".equals(go_to)){
					intent.putExtra("index", 1);
					intent.setClass(LoginActivity.this,
							TabHostActivity.class);
				}else if("1".equals(go_to)){
					intent.putExtra("index", 1);
					intent.setClass(LoginActivity.this,
							TabHostActivity.class);
				}else if("0".equals(go_to)){
					intent.putExtra("index", 0);
					intent.setClass(LoginActivity.this,
							TabHostActivity.class);
				}else {
					intent=new Intent(LoginActivity.this,MainActivity.class);
				}
				startActivity(intent);*/
			}

		});
		//透明状态栏
		RelativeLayout login_phonebar=(RelativeLayout)findViewById(R.id.login_phonebar);
		if(android.os.Build.VERSION.SDK_INT >= 19) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else {
			login_phonebar.setVisibility(View.GONE);
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
}
