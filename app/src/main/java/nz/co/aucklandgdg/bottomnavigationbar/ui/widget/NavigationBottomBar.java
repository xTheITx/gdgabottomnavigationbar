package nz.co.aucklandgdg.bottomnavigationbar.ui.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.aucklandgdg.bottomnavigationbar.R;

/**
 * View widget which displays the bottom navigation on the home screen
 */
public class NavigationBottomBar extends TabLayout {

	public interface OnNavigationSelectedListener {
		void onSectionSelected(@Section int section);
	}

	@IntDef({Section.PUPPIES, Section.CHICKS, Section.KITTENS})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Section {
		int PUPPIES = 0;
		int CHICKS = 1;
		int KITTENS = 2;
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * internal fields
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private OnNavigationSelectedListener mNavigationSelectedListener;

	private TabHolder mTodayTab;
	private TabHolder mJobsTab;
	private TabHolder mClientsTab;

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * constructors
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	public NavigationBottomBar(Context context) {
		this(context, null);
	}

	public NavigationBottomBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NavigationBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		addOnTabSelectedListener(mOnTabSelectedListener);

		mTodayTab = new TabHolder(context, R.string.dogs, R.drawable.ic_dogs);
		mJobsTab = new TabHolder(context, R.string.birds, R.drawable.ic_birds);
		mClientsTab = new TabHolder(context, R.string.cats, R.drawable.ic_cats);

		addTab(mTodayTab.getTab(), true);
		addTab(mJobsTab.getTab());
		addTab(mClientsTab.getTab());

		updateView();
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * mutators
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	public void setNavigationSelectedListener(
			OnNavigationSelectedListener navigationSelectedListener) {
		mNavigationSelectedListener = navigationSelectedListener;
	}

	public void setSelectedSection(@Section int section) {
		switch (section) {
			case Section.PUPPIES:
				mTodayTab.select();
				break;
			case Section.CHICKS:
				mJobsTab.select();
				break;
			case Section.KITTENS:
				mClientsTab.select();
				break;
		}
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * general
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private void updateView() {
		mTodayTab.updateView();
		mJobsTab.updateView();
		mClientsTab.updateView();
	}

	private @Section int getSectionForTabPosition(int position) {
		switch (position) {
			case Section.PUPPIES:
				return Section.PUPPIES;
			case Section.CHICKS:
				return Section.CHICKS;
			case Section.KITTENS:
				return Section.KITTENS;
			default:
				throw new IllegalStateException("No section for position: " + position);
		}
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * tab selected listener
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private OnTabSelectedListener mOnTabSelectedListener = new OnTabSelectedListener() {
		@Override public void onTabSelected(Tab tab) {
			if (mNavigationSelectedListener != null) {
				mNavigationSelectedListener.onSectionSelected(getSectionForTabPosition(tab.getPosition()));
			}
			updateView();
		}

		@Override public void onTabUnselected(Tab tab) {
			updateView();
		}

		@Override public void onTabReselected(Tab tab) { }
	};

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * tab holder
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	protected class TabHolder {

		@NonNull private Tab mTab;
		@NonNull private Context mContext;

		@BindView(R.id.icon) ImageView mIconImageView;
		@BindView(R.id.title) TextView mTitleTextView;

		public TabHolder(@NonNull Context context, @StringRes int titleRes, @DrawableRes int iconRes) {
			mContext = context;
			mTab = newTab();
			mTab.setCustomView(R.layout.tab_section_navigation);
			ButterKnife.bind(this, mTab.getCustomView());

			mTitleTextView.setText(titleRes);
			mIconImageView.setImageDrawable(AppCompatDrawableManager.get().getDrawable(mContext, iconRes));
		}

		@NonNull public Tab getTab() {
			return mTab;
		}

		public void select() {
			mTab.select();
		}

		public void updateView() {
			int colorValue = getColorValue();
			DrawableCompat.setTint(mIconImageView.getDrawable(), colorValue);
			mTitleTextView.setTextColor(colorValue);
		}

		private int getColorValue() {
			int colorRes = mTab.isSelected() ? R.color.tab_selected : R.color.tab_unselected;
			return ContextCompat.getColor(mContext, colorRes);
		}
	}
}
