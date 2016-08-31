package com.fengyang.customLib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;

/**
 * 动态刷新和加载数据ListView
 * 
 */
public class DynamicListView extends ListView implements OnScrollListener {

	/**
	 * 监听器 监听控件的刷新或者加载更多事件 所有的条目事件都会有一个偏移量，也就是position应该减1才是你适配器中的条目
	 * 
	 */
	public interface RefreshListener {
		/**
		 * 刷新数据
		 * 
		 * @param dynamicListView
		 * @return true:刷新动作完成，刷新的动画自动消失
		 *         false:刷新为完成，需要在数据加载完成之后去调用控件的doneRefresh ()方法
		 */
		public boolean onRefresh(DynamicListView dynamicListView);

		/**
		 * 取消刷新数据
		 * 
		 * @param dynamicListView
		 */
		public void onCancelRefresh(DynamicListView dynamicListView);

	}

	public interface LoadMoreListener {
		/**
		 * 加载数据
		 * 
		 * @param dynamicListView
		 * @return true:加载更多动作完成，加载更多的动画自动消失
		 *         false:加载更多为完成，需要在数据加载完成之后去调用控件的doneMore()方法
		 */
		public boolean onLoadMore(DynamicListView dynamicListView);

		/**
		 * 取消加载数据
		 * 
		 * @param dynamicListView
		 */
		public void onCancelLoadMore(DynamicListView dynamicListView);
	}

	/**
	 * 状态控件（StatusView，列表头上和底端的）的状态枚举
	 * 
	 * @author Bao
	 * 
	 */
	enum ViewStatus {
		none, normal, willrefresh, refreshing, stoprefreshing
	}

	/**
	 * 状态控件
	 * 
	 */
	class StatusView extends LinearLayout {
		public int height;
		public int width;
		private ProgressBar progressBar = null;
		private TextView textView = null;
		private ViewStatus viewStatus = ViewStatus.none;
		private int normalString = R.string.Refresh_normal;
		private int willrefreshString = R.string.Refresh_willrefresh;
		private int refreshingString = R.string.Refresh_refreshing;
		private int stopfreshString = R.string.Refresh_stop_refresh;

		public StatusView(Context context, AttributeSet attrs) {
			super(context, attrs);
			initThis(context);
		}

		public StatusView(Context context) {
			super(context);
			initThis(context);
		}

		private void initThis(Context context) {
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

			progressBar = new ProgressBar(context);
			progressBar.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
			progressBar.setIndeterminate(false);
			progressBar.setIndeterminateDrawable(getResources().getDrawable(
					R.drawable.refreshing));
			textView = new TextView(context);
			textView.setPadding(5, 20, 0, 20);

			this.addView(progressBar);
			this.addView(textView);

			int w = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			this.measure(w, h);

			height = this.getMeasuredHeight();
			width = this.getMeasuredWidth();

			this.setRefreshStatus(ViewStatus.normal);
		}

		public ViewStatus getRefreshStatus() {
			return viewStatus;
		}

		public void setRefreshStatus(ViewStatus refreshStatus) {
			if (this.viewStatus != refreshStatus) {
				this.viewStatus = refreshStatus;
				if (refreshStatus == ViewStatus.refreshing) {
					this.progressBar.setVisibility(View.VISIBLE);
				} else {
					this.progressBar.setVisibility(View.GONE);
				}
				refreshStatusString();
				this.invalidate();
			}
		}

		private void refreshStatusString() {
			switch (viewStatus) {
			case normal:
				textView.setText(normalString);
				progressBar.setProgress(0);
				break;
			case willrefresh:
				textView.setText(willrefreshString);
				break;
			case refreshing:
				textView.setText(refreshingString);
				break;
			case stoprefreshing:
				textView.setText(stopfreshString);
				break;
			default:
				break;
			}
		}

		/**
		 * 设置状态字符串
		 * 
		 * @param normalString
		 *            平时的字符串
		 * @param willrefreshString
		 *            松开后刷新（或加载）的字符串
		 * @param refreshingString
		 *            正在刷新（或加载）的字符串
		 */
		public void setStatusStrings(int normalString, int willrefreshString,
				int refreshingString) {
			this.normalString = normalString;
			this.willrefreshString = willrefreshString;
			this.refreshingString = refreshingString;
			this.refreshStatusString();
		}
	}

	private StatusView refreshView;
	private StatusView moreView;
	/**
	 * 
	 */
	private boolean isRecord = false;
	/**
	 * 是否停止刷新
	 */
	private boolean isStopRefresh = false;
	/**
	 * 是否停止加载
	 */
	private boolean isStopLoad = false;
	private int downY = -1;
	private final float minTimesToRefresh = 2.5f;
	private final static int ITEM_FLAG_FIRST = 0x0001;
	private final static int ITEM_FLAG_NONE = 0;
	private final static int ITEM_FLAG_LAST = 0x0010;
	/**
	 * ListView滑动位标志位
	 */
	private int itemFlag = ITEM_FLAG_FIRST;

