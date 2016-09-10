package nz.co.aucklandgdg.bottomnavigationbar.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.aucklandgdg.bottomnavigationbar.R;
import nz.co.aucklandgdg.bottomnavigationbar.ui.adaptor.ImageCardViewAdaptor;

import static nz.co.aucklandgdg.bottomnavigationbar.ui.widget.NavigationBottomBar.Section;

public class BirdsFragment extends SectionFragment {

	private ImageCardViewAdaptor mAdapter;

	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * new instance
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	public static BirdsFragment newInstance() {
		return new BirdsFragment();
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * lifecycle
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Nullable @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_birds, container, false);
		ButterKnife.bind(this, view);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		List<ImageCardViewAdaptor.Data> data = new ArrayList<>();
		data.add(new ImageCardViewAdaptor.Data("Cray cray", "http://i.huffpost.com/gen/1143173/images/o-POTOO-FUNNY-BIRD-facebook.jpg"));
		data.add(new ImageCardViewAdaptor.Data("Derp", "http://img.designswan.com/2009/Animal/funnyBird/10.jpg"));

		// specify an adapter (see also next example)
		mAdapter = new ImageCardViewAdaptor(getActivity(), data.toArray(new ImageCardViewAdaptor.Data[data.size()]));
		mRecyclerView.setAdapter(mAdapter);

		return view;
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * base class overrides
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Override public @Section int getSectionType() {
		return Section.CHICKS;
	}
}
