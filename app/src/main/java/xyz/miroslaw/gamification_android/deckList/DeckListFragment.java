package xyz.miroslaw.gamification_android.deckList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.miroslaw.gamification_android.R;
import xyz.miroslaw.gamification_android.viewUtils.ClickListener;
import xyz.miroslaw.gamification_android.viewUtils.Deck;
import xyz.miroslaw.gamification_android.viewUtils.DeckListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;


public class DeckListFragment extends Fragment implements DeckListContract.View {
    @BindView(R.id.rv_deckList)
    RecyclerView recyclerView;

    private List<Deck> deckList = Arrays.asList(
            new Deck(1,"fjdi"),
            new Deck(2,"fjcccccccdi"),
            new Deck(3,"fjdaaaaaai"),
            new Deck(1,"fjdi"),
            new Deck(2,"fjdeeeeei"),new Deck(1,"fjdi"),
            new Deck(2,"fjdi"),
            new Deck(3,"fjdssssi"),new Deck(1,"fjdi"),
            new Deck(2,"fjddddddddi"),
            new Deck(3,"fjdi"),new Deck(1,"fjdi"),
            new Deck(2,"fjdi"),
            new Deck(3,"fjdi"),
            new Deck(3,"fjdi")
    );

    private DeckListAdapter deckListAdapter;
    private DeckListContract.Presenter presenter;

    public DeckListFragment() {
        // Required empty public constructor
    }

    public static DeckListFragment newInstance() {
        return new DeckListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new DeckListPresenter(this);
        }
        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);
        ButterKnife.bind(this, view);

        createRrecyclerview();

        return view;
    }
    private void createRrecyclerview() {
        deckListAdapter = new DeckListAdapter(deckList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deckListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Deck deck = deckList.get(position);
                Toast.makeText(getContext().getApplicationContext(), deck.getDeckName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
    @Override
    public void setPresenter(DeckListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}