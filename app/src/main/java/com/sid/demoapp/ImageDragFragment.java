package com.sid.demoapp;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageDragFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageDragFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDragFragment extends Fragment {
    public static final String TAG = "ImageDragFragment";

    private ImageView draggableImageView;
    private FrameLayout container;

    private OnFragmentInteractionListener mListener;

    public ImageDragFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImageDragFragment.
     */
    public static ImageDragFragment newInstance() {
        ImageDragFragment fragment = new ImageDragFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_drag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //noinspection ConstantConditions
        draggableImageView = (ImageView) getView().findViewById(R.id.imageView);
        draggableImageView.setOnTouchListener(new TouchListener());
        container = (FrameLayout) getView().findViewById(R.id.container);
        container.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, final DragEvent event) {
                // TODO: 2016-04-19 review code
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        final View draggableView = (View) event.getLocalState();
                        final float x = event.getX() - draggableView.getWidth() / 2;
                        final float y = event.getY() - draggableView.getHeight() / 2;
                        final float dx = event.getX() - draggableView.getWidth() / 2 - draggableView.getX();
                        final float dy = event.getY() - draggableView.getHeight() / 2 - draggableView.getY();

                        Animation animation = new TranslateAnimation(0, dx, 0, dy);
                        animation.setDuration(500L);
                        animation.setInterpolator(new AccelerateDecelerateInterpolator());
                        animation.setAnimationListener(new ImageDragAnimationListener(x, y));
                        draggableView.startAnimation(animation);
                        break;
                }
                return true;
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onImageDragFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onImageDragFragmentInteraction();
    }

    private final class TouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                final ClipData clipData = ClipData.newPlainText("", "");
                final View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(clipData, dragShadowBuilder, v, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class ImageDragAnimationListener implements Animation.AnimationListener {
        private final float x;
        private final float y;

        public ImageDragAnimationListener(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            draggableImageView.setX(x);
            draggableImageView.setY(y);
            draggableImageView.invalidate();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
