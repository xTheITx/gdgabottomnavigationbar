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

public class DogsFragment extends SectionFragment {

	private ImageCardViewAdaptor mAdapter;

	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * new instance
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	public static DogsFragment newInstance() {
		return new DogsFragment();
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * lifecycle
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Nullable @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_dogs, container, false);
		ButterKnife.bind(this, view);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		List<ImageCardViewAdaptor.Data> data = new ArrayList<>();
		data.add(new ImageCardViewAdaptor.Data("Such safety! Much wow!", "https://i.redd.it/32e3ean7aikx.jpg"));
		data.add(new ImageCardViewAdaptor.Data("Om nom nom?",
				"https://a.dilcdn.com/bl/wp-content/uploads/sites/8/2013/09/puppy5-624x398.jpg"));
		data.add(new ImageCardViewAdaptor.Data("Obvious bun",
				"http://cdn0.whiskeyriff.com/wp-content/uploads/513d76a1cc6a4b475365af369ed0dc1d.jpg"));
		data.add(new ImageCardViewAdaptor.Data("Bluey", "https://i.redd.it/dx53uhlo3lkx.jpg"));

		// specify an adapter (see also next example)
		mAdapter = new ImageCardViewAdaptor(getActivity(), data.toArray(new ImageCardViewAdaptor.Data[data.size()]));
		mRecyclerView.setAdapter(mAdapter);

		return view;
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 * base class overrides
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Override public @Section int getSectionType() {
		return Section.PUPPIES;
	}
}
