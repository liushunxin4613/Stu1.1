package com.fengyang.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fengyang.adapter.DialogItemAdapter;
import com.fengyang.stu.R;

/**
 * 多选选择对话框
 * 
 * @author HeJie
 *
 */
public class MultiChoiceDialog extends BaseDialog {

	private ListView listview;

	private DialogItemAdapter adapter;
	private boolean[] selectedItem;
	private int choosedCount = 0;
	private String[] itemStr;

	private static final String KEY_CHOICE_ITEM = "choiceItem";
	private static final String KEY_ITEM = "itemStr";

	public MultiChoiceDialog() {
	}

	public MultiChoiceDialog(Context context, int itemsId, boolean[] selectedItem) {
		super(context);
		this.itemStr = context.getResources().getStringArray(itemsId);
		this.selectedItem = selectedItem;
		this.theme = R.style.StuDialogTheme_Center;
		for (boolean b : selectedItem) {
			if (b)
				choosedCount++;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			itemStr = savedInstanceState.getStringArray(KEY_CHOICE_ITEM);
			selectedItem = savedInstanceState.getBooleanArray(KEY_ITEM);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArray(KEY_CHOICE_ITEM, itemStr);
		outState.putBooleanArray(KEY_ITEM, selectedItem);
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_multi_choice,
				container, false);
		listview = (ListView) rootView.findViewById(R.id.dialog_listview);
		titleView = (TextView) rootView.findViewById(R.id.dialog_title);
		positiveBtn = (Button) rootView
				.findViewById(R.id.dialog_positive_button);
		nevigativeBtn = (Button) rootView
				.findViewById(R.id.dialog_nevigative_button);
		if (choosedCount == 0){
			positiveBtn.setEnabled(false);
		}
		
		if (title != null)
			titleView.setText(title);
		positiveBtn.setOnClickListener(this);
		nevigativeBtn.setOnClickListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.setSelectedItem(position);
				if (selectedItem[position]) {
					if (--choosedCount == 0) {
						positiveBtn.setEnabled(false);
					}
				} else {
					if (choosedCount++ == 0) {
						positiveBtn.setEnabled(true);
					}
				}
			}
		});
		adapter = new DialogItemAdapter(getActivity(), itemStr, selectedItem);
		listview.setAdapter(adapter);
		return rootView;
	}

	public boolean[] getChoiceItem() {
		return adapter.getSelectedItem();
	}

}
