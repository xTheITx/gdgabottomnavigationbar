package nz.co.aucklandgdg.bottomnavigationbar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.aucklandgdg.bottomnavigationbar.ui.fragment.BirdsFragment;
import nz.co.aucklandgdg.bottomnavigationbar.ui.fragment.CatsFragment;
import nz.co.aucklandgdg.bottomnavigationbar.ui.fragment.DogsFragment;
import nz.co.aucklandgdg.bottomnavigationbar.ui.fragment.SectionFragment;
import nz.co.aucklandgdg.bottomnavigationbar.ui.widget.NavigationBottomBar;
import nz.co.aucklandgdg.bottomnavigationbar.ui.widget.NavigationBottomBar.Section;

public class MainActivity extends AppCompatActivity {

	private static final String DOGS_TAG = "dogs_tag";
	private static final String BIRDS_TAG = "birds_tag";
	private static final String CATS_TAG = "cats_tag";

	private SectionFragment mCurrentSectionFragment;

	private DogsFragment mDogsFragment;
	private BirdsFragment mBirdsFragment;
	private CatsFragment mCatsFragment;

	@BindView(R.id.section_container_layout) FrameLayout mSectionContainerLayout;
	@BindView(R.id.section_navigation) NavigationBottomBar mSectionNavigation;

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * lifecycle
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initializeAndRestoreFragments(savedInstanceState);

		// restore the selected section
		mSectionNavigation.setSelectedSection(mCurrentSectionFragment.getSectionType());

		mSectionNavigation.setNavigationSelectedListener(mOnNavigationSelectedListener);
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * ui
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private NavigationBottomBar.OnNavigationSelectedListener mOnNavigationSelectedListener =
			new NavigationBottomBar.OnNavigationSelectedListener() {
				@Override public void onSectionSelected(@Section int section) {
					switch (section) {
						case Section.PUPPIES:
							navigateToPuppiesSection();
							break;
						case Section.CHICKS:
							navigateToChicksSection();
							break;
						case Section.KITTENS:
							navigateToKittensSection();
							break;
					}
				}
			};

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * section fragment navigation
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private void navigateToPuppiesSection() {
		if (mDogsFragment == null) {
			mDogsFragment = DogsFragment.newInstance();
			addSectionFragment(mDogsFragment, DOGS_TAG);

		} else {
			showSectionFragment(mDogsFragment);
		}
	}

	private void navigateToChicksSection() {
		if (mBirdsFragment == null) {
			mBirdsFragment = BirdsFragment.newInstance();
			addSectionFragment(mBirdsFragment, BIRDS_TAG);

		} else {
			showSectionFragment(mBirdsFragment);
		}
	}

	private void navigateToKittensSection() {
		if (mCatsFragment == null) {
			mCatsFragment = CatsFragment.newInstance();
			addSectionFragment(mCatsFragment, CATS_TAG);

		} else {
			showSectionFragment(mCatsFragment);
		}
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * section fragment general
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	private void initializeAndRestoreFragments(Bundle savedInstanceState) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		// attempt to restore the fragments
		if (savedInstanceState != null) {

			// restore all section fragments
			mDogsFragment = (DogsFragment) fragmentManager.findFragmentByTag(DOGS_TAG);
			mBirdsFragment = (BirdsFragment) fragmentManager.findFragmentByTag(BIRDS_TAG);
			mCatsFragment = (CatsFragment) fragmentManager.findFragmentByTag(CATS_TAG);

			// restore current section Fragment
			if (isCurrentFragment(mDogsFragment)) {
				mCurrentSectionFragment = mDogsFragment;

			} else if (isCurrentFragment(mBirdsFragment)) {
				mCurrentSectionFragment = mBirdsFragment;

			} else if (isCurrentFragment(mCatsFragment)) {
				mCurrentSectionFragment = mCatsFragment;

			} else {
				throw new IllegalStateException("Unable to restore current section Fragment");
			}
		}

		// if no Fragments were restored, then display the dogs section
		if (fragmentManager.findFragmentById(R.id.section_container_layout) == null) {
			navigateToPuppiesSection();
		}
	}

	private boolean isCurrentFragment(@Nullable SectionFragment sectionContainerFragment) {
		return sectionContainerFragment != null && !sectionContainerFragment.isHidden();
	}

	private void addSectionFragment(@NonNull SectionFragment sectionContainerFragment, @NonNull String tag) {
		mCurrentSectionFragment = sectionContainerFragment;
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideSectionIfNotCurrent(mDogsFragment, mCurrentSectionFragment, transaction);
		hideSectionIfNotCurrent(mBirdsFragment, mCurrentSectionFragment, transaction);
		hideSectionIfNotCurrent(mCatsFragment, mCurrentSectionFragment, transaction);

		transaction.add(R.id.section_container_layout, sectionContainerFragment, tag);
		transaction.commitNow();
	}
	private void showSectionFragment(@NonNull SectionFragment sectionContainerFragment) {
		mCurrentSectionFragment = sectionContainerFragment;
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideSectionIfNotCurrent(mDogsFragment, mCurrentSectionFragment, transaction);
		hideSectionIfNotCurrent(mBirdsFragment, mCurrentSectionFragment, transaction);
		hideSectionIfNotCurrent(mCatsFragment, mCurrentSectionFragment, transaction);

		// show the fragment we're interested in
		transaction.show(sectionContainerFragment);
		transaction.commitNow();
	}

	private void hideSectionIfNotCurrent(
			@Nullable SectionFragment fragment,
			@NonNull SectionFragment currentSectionFragment,
			@NonNull FragmentTransaction transaction) {
		if (fragment != null && fragment != currentSectionFragment) transaction.hide(fragment);
	}

}
