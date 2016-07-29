package net.huadong.pd.android.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{
	private ArrayList<View> alist;
	
	public ViewPagerAdapter(ArrayList<View> alist) {
		super();
		this.alist = alist;
	}
	public int getCount() {
		return this.alist==null?0:this.alist.size();
	}
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(this.alist.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {		
		container.addView(this.alist.get(position));
		return this.alist.get(position);
	}
    
}