	// 两个监听器
	private RefreshListener onRefreshListener;
	private LoadMoreListener onMoreListener;
	// 滚动到低端的时候是否自动加载更多
	private boolean doMoreWhenBottom = false;

	public DynamicListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initThis(context);
	}

	public DynamicListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initThis(context);
	}

	public DynamicListView(Context context) {
		super(context);
		initThis(context);
	}

	private void initThis(Context context) {
		refreshView = new StatusView(context);
		moreView = new StatusView(context);
		refreshView.setStatusStrings(R.string.Refresh_normal,
				R.string.Refresh_willrefresh, R.string.Refresh_refreshing);
		moreView.setStatusStrings(R.string.Refresh_normal_up,
				R.string.Refresh_willrefresh, R.string.Refresh_refreshing_up);
		this.addHeaderView(refreshView, null, false);
		this.addFooterView(moreView, null, false);
		this.setOnScrollListener(this);
		doneRefresh();
		doneMore();

	}
	
	public View getViewByPosition(int pos) {
	    final int firstListItemPosition = getFirstVisiblePosition();
	    final int lastListItemPosition = firstListItemPosition + getChildCount() - 1;

	    if (pos < firstListItemPosition || pos > lastListItemPosition ) {
	        return getAdapter().getView(pos, null, this);
	    } else {
	        final int childIndex = pos - firstListItemPosition;
	        return getChildAt(childIndex);
	    }
	}

	// 监听器操作
	public void setOnRefreshListener(RefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	public LoadMoreListener getOnMoreListener() {
		return onMoreListener;
	}

	public void setOnMoreListener(LoadMoreListener onMoreListener) {
		this.onMoreListener = onMoreListener;
	}

	public void setDoMoreWhenBottom(boolean doMoreWhenBottom) {
		this.doMoreWhenBottom = doMoreWhenBottom;
	}

	// 设置
	public boolean isDoMoreWhenBottom() {
		return doMoreWhenBottom;
	}

	public RefreshListener getOnRefreshListener() {
		return onRefreshListener;
	}

	@Override
	public void onScroll(AbsListView l, int t, int oldl, int count) {
		if (t == 0)
			itemFlag = itemFlag | ITEM_FLAG_FIRST;
		else if ((t + oldl) == count) {
			itemFlag = itemFlag | ITEM_FLAG_LAST;
			if (doMoreWhenBottom && onMoreListener != null
					&& moreView.getRefreshStatus() != ViewStatus.refreshing) {
				doMore();
			}
		} else {
			itemFlag = itemFlag & ITEM_FLAG_NONE;
			// isRecorded = false;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 在顶部
			if (!isRecord && (itemFlag & ITEM_FLAG_FIRST) == ITEM_FLAG_FIRST
					&& onRefreshListener != null) {
				if (refreshView.getRefreshStatus() == ViewStatus.normal) {
					downY = (int) ev.getY(0);
					isRecord = true;
				} else if (refreshView.getRefreshStatus() == ViewStatus.refreshing) {
					downY = (int) ev.getY(0);
					isRecord = true;
					isStopRefresh = true;
				}
			}
			// 在底部
			if (!isRecord && (itemFlag & ITEM_FLAG_LAST) == ITEM_FLAG_LAST
					&& onMoreListener != null) {
				if (moreView.getRefreshStatus() == ViewStatus.normal) {
					downY = (int) ev.getY(0);
					isRecord = true;
				} else if (moreView.getRefreshStatus() == ViewStatus.refreshing) {
					downY = (int) ev.getY(0);
					isRecord = true;
					isStopLoad = true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isRecord = false;
			if (onRefreshListener != null
					&& refreshView.getRefreshStatus() == ViewStatus.willrefresh) {
				doRefresh();
			} else if (refreshView.getRefreshStatus() == ViewStatus.normal) {
				refreshView.setPadding(0, -1 * refreshView.height, 0, 0);
			} else if (refreshView.getRefreshStatus() == ViewStatus.stoprefreshing) {
				isStopRefresh = false;
				doneRefresh();
				refreshView.setPadding(0, -1 * refreshView.height, 0, 0);
			} else if (refreshView.getRefreshStatus() == ViewStatus.refreshing
					&& isStopRefresh) {
				refreshView.setPadding(0, 10, 0, 10);
			}

			if (onMoreListener != null
					&& moreView.getRefreshStatus() == ViewStatus.willrefresh) {
				doMore();
			} else if (moreView.getRefreshStatus() == ViewStatus.normal) {
				moreView.setPadding(0, 0, 0, -1 * moreView.height);
			} else if (moreView.getRefreshStatus() == ViewStatus.stoprefreshing) {
				isStopLoad = false;
				doneMore();
				moreView.setPadding(0, 0, 0, -1 * moreView.height);
			} else if (refreshView.getRefreshStatus() == ViewStatus.refreshing
					&& isStopLoad) {
				refreshView.setPadding(0, 10, 0, 10);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (!isRecord && (itemFlag & ITEM_FLAG_FIRST) == ITEM_FLAG_FIRST
					&& onRefreshListener != null) {
				if (refreshView.getRefreshStatus() == ViewStatus.normal) {
					downY = (int) ev.getY(0);
					isRecord = true;
				} else if (refreshView.getRefreshStatus() == ViewStatus.refreshing) {
					downY = (int) ev.getY(0);
					isRecord = true;
					isStopRefresh = true;
				}
			} else if (!isRecord
					&& (itemFlag & ITEM_FLAG_LAST) == ITEM_FLAG_LAST
					&& onMoreListener != null) {
				if (moreView.getRefreshStatus() == ViewStatus.normal) {
					downY = (int) ev.getY(0);
					isRecord = true;
				} else if (moreView.getRefreshStatus() == ViewStatus.refreshing) {
					isRecord = true;
					isStopLoad = true;
				}
			} else if (isRecord) {
				int nowY = (int) ev.getY(0);
				int offset = nowY - downY;
				// 计算增加的长度
				double increase = arctan(Math.abs(offset));
				if (isStopRefresh && offset > 0
						&& (itemFlag & ITEM_FLAG_FIRST) == ITEM_FLAG_FIRST
						&& onRefreshListener != null) {// 停止刷新
					// 下拉
					setSelection(0);
					if (offset >= (minTimesToRefresh * refreshView.height)) {
						refreshView.setRefreshStatus(ViewStatus.stoprefreshing);
					} else {
						refreshView.setRefreshStatus(ViewStatus.refreshing);
					}
					refreshView.setPadding(0, (int) increase, 0, 0);
				} else if (isStopLoad && offset < 0
						&& (itemFlag & ITEM_FLAG_LAST) == ITEM_FLAG_LAST
						&& onMoreListener != null) {
					// 上拉
					setSelection(this.getCount());
					if (offset <= -1 * (minTimesToRefresh * moreView.height)) {
						moreView.setRefreshStatus(ViewStatus.stoprefreshing);
					} else {
						moreView.setRefreshStatus(ViewStatus.refreshing);
					}
					moreView.setPadding(0, 0, 0, (int) increase);
				} else { // 刷新
					if (offset > 0
							&& (itemFlag & ITEM_FLAG_FIRST) == ITEM_FLAG_FIRST
							&& onRefreshListener != null) {
						// 下拉
						setSelection(0);
						if (offset >= (minTimesToRefresh * refreshView.height)) {
							refreshView
									.setRefreshStatus(ViewStatus.willrefresh);
						} else {
							refreshView.setRefreshStatus(ViewStatus.normal);
						}

						refreshView.setPadding(0, (int) increase
								- refreshView.height, 0, 0);
					} else if (offset < 0
							&& (itemFlag & ITEM_FLAG_LAST) == ITEM_FLAG_LAST
							&& onMoreListener != null) {
						// 上拉
						setSelection(this.getCount());
						if (offset <= -1
								* (minTimesToRefresh * moreView.height)) {
							moreView.setRefreshStatus(ViewStatus.willrefresh);
						} else {
							moreView.setRefreshStatus(ViewStatus.normal);
						}
						moreView.setPadding(0, 0, 0, (int) increase
								- refreshView.height);
					}
				}

			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 反正弦函数，返回偏移量 最大100dp
	 * 
	 * @param x
	 * @return
	 */
	public double arctan(int x) {
		int len = UIUtils.dip2px(getContext(), 500);
		double a = 2 * len / Math.PI;
		double y = a * Math.atan(x / (len * 1.5));
		return y;
	}

	/**
	 * 开始刷新
	 */
	public void doRefresh() {
		refreshView.setRefreshStatus(ViewStatus.refreshing);
		refreshView.setPadding(0, 10, 0, 10);
		if (onRefreshListener.onRefresh(this))
			doneRefresh();
	}

	/**
	 * 开始加载更多
	 */
	public void doMore() {
		moreView.setRefreshStatus(ViewStatus.refreshing);
		moreView.setPadding(0, 10, 0, 10);
		if (onMoreListener.onLoadMore(this))
			doneMore();
	}

	/**
	 * 刷新完成之后调用，用于取消刷新的动画
	 */
	public void doneRefresh() {
		refreshView.setRefreshStatus(ViewStatus.normal);
		refreshView.setPadding(0, -1 * refreshView.height, 0, 0);
		if (onRefreshListener != null)
			onRefreshListener.onCancelRefresh(this);
	}

	/**
	 * 加载更多完成之后调用，用于取消加载更多的动画
	 */
	public void doneMore() {
		moreView.setRefreshStatus(ViewStatus.normal);
		moreView.setPadding(0, 0, 0, -1 * moreView.height);
		if (onMoreListener != null)
			onMoreListener.onCancelLoadMore(this);
	}

	/**
	 * 获取刷新的状态
	 * 
	 * @return 一般 将要刷新 刷新完成
	 */
	public ViewStatus getRefreshStatus() {
		return refreshView.getRefreshStatus();
	}

	/**
	 * 获取加载更多的状态
	 * 
	 * @return 一般 将要加载 加载完成
	 */
	public ViewStatus getMoreStatus() {
		return moreView.getRefreshStatus();
	}

}
