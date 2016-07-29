package net.huadong.pd.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import net.huadong.pd.android.R;
import net.huadong.pd.android.eneity.examEneity;

import java.util.ArrayList;

public class Example1Adapter extends BaseAdapter {
	private ArrayList<examEneity> mlist;
	private Context mcontext;
	private LayoutInflater inflater;
	public Example1Adapter(ArrayList<examEneity> mlist, Context mcontext) {
		super();
		this.mlist = mlist;
		this.mcontext = mcontext;
		this.inflater = LayoutInflater.from(mcontext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_example1, null);
			viewHolder = new ViewHolder();
			viewHolder.text=(TextView)convertView.findViewById(R.id.text);
			viewHolder.img=(ImageView)convertView.findViewById(R.id.img);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		examEneity p=mlist.get(position);
		viewHolder.text.setText(p.getText());
		if(p.getImgUrl()!=null&&!"".equals(p.getImgUrl())){
			BitmapUtils bitmaputils=new BitmapUtils(mcontext);
			bitmaputils.display(viewHolder.img,p.getImgUrl());
		}
		return convertView;
	}
	public static class ViewHolder {
		TextView text;
		ImageView img;
	}
}
