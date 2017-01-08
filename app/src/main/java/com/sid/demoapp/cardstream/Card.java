package com.sid.demoapp.cardstream;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sid.demoapp.R;

/**
 * Created by Okis on 2017.01.06.
 */

public class Card {
    private int layoutId = R.layout.card;
    private String title;
    private String description;

    private View cardView;
    private TextView titleView;
    private TextView descriptionView;

    public Card() {
    }

    public View getCardView() {
        return cardView;
    }

    public static class Builder {
        private Card card;

        public Builder() {
            card = new Card();
        }

        public Builder setTitle(String title) {
            card.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            card.description = description;
            return this;
        }

        public Card build(Activity activity) {
            final LayoutInflater inflater = activity.getLayoutInflater();
            final ViewGroup cardStreamLayout = (ViewGroup) activity.findViewById(R.id.card_stream);
            final ViewGroup cardView = (ViewGroup) inflater.inflate(card.layoutId, cardStreamLayout, false);
            ((ImageView) cardView.findViewById(R.id.action_delete_card)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardStreamLayout.removeView(cardView);
                }
            });

            final View viewTitle = cardView.findViewById(R.id.card_title);
            if (card.title != null && viewTitle != null) {
                card.titleView = (TextView) viewTitle;
                card.titleView.setText(card.title);
            } else if (viewTitle == null) {
                viewTitle.setVisibility(View.GONE);
            }

            final View viewDescription = cardView.findViewById(R.id.card_description);
            if (card.description != null && viewDescription != null) {
                card.descriptionView = (TextView) viewDescription;
                card.descriptionView.setText(card.description);
            } else if (viewDescription == null) {
                viewDescription.setVisibility(View.GONE);
            }
            card.cardView = cardView;

            return card;
        }
    }
}
