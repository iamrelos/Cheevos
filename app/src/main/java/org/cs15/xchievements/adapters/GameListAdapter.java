package org.cs15.xchievements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.cs15.xchievements.R;
import org.cs15.xchievements.misc.Singleton;
import org.cs15.xchievements.objects.GameDetails;

import java.util.List;

public class GameListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GameDetails> mList;

    public GameListAdapter(List<GameDetails> data, Context context) {
        mContext = context;
        mList = data;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // variables
        ViewHolder mViewHolder;

        // check if view is null
        if (view == null) {
            // inflate layout
            LayoutInflater viewInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = viewInflater.inflate(R.layout.row_game_list, null);

            // view holder
            mViewHolder = new ViewHolder();
            mViewHolder.mIvCover = (NetworkImageView) view.findViewById(R.id.iv_game_cover);
            mViewHolder.mTvTitle = (TextView) view.findViewById(R.id.tv_game_title);
            mViewHolder.mTvSubTitle = (TextView) view.findViewById(R.id.tv_game_subtitle);

            // set tag
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        // set titles
        mViewHolder.mIvCover.setImageUrl(mList.get(i).getCoverUrl(), Singleton.getImageLoader());
        mViewHolder.mTvTitle.setText(mList.get(i).getTitle());
        mViewHolder.mTvSubTitle.setText(String.format("%s Achievements", mList.get(i).getAchievementsAmount()));

        // return view
        return view;

    }

    class ViewHolder {
        NetworkImageView mIvCover;
        TextView mTvTitle;
        TextView mTvSubTitle;
    }
}
