package ua.ck.ghplayer.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.ck.ghplayer.R;
import ua.ck.ghplayer.interfaces.ItemClickFragmentSetter;
import ua.ck.ghplayer.models.Artist;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder> {
    private ArrayList<Artist> data;
    private Activity parentActivity;

    public ArtistListAdapter(ArrayList<Artist> data, Activity parentActivity) {
        super();
        this.data = data;
        this.parentActivity = parentActivity;
    }

    @Override
    public ArtistListAdapter.ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_artist_list, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistListAdapter.ArtistViewHolder holder, int position) {
        Artist artist = data.get(position);
        //holder.cover.setImageBitmap();
        holder.artist.setText(artist.getArtist());
        holder.numberOfAlbums.setText(String.valueOf(artist.getNumberOfAlbums()));
        holder.numberOfTracks.setText(String.valueOf(artist.getNumberOfTracks()));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView cover;
        public TextView artist;
        public TextView numberOfAlbums;
        public TextView numberOfTracks;


        public ArtistViewHolder(View itemView) {
            super(itemView);
            this.cover = (ImageView) itemView.findViewById(R.id.item_artist_list_cover);
            this.artist = (TextView) itemView.findViewById(R.id.item_artist_list_artist);
            this.numberOfAlbums = (TextView) itemView.findViewById(R.id.item_artist_list_number_of_albums);
            this.numberOfTracks = (TextView) itemView.findViewById(R.id.item_artist_list_number_of_tracks);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Artist artist = data.get(position);
            ItemClickFragmentSetter itemClick = (ItemClickFragmentSetter) parentActivity;
            itemClick.onArtistListItemClick(artist.getId(),artist.getArtist());
        }
    }
}
