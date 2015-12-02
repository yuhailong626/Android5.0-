package yhl.come.android50;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yuhailong on 15/11/29.
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.NormalTextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<String> mTitles = new ArrayList<>();

    public RecylerViewAdapter(Context context) {
        Collections.addAll(mTitles, context.getResources().getStringArray(R.array.titles));
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false),this);
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTextView.setText(mTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        RecylerViewAdapter mAdapter;
        NormalTextViewHolder(View view, final RecylerViewAdapter adapter) {
            super(view);
            this.mAdapter = adapter;
            mTextView = (TextView) view.findViewById(R.id.text_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition == 0) {
                        mAdapter.addItem("add item "+adapterPosition);
                    }else if (adapterPosition == mAdapter.getItemCount()-1) {
                        mAdapter.removeItem(adapterPosition);
                    }
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getAdapterPosition());
                }
            });
        }
    }

    public void addItem(String title) {
        mTitles.add(1, title);
        notifyItemInserted(1);
    }

    public void removeItem(int position) {
        mTitles.remove(position);
        notifyItemRemoved(position);
    }
}