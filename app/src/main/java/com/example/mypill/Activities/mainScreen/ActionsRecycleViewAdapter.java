package com.example.mypill.Activities.mainScreen;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypill.Activities.data.LocalDBHandler;
import com.example.mypill.R;

import androidx.recyclerview.widget.RecyclerView;

public class ActionsRecycleViewAdapter extends RecyclerView.Adapter<ActionsRecycleViewAdapter.ActionViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public ActionsRecycleViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ActionViewHolder extends RecyclerView.ViewHolder {
        public TextView actionTextView, dateTextView;
        private ImageView actionImageView;

        public ActionViewHolder(View itemView) {
            super(itemView);

            actionTextView = itemView.findViewById(R.id.actionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            actionImageView = itemView.findViewById(R.id.actionImageView);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new ActionViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String actionName = mCursor.getString(mCursor.getColumnIndex(LocalDBHandler.FeedEntry.COLUMN_NAME_ACTION));
        String time = mCursor.getString(mCursor.getColumnIndex(LocalDBHandler.FeedEntry.COLUMN_NAME_TIME));

        holder.actionTextView.setText(actionName);
        holder.dateTextView.setText(time.split(" ")[1]);

        if (actionName.equals("taken")) {
            holder.actionImageView.setImageDrawable(mContext.getDrawable(R.drawable.intake_action));
            holder.actionTextView.setText(mContext.getText(R.string.pillIngestedAction));
        } else if (actionName.equals("snooze")) {
            holder.actionImageView.setImageDrawable(mContext.getDrawable(R.drawable.snooze_action));
            holder.actionTextView.setText(mContext.getText(R.string.pillSnoozedAction));
        } else if (actionName.equals("forgotten")) {
            holder.actionImageView.setImageDrawable(mContext.getDrawable(R.drawable.forget_action));
            holder.actionTextView.setText(mContext.getText(R.string.pillForgottenAction));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
