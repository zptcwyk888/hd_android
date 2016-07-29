package net.huadong.pd.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ZoomBitmapUtils {
    //将图片压缩到指定宽高
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		bgimage.recycle();
		return bitmap;
	}

	//将图片保存到本地时进行压缩, 即将图片从Bitmap形式变为File形式时进行压缩,
	//特点是:  File形式的图片确实被压缩了, 但是当你重新读取压缩后的file为 Bitmap是,它占用的内存并没有改变
	public static void compressBmpToFile(Bitmap bmp,File file,int maxSize){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > maxSize) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
			bmp.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	/*
	*srcPath 图片地址
	* maxheigh 图片高度峰值（超过峰值将压缩图片）
	* maxwidth 图片宽度峰值（超过峰值将压缩图片）
	* maxSize 图片大小峰值（超过峰值将压缩图片）
	 */
	public static Bitmap compressImageFromFile(String srcPath,float maxheigh,float maxwidth,int maxSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcPath, options);
		Bitmap bitmap;
		if(options.outHeight>(2*maxheigh)||options.outWidth>(2*maxwidth)){
			//重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
			options.inJustDecodeBounds = false;
			options.inSampleSize = 4;//width，hight设为原来的四分一，总体缩小为原来的十六分之一
			bitmap=BitmapFactory.decodeFile(srcPath,options);
		}else if(options.outHeight>maxheigh||options.outWidth>maxwidth){
			//重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
			options.inJustDecodeBounds = false;
			options.inSampleSize = 2;//width，hight设为原来的二分一
			bitmap=BitmapFactory.decodeFile(srcPath,options);
		}else{
			bitmap = BitmapFactory.decodeFile(srcPath);
		}
      return compressBmpFromBmp(bitmap, maxSize);
	}
	//maxSize的大小为图片长*宽*4,ARGB_8888一像素4字节
	public static Bitmap compressBmpFromBmp(Bitmap image,int maxSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		while (baos.toByteArray().length / 1024 > maxSize) {
			baos.reset();
			options -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		image.recycle();
		return bitmap;
	}
    //读取图片旋转角度
	public static int readPictureDegree(String picPath){
        int degress=0;
		try{
			ExifInterface mExifInterface=new ExifInterface(picPath);
			int orientation=mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
			switch (orientation){
				case ExifInterface.ORIENTATION_ROTATE_90:
					degress=90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degress=180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degress=270;
					break;
				default:
					break;
			}
		}catch (IOException e){
			e.printStackTrace();

		}
		return degress;
	}
    //旋转图片   degress-所要旋转的度数
    public static Bitmap rotateBitmap(Bitmap bitmap,int degress){
		if(bitmap!=null){
			Matrix m=new Matrix();
			m.postRotate(degress);
			bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,true);
		}
		return bitmap;
	}

	//根据图片大小切割图片
	public static Bitmap dealBitmap(Bitmap bitmap,double maxSize){
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		//将字节换成KB
		double mid = b.length/1024;
		//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩 ARGB_8888一像素4字节
		if (mid > maxSize*4) {
			//获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitmap =  zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i),
					bitmap.getHeight() / Math.sqrt(i));
		}
		return bitmap;
	}
}
