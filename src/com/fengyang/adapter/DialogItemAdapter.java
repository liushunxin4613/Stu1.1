package com.fengyang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fengyang.stu.R;

public class DialogItemAdapter extends BaseAdapter {

	private Context context;
	private String[] itemStr;

	private boolean[] selectedItem;

	private boolean singleMode;

	/**
	 * 单选模式使用
	 * 
	 * @param itemStr
	 *            列表显示的文字内容
	 * @param selected
	 *            选中的一项
	 */
	public DialogItemAdapter(Context context, String[] itemStr, int selected) {
		this.context = context;
		this.itemStr = itemStr;
		this.selectedItem = new boolean[itemStr.length];
		this.selectedItem[selected] = true;
		singleMode = true;
	}

	/**
	 * 多选模式使用
	 * 
	 * @param itemStr
	 *            列表显示的文字内容
	 * @param choiceItem
	 *            选中的多项
	 */
	public DialogItemAdapter(Context context, String[] itemStr,
			boolean[] choiceItem) {
		super();
		this.context = context;
		this.itemStr = itemStr;
		this.selectedItem = choiceItem;
		singleMode = false;
	}

	@Override
	public int getCount() {
		return itemStr.length;
	}

	@Override
	public Object getItem(int position) {
		return itemStr[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_dialog_choice, parent, false);

			holder = new ViewHolder();
			holder.content = (TextView) view
					.findViewById(R.id.item_dialog_text_content);
			holder.chackBox = (CheckBox) view
					.findViewById(R.id.item_dialog_checkBox);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.content.setText(itemStr[position]);
		holder.chackBox.setChecked(selectedItem[position]);
		return view;
	}

	public void setSelectedItem(int pos) {
		if (singleMode) {
			for (int i = 0; i < selectedItem.length; i++) {
				selectedItem[i] = false;
			}
			selectedItem[pos] = true;
		} else {
			if (selectedItem[pos])
				selectedItem[pos] = false;
			else
				selectedItem[pos] = true;
		}
		notifyDataSetChanged();
	}

	public boolean[] getSelectedItem() {
		return selectedItem;
	}

	class ViewHolder {
		TextView content;
		CheckBox chackBox;
	}
}