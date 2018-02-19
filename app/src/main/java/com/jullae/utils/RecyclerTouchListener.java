package com.jullae.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Class used to handle recycler View Touch listener.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    @Nullable
    private GestureDetector gestureDetector;
    @Nullable
    private ClickListener clickListener;

    /**
     * Constructor.
     *
     * @param context       calling class context.
     * @param recyclerView  recycler view instance
     * @param clickListener callback.
     */
    public RecyclerTouchListener(final Context context, @NonNull final RecyclerView recyclerView, @Nullable final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(final MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(@NonNull final MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull final RecyclerView rv, @NonNull final MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(final RecyclerView rv, final MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {
    }

    /**
     * Interface to respond when onClick and onlongclick.
     */
    public interface ClickListener {
        /**
         * on Click callback.
         *
         * @param view     view that is clicked
         * @param position clicked position
         */
        void onClick(View view, int position);

        /**
         * On long clicked handle.
         *
         * @param view     view that is clicked
         * @param position position
         */
        void onLongClick(View view, int position);
    }
}