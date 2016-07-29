package net.huadong.pd.android.activity;

import net.huadong.pd.android.R;
import net.huadong.pd.android.util.GetImgsUtils;
import net.huadong.pd.android.util.ZoomBitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.mobstat.StatService;

public class ExampleActivity2 extends Activity implements OnClickListener{
	private GetImgsUtils getImgsUtils;
	private ImageView get_pic_test;
	private TextView imgStr_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.example_layout2);
		//注册id及监听事件
		init();
		//透明状态栏
		RelativeLayout order_list_phonebar=(RelativeLayout)findViewById(R.id.order_list_phonebar);
		if(android.os.Build.VERSION.SDK_INT >= 19) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else {
			order_list_phonebar.setVisibility(View.GONE);
		}
	}
	public void init(){
		findViewById(R.id.example_layout2_back).setOnClickListener(this);
		get_pic_test=(ImageView)findViewById(R.id.get_pic_test);
		get_pic_test.setOnClickListener(this);
		imgStr_text=(TextView)findViewById(R.id.imgStr_text);
		getImgsUtils=new GetImgsUtils(ExampleActivity2.this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.order_back:
				finish();
				break;
		case R.id.get_pic_test:
			getImgsUtils.getImgs();
				break;
		default:
			break;
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap bitmap=getImgsUtils.getImgBitmapByIntent(requestCode, resultCode, data, 150);
		//图片旋转90度
		bitmap= ZoomBitmapUtils.rotateBitmap(bitmap,90);
        get_pic_test.setImageBitmap(bitmap);
		//将bitmap转码为base64格式的字符串
		String strBitmap=getImgsUtils.getImgBase64ByBitmap(bitmap);
		imgStr_text.setText(strBitmap);
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
