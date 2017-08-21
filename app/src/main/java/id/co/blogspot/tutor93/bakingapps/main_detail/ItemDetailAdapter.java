package id.co.blogspot.tutor93.bakingapps.main_detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.co.blogspot.tutor93.bakingapps.R;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;

/**
 * Created by indraaguslesmana on 8/21/17.
 */

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    private List<Step> mValues;
    private String mRecipesName;
    private ListItemClickListener mOnItemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> stepsOut, int clickedItemIndex, String recipeName);
    }

    public ItemDetailAdapter(ListItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setupData(List<Step> mValues, String mRecipesName) {
        this.mValues = mValues;
        this.mRecipesName = mRecipesName;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stepcard, parent, false);
        return new ItemDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mStepCount.setText(String.valueOf(
                holder.mView.getContext().getString(R.string.step_count,
                        holder.mItem.id == 0 ? "Intro" : holder.mItem.id.toString())));
        holder.mShortdescription.setText(mValues.get(position).shortDescription);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final View mView;
        final TextView mStepCount;
        final TextView mShortdescription;
        Step mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mStepCount = (TextView) view.findViewById(R.id.step_count);
            mShortdescription = (TextView) view.findViewById(R.id.tv_shortdescription);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mShortdescription.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnItemClickListener.onListItemClick(mValues, clickedPosition, mRecipesName);
        }
    }
}
