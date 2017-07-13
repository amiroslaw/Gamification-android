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
import xyz.miroslaw.gamification_android.viewUtils.DeckListAdapter;
import xyz.miroslaw.gamification_android.viewUtils.Decks;
import xyz.miroslaw.gamification_android.viewUtils.RecyclerTouchListener;


public class DeckListFragment extends Fragment implements DeckListContract.View {
    private final String DEBUGTAG = getClass().getSimpleName();
    @BindView(R.id.rv_deckList)
    RecyclerView recyclerView;

    private List<Decks> decksList = Arrays.asList(
            new Decks(1,"fjdi"),
            new Decks(2,"fjcccccccdi"),
            new Decks(3,"fjdaaaaaai"),
            new Decks(1,"fjdi"),
            new Decks(2,"fjdeeeeei"),new Decks(1,"fjdi"),
            new Decks(2,"fjdi"),
            new Decks(3,"fjdssssi"),new Decks(1,"fjdi"),
            new Decks(2,"fjddddddddi"),
            new Decks(3,"fjdi"),new Decks(1,"fjdi"),
            new Decks(2,"fjdi"),
            new Decks(3,"fjdi"),
            new Decks(3,"fjdi")
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
        deckListAdapter = new DeckListAdapter(decksList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deckListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Decks decks = decksList.get(position);
                Toast.makeText(getContext().getApplicationContext(), decks.getDeckName() + " is selected!", Toast.LENGTH_SHORT).show();
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