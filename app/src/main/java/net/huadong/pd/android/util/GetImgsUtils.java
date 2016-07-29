package net.huadong.pd.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import net.huadong.pd.android.R;
import net.huadong.pd.android.constant.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class GetImgsUtils {
	//选择图片标示code
	public static final int GET_SELECT_IMG_RESULT_CODE=1111;
	//拍照标示code
	public static final int GET_TAKE_IMG_RESULT_CODE=2222;
     private Context mcontext;
	private String localTempImgFileName;
//	private Bitmap bitmap = null ;
	public GetImgsUtils(Context mcontext){
		this.mcontext=mcontext;

	}
	// 上传图片
	public void getImgs() {
		localTempImgFileName = System.currentTimeMillis() + ".jpg";
		View view = LayoutInflater.from(mcontext).inflate(
				R.layout.pop_take_phone, null);
		final Dialog dialog = new AlertDialog.Builder(mcontext).create();
		// LayoutParams params= new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);
		// dialog.setCancelable(true);
		dialog.show();
		dialog.setContentView(view);
		view.findViewById(R.id.select_photo).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						((Activity)mcontext).startActivityForResult(intent, GET_SELECT_IMG_RESULT_CODE);
						dialog.cancel();
					}
				});
		view.findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取SD卡的状态
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					try {
						File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constants.IMGS_FILES);
						if (!dir.exists()) dir.mkdirs();
						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

						File f = new File(dir, localTempImgFileName);//localTempImgDir和localTempImageFileName是自己定义的名字
						Uri u = Uri.fromFile(f);
						intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
						// 下面这句指定调用相机拍照后的照片存储的路径
						intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
						((Activity)mcontext).startActivityForResult(intent, GET_TAKE_IMG_RESULT_CODE);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					((Activity)mcontext).startActivityForResult(intent, GET_TAKE_IMG_RESULT_CODE);

				}
				dialog.cancel();
			}
		});
		view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}
	//通过intent获取图片Base64转码
	public String getImgBase64ByIntent(int requestCode, int resultCode, Intent data){
		Bitmap mbitmap=null;
		if(resultCode==((Activity)mcontext).RESULT_OK){
			switch (requestCode) {
				case GET_TAKE_IMG_RESULT_CODE:
					// 解成bitmap,方便裁剪
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						String srcPath=Environment.getExternalStorageDirectory()
								+"/"+Constants.IMGS_FILES+"/"+localTempImgFileName;
						mbitmap=ZoomBitmapUtils.compressImageFromFile(srcPath,1024,1024,150);
					}else{
						mbitmap = (Bitmap) data.getExtras().get("data");
					}

					break;
				case GET_SELECT_IMG_RESULT_CODE:
					if (data != null) {
						String srcPath=getAbsoluteImagePath(data
								.getData());
						mbitmap=ZoomBitmapUtils.compressImageFromFile(srcPath,1024,1024,150);

					}
					break;
				default:
					break;
			}
		}
		String strBitmap="";
		if(mbitmap!=null){
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			mbitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			strBitmap = new String(Base64Coder.encodeLines(b));
		}
		return strBitmap;
	}
	/*	通过bitmap获取图片Base64转码
*maxsize 压缩尺寸
*
*
	 */
	public String getImgBase64ByBitmap(Bitmap bitmap,int maxsize){
		String strBitmap="";
		if(bitmap!=null){
			bitmap=ZoomBitmapUtils.compressBmpFromBmp(bitmap,maxsize);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			strBitmap = new String(Base64Coder.encodeLines(b));
		}
		return strBitmap;
	}
	//通过bitmap获取图片Base64转码（bitmap已压缩）
	public String getImgBase64ByBitmap(Bitmap bitmap){
		String strBitmap="";
		if(bitmap!=null){
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			strBitmap = new String(Base64Coder.encodeLines(b));
		}
		return strBitmap;
	}
	//通过intent获取图片bitmap
	public Bitmap getImgBitmapByIntent(int requestCode, int resultCode, Intent data,int maxsize){
		Bitmap mbitmap=null;
		if(resultCode==((Activity)mcontext).RESULT_OK){
			switch (requestCode) {
				case GET_TAKE_IMG_RESULT_CODE:
					// 解成bitmap,方便裁剪
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						String srcPath=Environment.getExternalStorageDirectory()
								+"/"+Constants.IMGS_FILES+"/"+localTempImgFileName;
						mbitmap=ZoomBitmapUtils.compressImageFromFile(srcPath,1024,1024,150);


					}else{
						mbitmap = (Bitmap) data.getExtras().get("data");
					}
					if (mbitmap != null ) {
						mbitmap=ZoomBitmapUtils.compressBmpFromBmp(mbitmap,maxsize);
					}
					break;
				case GET_SELECT_IMG_RESULT_CODE:
					if (data != null) {
						String srcPath=getAbsoluteImagePath(data
								.getData());
						mbitmap=ZoomBitmapUtils.compressImageFromFile(srcPath,1024,1024,150);
						if (mbitmap != null ) {
							mbitmap=ZoomBitmapUtils.compressBmpFromBmp(mbitmap,maxsize);
						}
					}
					break;
				default:
					break;
			}
		}
		return mbitmap;
	}
	// 取到绝对路径
	protected String getAbsoluteImagePath(Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = ((Activity)mcontext).managedQuery(uri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
