package net.huadong.pd.android.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.baidu.mobstat.StatService;
import net.huadong.pd.android.R;
import net.huadong.pd.android.zxing.CaptureActivity;
import net.huadong.pd.android.zxing.CreateQRImage;

public class ExampleActivity3 extends Activity implements OnClickListener {
    private ImageView zxing_img;
    private EditText zxing_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.example_layout3);
        init();

        //透明状态栏
        RelativeLayout getmoney_phonebar = (RelativeLayout) findViewById(R.id.getmoney_phonebar);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getmoney_phonebar.setVisibility(View.GONE);
        }
    }
    public void init(){
        findViewById(R.id.order_back).setOnClickListener(this);
        findViewById(R.id.scanning).setOnClickListener(this);
        findViewById(R.id.get_qrcode).setOnClickListener(this);
        zxing_img=(ImageView)findViewById(R.id.zxing_img);
        zxing_text=(EditText)findViewById(R.id.qrcode_text);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.order_back:
                finish();
                break;
            case R.id.scanning:
                Intent intent =new Intent(ExampleActivity3.this, CaptureActivity.class);
                startActivity(intent);
                break;
            case R.id.get_qrcode:
               String strurl=zxing_text.getText().toString().trim();
                if("".equals(strurl)){
                    Toast.makeText(ExampleActivity3.this,"请输入您好生成二维码的地址！",Toast.LENGTH_SHORT).show();
                }else{
                    CreateQRImage creat=new CreateQRImage();
                    creat.createQRImage(strurl,zxing_img);
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
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
