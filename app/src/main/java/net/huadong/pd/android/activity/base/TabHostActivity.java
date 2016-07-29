package net.huadong.pd.android.activity.base;


import android.os.Bundle;
import android.app.ActivityGroup;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import net.huadong.pd.android.R;
import net.huadong.pd.android.activity.ExampleActivity4;
import net.huadong.pd.android.activity.ExampleActivity1;
import net.huadong.pd.android.activity.ExampleActivity3;
import net.huadong.pd.android.activity.ExampleActivity2;

public class TabHostActivity extends ActivityGroup {

	private TabHost tabHost;//声明一个TabHost对象
	
	     //资源文件
	     private Class activitys[]={ExampleActivity1.class,ExampleActivity2.class,ExampleActivity3.class,ExampleActivity4.class};//跳转的Activity
	     private String title[]={"接活","订单","收钱","我"};//设置菜单的标题
	     private int image[]={R.drawable.get_work,R.drawable.order,R.drawable.get_money,R.drawable.me};//设置菜单
	     
	     @Override
	     protected void onCreate(Bundle savedInstanceState) {
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.activity_main);
	         initTabView();//初始化tab标签
	         Intent intent=getIntent();
	         int s=intent.getIntExtra("index",9);
	         if(s!=9){
	        	 tabHost.setCurrentTab(s);
	         }
			 //透明状态栏
			 getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	     }
	 
	     private void initTabView() {
	         //实例化tabhost
	         this.tabHost=(TabHost) findViewById(R.id.mytabhost);
	       //由于继承了ActivityGroup，所以需要在setup方法里加入此参数，若继承TabActivity则可省略
	         tabHost.setup(this.getLocalActivityManager());      
	         //创建标签
	         for(int i=0;i<activitys.length;i++){
	             //实例化一个view作为tab标签的布局
	             View view=View.inflate(this, R.layout.tab_layout, null);
	             
	             //设置imageview
	             ImageView imageView=(ImageView) view.findViewById(R.id.image);
	             imageView.setImageDrawable(getResources().getDrawable(image[i]));
	             //设置textview
	             TextView textView=(TextView) view.findViewById(R.id.title);
	             textView.setText(title[i]);
	             //设置跳转activity
	             Intent intent=new Intent();
				 intent.setClass(this,activitys[i]);
	             //载入view对象并设置跳转的activity
	             TabSpec spec=tabHost.newTabSpec(title[i]).setIndicator(view).setContent(intent);
	             
	            //添加到选项卡
	             tabHost.addTab(spec);
	         }
	         
	     }

}
