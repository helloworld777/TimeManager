package com.tm.lyw.timemanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tm.lyw.timemanager.adapter.base.ViewHolder;

import java.util.List;

public abstract class LuAdapter<T> extends BaseAdapter{
	private Context context;
	private List<T> datas;
	private final int mItemLayoutId;
	public LuAdapter(Context context,List<T> datas,int mItemLayoutId){
		this.context=context;
		this.datas=datas;
		this.mItemLayoutId=mItemLayoutId;
	}
	public void replaceAll(List<T> elem) {
		datas.clear();
		datas.addAll(elem);
		notifyDataSetChanged();
	}
	public void add(T elem) {
		datas.add(elem);
		notifyDataSetChanged();
	}

	public void addAll(List<T> elem) {
		datas.addAll(elem);
		notifyDataSetChanged();
	}

	public void set(T oldElem, T newElem) {
		set(datas.indexOf(oldElem), newElem);
	}

	public void set(int index, T elem) {
		datas.set(index, elem);
		notifyDataSetChanged();
	}

	public void remove(T elem) {
		datas.remove(elem);
		notifyDataSetChanged();
	}

	public void remove(int index) {
		datas.remove(index);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		
		return datas.size();
	}
	public boolean contains(T elem) {
		return datas.contains(elem);
	}

	/** Clear data list */
	public void clear() {
		datas.clear();
		notifyDataSetChanged();
	}
	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder=getViewHolder(convertView, parent, position);
		convert(viewHolder,getItem(position));
		convert(viewHolder,position);
		return viewHolder.getConvertView();
	}
	public  void convert(ViewHolder helper, T item){};
	public abstract void convert(ViewHolder helper, int position);
		
//	};
	protected ViewHolder getViewHolder(View convertView, ViewGroup parent, int position){
		return ViewHolder.createViewHolder(context, parent, mItemLayoutId);
	}
}
