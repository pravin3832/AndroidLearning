package learning.android.tenmarks.com.androidlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import learning.android.tenmarks.com.androidlearning.responses.DiscoverResponse;

/**
 * Created by Talha on 10/26/15.
 */
public class MoviesAdapter extends BaseAdapter {
    List<DiscoverResponse.DiscoverResult> mItems;
    Context mContext;
    LayoutInflater mInflater;
    ImageLoader imageLoader;


    public MoviesAdapter(Context context, List<DiscoverResponse.DiscoverResult> responseList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mItems = responseList;
        imageLoader = new ImageLoader(Volley.newRequestQueue(mContext), new LruBitmapCache(getDefaultLruCacheSize()));
    }

    public int getDefaultLruCacheSize() {
        final int maxMemory =
                (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    @Override
    public int getCount() {
        return (mItems != null) ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (mItems != null && position > -1 && position < mItems.size()) ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        long toReturn = 0;
        if (position > -1 && position < mItems.size()) {
            toReturn = mItems.get(position).id;
        }
        return toReturn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.row_movie_db, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        int index = position + 1;
        holder.tvTitle.setText(index + ". " + mItems.get(position).title);
        holder.tvOverview.setText(mItems.get(position).overview);
        holder.imgThumb.setImageUrl("http://image.tmdb.org/t/p/w154" + mItems.get(position).backdrop_path, imageLoader);

        return convertView;
    }

    // View holder class...
    private static class ViewHolder {

        public TextView tvTitle;
        public TextView tvOverview;
        public NetworkImageView imgThumb;

        public ViewHolder(View parent) {
            tvTitle = (TextView) parent.findViewById(R.id.title);
            tvOverview = (TextView) parent.findViewById(R.id.overview);
            imgThumb = (NetworkImageView) parent.findViewById(R.id.imgThumb);
            parent.setTag(ViewHolder.this);
        }
    }
}
