package xyz.miroslaw.gamification_android.viewUtils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;


public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.MyViewHolder> {
    private List<Deck> deckList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_deckList_name)
        public TextView deckName;
        @BindView(R.id.txt_deckList_id)
        public TextView id;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public DeckListAdapter(List<Deck> deckList) {
        this.deckList = deckList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_deck, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Deck deck = deckList.get(position);
        holder.deckName.setText(deck.getDeckName());
        holder.id.setText(Integer.toString(deck.getId()));
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }


}
