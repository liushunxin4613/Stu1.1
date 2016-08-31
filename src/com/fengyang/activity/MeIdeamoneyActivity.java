package com.fengyang.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.fengyang.fragment.MeIdealmoneyFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class MeIdeamoneyActivity extends ImmersionActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me_idealmoney);

		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.Fm_me_idealmoney,
							MeIdealmoneyFragment.getInstance()).commit();
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
}
