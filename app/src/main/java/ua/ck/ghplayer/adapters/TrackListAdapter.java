package ua.ck.ghplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import ua.ck.ghplayer.R;
import ua.ck.ghplayer.events.StartMiniPlayerEvent;
import ua.ck.ghplayer.lists.TrackList;
import ua.ck.ghplayer.utils.Constants;
import ua.ck.ghplayer.utils.PlaylistUtils;

public class TrackListAdapter extends RecyclerSwipeAdapter<TrackListAdapter.MyViewHolder> {

    private Context context;
    private EventBus eventBus = EventBus.getDefault();

    public TrackListAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_track_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(TrackList.getInstance().getTrackList().get(position).getTitle());
        holder.artist.setText(TrackList.getInstance().getTrackList().get(position).getArtist());
        holder.album.setText(TrackList.getInstance().getTrackList().get(position).getAlbum());
        if (TrackList.getInstance().getTrackList().get(position).getAlbumArt() != null) {
            Picasso.with(context)
                    .load(TrackList.getInstance().getTrackList().get(position).getAlbumArt())
                    .into(holder.albumArt);
        } else {
            Picasso.with(context)
                    .load(R.drawable.album_cover_default)
                    .into(holder.albumArt);
        }

        // Swipe Layout
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.item_track_list_bottom_view));

        // Main Surface Click
        holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sending the current track position in the MiniPlayer
                eventBus.post(new StartMiniPlayerEvent(Constants.MAIN_TRACK_LIST_ID, position));
            }
        });

        // Button Cancel Click
        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.closeAllItems();
            }
        });

        // Button Favorite Click
        holder.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaylistUtils.addTrackToFavorite(context, TrackList.getInstance().getTrackList().get(position).getId());
                mItemManger.closeAllItems();
            }
        });

        // Swipe Item Manger
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return TrackList.getInstance().getTrackList() != null ? TrackList.getInstance().getTrackList().size() : 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.item_track_list_swipe;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Views
        private TextView title;
        private TextView artist;
        private TextView album;
        private ImageView albumArt;
        private SwipeLayout swipeLayout;
        private TextView buttonFavorite;
        private TextView buttonCancel;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.item_track_list_title);
            this.artist = (TextView) itemView.findViewById(R.id.item_track_list_artist);
            this.album = (TextView) itemView.findViewById(R.id.item_track_list_album);
            this.albumArt = (ImageView) itemView.findViewById(R.id.item_track_list_album_art);
            this.swipeLayout = (SwipeLayout) itemView.findViewById(R.id.item_track_list_swipe);
            this.buttonFavorite = (TextView) itemView.findViewById(R.id.item_track_list_button_favorite);
            this.buttonCancel = (TextView) itemView.findViewById(R.id.item_track_list_button_cancel);
        }
    }
}
