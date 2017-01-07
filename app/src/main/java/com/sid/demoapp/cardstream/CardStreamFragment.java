package com.sid.demoapp.cardstream;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sid.demoapp.R;


public class CardStreamFragment extends Fragment implements View.OnClickListener {

    private CardStreamLinearLayout layout;

    public CardStreamFragment() {
        // Required empty public constructor
    }

    public static CardStreamFragment newInstance() {
        return new CardStreamFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card_stream, container, false);
        layout = (CardStreamLinearLayout) view.findViewById(R.id.card_stream);
        view.findViewById(R.id.action_add_card).setOnClickListener(this);
        return view;
    }

    private void addCard() {
        final Card card = new Card.Builder()
                .setTitle("Big Show")
                .setDescription("Today Big event will take place.\n Stay tuned.")
                .build(getActivity());
        layout.addCard(card.getCardView());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_add_card:
                addCard();
                break;
        }
    }
}
