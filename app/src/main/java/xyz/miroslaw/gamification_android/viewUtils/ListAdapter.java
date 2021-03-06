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


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<Item> items;

    public ListAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_deck, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.deckName.setText(item.getName());
        holder.number.setText(item.getNumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void duplicate(int position) {
        Item copy = items.get(position);
        addItems(copy);
    }

    public void duplicate(int position, String deckName) {
        //TODO copy not reference, copy cards
        Item copy = items.get(position);
        copy.setName(deckName);
        addItems(copy);
    }

    private void addItems(Item copy) {
        items.add(copy);
        notifyItemInserted(items.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_deckList_name)
        public TextView deckName;
        @BindView(R.id.txt_list_number)
        public TextView number;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
