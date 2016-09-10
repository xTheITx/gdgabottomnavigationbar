package nz.co.aucklandgdg.bottomnavigationbar.ui.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import nz.co.aucklandgdg.bottomnavigationbar.R;

public class ImageCardViewAdaptor extends RecyclerView.Adapter<ImageCardViewAdaptor.ViewHolder> {

	public static class Data {
		private String mTitle;
		private String mUrl;

		public Data(String title, String url) {
			mTitle = title;
			mUrl = url;
		}

		public String getTitle() {
			return mTitle;
		}

		public String getUrl() {
			return mUrl;
		}
	}

	@NonNull private final Context mContext;
	@NonNull private final Data[] mDataset;

	// Provide a suitable constructor (depends on the kind of dataset)
	public ImageCardViewAdaptor(@NonNull Context context, Data[] myDataset) {
		mContext = context;
		mDataset = myDataset;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public ImageCardViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.row_image_card, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.mTextView.setText(mDataset[position].getTitle());
		Picasso.with(mContext).load(mDataset[position].getUrl()).placeholder(android.R.drawable.ic_popup_sync)
				.error(android.R.drawable.stat_notify_error).into(holder.mImageView);
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.length;
	}



	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		@BindView(R.id.card_view) public CardView mCardView;
		@BindView(R.id.image_imageview) public ImageView mImageView;
		@BindView(R.id.title_textview) public TextView mTextView;

		public ViewHolder(@NonNull View v) {
			super(v);
			ButterKnife.bind(this, v);
		}
	}
}