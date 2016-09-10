package nz.co.aucklandgdg.bottomnavigationbar.ui.fragment;

import android.support.v4.app.Fragment;

import nz.co.aucklandgdg.bottomnavigationbar.ui.widget.NavigationBottomBar;

/**
 * Base class for section Fragments which are displayed in the home screen
 */
public abstract class SectionFragment extends Fragment {

	public abstract @NavigationBottomBar.Section int getSectionType();
}
