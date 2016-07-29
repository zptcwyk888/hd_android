package net.huadong.pd.android.util;

import android.content.Context;

import java.io.File;

//FileUtils获得文件缓存目录
public class FileUtils {
	/**
	 * 缓存文件目录
	 */
	private File mCacheDir;

	public FileUtils(Context context, String cacheDir) {
		if (android.os.Environment.getExternalStorageState().
				equals(android.os.Environment.MEDIA_MOUNTED)) {
			mCacheDir = new File(cacheDir);
		} else {
			mCacheDir = context.getCacheDir();// 获取系统内置的缓存存储路径
		}
		if (!mCacheDir.exists()) {
			mCacheDir.mkdirs();
		}
	}

	public  String getCacheDir() {
		return mCacheDir.getAbsolutePath();
	}
}