package com.adx2099.popularmovies.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    public static interface OnItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mListener;
    private GestureDetectorCompat mDetector;
    private RecyclerView mRecyclerView;
    //----------------------------------------------------------------------------------------------

    public RecyclerItemClickListener(final Context context, RecyclerView recyclerView, OnItemClickListener listener){
        mListener = listener;
        mRecyclerView = recyclerView;
        mDetector = new GestureDetectorCompat(context,this);

    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && mListener != null)
        {
            mListener.onItemLongClick(childView, mRecyclerView.getChildPosition(childView));
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if(childView != null && mListener != null && mDetector.onTouchEvent(motionEvent)){
            mListener.onItemClick(childView, recyclerView.getChildPosition(childView));
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }
    //----------------------------------------------------------------------------------------------
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
    //----------------------------------------------------------------------------------------------
}
