package com.fengyang.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyang.stu.R;

/** 
* �õ��Զ����progressDialog 
* @param context 
* @param msg 
* @return 
*/  
public class FreshDialog {
	public static Dialog freshDialog(Context context, String msg) {  
		  
        LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.dialog_fresh, null);// �õ�����view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// ���ز���  
        // main.xml�е�ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// ��ʾ����  
        // ���ض���  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.rotate_refresh);  
        // ʹ��ImageView��ʾ����  
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
        tipTextView.setText(msg);// ���ü�����Ϣ  
  
        Dialog loadingDialog = new Dialog(context, R.style.fresh_dialog);// �����Զ�����ʽdialog  
  
        loadingDialog.setCancelable(false);// �������á����ؼ���ȡ��  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.MATCH_PARENT,  
                LinearLayout.LayoutParams.MATCH_PARENT));// ���ò���  
        return loadingDialog;  
  
	}

}