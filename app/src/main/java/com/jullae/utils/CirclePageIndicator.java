//package com.jullae.utils;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.graphics.drawable.Drawable;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.v4.view.MotionEventCompat;
//import android.support.v4.view.ViewConfigurationCompat;
//import android.support.v4.view.ViewPager;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//
//import com.jullae.R;
//
//import static android.graphics.Paint.ANTI_ALIAS_FLAG;
//import static android.widget.LinearLayout.HORIZONTAL;
//import static android.widget.LinearLayout.VERTICAL;
//
///**
// * Draws circles (one for each view). The current view position is filled and
// * others are only stroked.
// */
//public class CirclePageIndicator extends View implements PageIndicator {
//    private static final int INVALID_POINTER = -1;
//    private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
//    private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
//    private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);
//    private float mRadius;
//    private ViewPager mViewPager;
//    private ViewPager.OnPageChangeListener mListener;
//    private int mCurrentPage;
//    private int mSnapPage;
//    private float mPageOffset;
//    private int mScrollState;
//    private int mOrientation;
//    private boolean mCentered;
//    private boolean mSnap;
//
//    private int mTouchSlop;
//    private float mLastMotionX = -1;
//    private int mActivePointerId = INVALID_POINTER;
//    private boolean mIsDragging;
//
//
//    /**
//     * Instantiates a new Circle page indicator.
//     *
//     * @param context the context
//     */
//    public CirclePageIndicator(final Context context) {
//        this(context, null);
//    }
//
//    /**
//     * Instantiates a new Circle page indicator.
//     *
//     * @param context the context
//     * @param attrs   the attrs
//     */
//    public CirclePageIndicator(final Context context, final AttributeSet attrs) {
//        this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
//    }
//
//    /**
//     * Instantiates a new Circle page indicator.
//     *
//     * @param context  the context
//     * @param attrs    the attrs
//     * @param defStyle the def style
//     */
//    public CirclePageIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
//        super(context, attrs, defStyle);
//        if (isInEditMode()) {
//            return;
//        }
//
//        //Load defaults from resources
//        final Resources res = getResources();
//        final int defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color);
//        final int defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color);
//        final int defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation);
//        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
//        final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
//        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
//        final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
//        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
//
//        //Retrieve styles attributes
//        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, 0);
//
//        mCentered = a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered);
//        mOrientation = a.getInt(R.styleable.CirclePageIndicator_android_orientation, defaultOrientation);
//        mPaintPageFill.setStyle(Style.FILL);
//        mPaintPageFill.setColor(a.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor));
//        mPaintStroke.setStyle(Style.STROKE);
//        mPaintStroke.setColor(a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor));
//        mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth));
//        mPaintFill.setStyle(Style.FILL);
//        mPaintFill.setColor(a.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor));
//        mRadius = a.getDimension(R.styleable.CirclePageIndicator_radius, defaultRadius);
//        mSnap = a.getBoolean(R.styleable.CirclePageIndicator_snap, defaultSnap);
//
//        final Drawable background = a.getDrawable(R.styleable.CirclePageIndicator_android_background);
//        if (background != null) {
//            setBackgroundDrawable(background);
//        }
//
//        a.recycle();
//
//        final ViewConfiguration configuration = ViewConfiguration.get(context);
//        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
//    }
//
//    /**
//     * Is centered boolean.
//     *
//     * @return the boolean
//     */
//    public boolean isCentered() {
//        return mCentered;
//    }
//
//    /**
//     * Sets centered.
//     *
//     * @param centered the centered
//     */
//    public void setCentered(final boolean centered) {
//        mCentered = centered;
//        invalidate();
//    }
//
//    /**
//     * Gets page color.
//     *
//     * @return the page color
//     */
//    public int getPageColor() {
//        return mPaintPageFill.getColor();
//    }
//
//    /**
//     * Sets page color.
//     *
//     * @param pageColor the page color
//     */
//    public void setPageColor(final int pageColor) {
//        mPaintPageFill.setColor(pageColor);
//        invalidate();
//    }
//
//    /**
//     * Gets fill color.
//     *
//     * @return the fill color
//     */
//    public int getFillColor() {
//        return mPaintFill.getColor();
//    }
//
//    /**
//     * Sets fill color.
//     *
//     * @param fillColor the fill color
//     */
//    public void setFillColor(final int fillColor) {
//        mPaintFill.setColor(fillColor);
//        invalidate();
//    }
//
//    /**
//     * Gets orientation.
//     *
//     * @return the orientation
//     */
//    public int getOrientation() {
//        return mOrientation;
//    }
//
//    /**
//     * Sets orientation.
//     *
//     * @param orientation the orientation
//     */
//    public void setOrientation(final int orientation) {
//        switch (orientation) {
//            case HORIZONTAL:
//            case VERTICAL:
//                mOrientation = orientation;
//                requestLayout();
//                break;
//
//            default:
//                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
//        }
//    }
//
//    /**
//     * Gets stroke color.
//     *
//     * @return the stroke color
//     */
//    public int getStrokeColor() {
//        return mPaintStroke.getColor();
//    }
//
//    /**
//     * Sets stroke color.
//     *
//     * @param strokeColor the stroke color
//     */
//    public void setStrokeColor(final int strokeColor) {
//        mPaintStroke.setColor(strokeColor);
//        invalidate();
//    }
//
//    /**
//     * Gets stroke width.
//     *
//     * @return the stroke width
//     */
//    public float getStrokeWidth() {
//        return mPaintStroke.getStrokeWidth();
//    }
//
//    /**
//     * Sets stroke width.
//     *
//     * @param strokeWidth the stroke width
//     */
//    public void setStrokeWidth(final float strokeWidth) {
//        mPaintStroke.setStrokeWidth(strokeWidth);
//        invalidate();
//    }
//
//    /**
//     * Gets radius.
//     *
//     * @return the radius
//     */
//    public float getRadius() {
//        return mRadius;
//    }
//
//    /**
//     * Sets radius.
//     *
//     * @param radius the radius
//     */
//    public void setRadius(final float radius) {
//        mRadius = radius;
//        invalidate();
//    }
//
//    /**
//     * Is snap boolean.
//     *
//     * @return the boolean
//     */
//    public boolean isSnap() {
//        return mSnap;
//    }
//
//    /**
//     * Sets snap.
//     *
//     * @param snap the snap
//     */
//    public void setSnap(final boolean snap) {
//        mSnap = snap;
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (mViewPager == null) {
//            return;
//        }
//        final int count = mViewPager.getAdapter().getCount();
//        if (count == 0) {
//            return;
//        }
//
//        if (mCurrentPage >= count) {
//            setCurrentItem(count - 1);
//            return;
//        }
//
//        final int longSize;
//        final int longPaddingBefore;
//        final int longPaddingAfter;
//        final int shortPaddingBefore;
//        if (mOrientation == HORIZONTAL) {
//            longSize = getWidth();
//            longPaddingBefore = getPaddingLeft();
//            longPaddingAfter = getPaddingRight();
//            shortPaddingBefore = getPaddingTop();
//        } else {
//            longSize = getHeight();
//            longPaddingBefore = getPaddingTop();
//            longPaddingAfter = getPaddingBottom();
//            shortPaddingBefore = getPaddingLeft();
//        }
//
//        final float threeRadius = mRadius * 3;
//        final float shortOffset = shortPaddingBefore + mRadius;
//        float longOffset = longPaddingBefore + mRadius;
//        if (mCentered) {
//            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * threeRadius) / 2.0f);
//        }
//
//        float dX;
//        float dY;
//
//        float pageFillRadius = mRadius;
//        if (mPaintStroke.getStrokeWidth() > 0) {
//            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
//        }
//
//        //Draw stroked circles
//        for (int iLoop = 0; iLoop < count; iLoop++) {
//            final float drawLong = longOffset + (iLoop * threeRadius);
//            if (mOrientation == HORIZONTAL) {
//                dX = drawLong;
//                dY = shortOffset;
//            } else {
//                dX = shortOffset;
//                dY = drawLong;
//            }
//            // Only paint fill if not completely transparent
//            if (mPaintPageFill.getAlpha() > 0) {
//                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
//            }
//
//            // Only paint stroke if a stroke width was non-zero
//            if (pageFillRadius != mRadius) {
//                canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
//            }
//        }
//
//        //Draw the filled circle according to the current scroll
//        float cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius;
//        if (!mSnap) {
//            cx += mPageOffset * threeRadius;
//        }
//        if (mOrientation == HORIZONTAL) {
//            dX = longOffset + cx;
//            dY = shortOffset;
//        } else {
//            dX = shortOffset;
//            dY = longOffset + cx;
//        }
//        canvas.drawCircle(dX, dY, mRadius, mPaintFill);
//    }
//
//    /**
//     * onTouchEvent method to check whether touch has been performed
//     *
//     * @param ev MotionEvent instance
//     * @return onTouchEvent status
//     */
//    public boolean onTouchEvent(final MotionEvent ev) {
//        if (super.onTouchEvent(ev)) {
//            return true;
//        }
//        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
//            return false;
//        }
//
//        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
//                mLastMotionX = ev.getX();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
//                final float x = MotionEventCompat.getX(ev, activePointerIndex);
//                final float deltaX = x - mLastMotionX;
//
//                if (!mIsDragging) {
//                    if (Math.abs(deltaX) > mTouchSlop) {
//                        mIsDragging = true;
//                    }
//                }
//
//                if (mIsDragging) {
//                    mLastMotionX = x;
//                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
//                        mViewPager.fakeDragBy(deltaX);
//                    }
//                }
//
//                break;
//
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                if (!mIsDragging) {
//                    final int count = mViewPager.getAdapter().getCount();
//                    final int width = getWidth();
//                    final float halfWidth = width / 2f;
//                    final float sixthWidth = width / 6f;
//
//                    if ((mCurrentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
//                        if (action != MotionEvent.ACTION_CANCEL) {
//                            mViewPager.setCurrentItem(mCurrentPage - 1);
//                        }
//                        return true;
//                    } else if ((mCurrentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
//                        if (action != MotionEvent.ACTION_CANCEL) {
//                            mViewPager.setCurrentItem(mCurrentPage + 1);
//                        }
//                        return true;
//                    }
//                }
//
//                mIsDragging = false;
//                mActivePointerId = INVALID_POINTER;
//                if (mViewPager.isFakeDragging()) {
//                    mViewPager.endFakeDrag();
//                }
//                break;
//
//            case MotionEventCompat.ACTION_POINTER_DOWN:
//                final int index = MotionEventCompat.getActionIndex(ev);
//                mLastMotionX = MotionEventCompat.getX(ev, index);
//                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
//                break;
//
//
//            case MotionEventCompat.ACTION_POINTER_UP:
//                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
//                if (pointerId == mActivePointerId) {
//                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
//                }
//                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId));
//                break;
//
//            default:
//                break;
//        }
//
//        return true;
//    }
//
//    @Override
//    public void setViewPager(final ViewPager view) {
//        if (mViewPager == view) {
//            return;
//        }
//        if (mViewPager != null) {
//            mViewPager.setOnPageChangeListener(null);
//        }
//        if (view.getAdapter() == null) {
//            throw new IllegalStateException("ViewPager does not have com.skeleton.adapter instance.");
//        }
//        mViewPager = view;
//        mViewPager.setOnPageChangeListener(this);
//        invalidate();
//    }
//
//    @Override
//    public void setViewPager(final ViewPager view, final int initialPosition) {
//        setViewPager(view);
//        setCurrentItem(initialPosition);
//    }
//
//    @Override
//    public void setCurrentItem(final int item) {
//        if (mViewPager == null) {
//            throw new IllegalStateException("ViewPager has not been bound.");
//        }
//        mViewPager.setCurrentItem(item);
//        mCurrentPage = item;
//        invalidate();
//    }
//
//    @Override
//    public void notifyDataSetChanged() {
//        invalidate();
//    }
//
//    @Override
//    public void onPageScrollStateChanged(final int state) {
//        mScrollState = state;
//
//        if (mListener != null) {
//            mListener.onPageScrollStateChanged(state);
//        }
//    }
//
//    @Override
//    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
//        mCurrentPage = position;
//        mPageOffset = positionOffset;
//        invalidate();
//
//        if (mListener != null) {
//            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
//        }
//    }
//
//    @Override
//    public void onPageSelected(final int position) {
//        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
//            mCurrentPage = position;
//            mSnapPage = position;
//            invalidate();
//        }
//
//        if (mListener != null) {
//            mListener.onPageSelected(position);
//        }
//    }
//
//    @Override
//    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
//        mListener = listener;
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see android.view.View#onMeasure(int, int)
//     */
//    @Override
//    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
//        if (mOrientation == HORIZONTAL) {
//            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
//        } else {
//            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
//        }
//    }
//
//    /**
//     * Determines the width of this view
//     *
//     * @param measureSpec A measureSpec packed into an int
//     * @return The width of the view, honoring constraints from measureSpec
//     */
//    private int measureLong(final int measureSpec) {
//        int result;
//        final int specMode = MeasureSpec.getMode(measureSpec);
//        final int specSize = MeasureSpec.getSize(measureSpec);
//
//        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
//            //We were told how big to be
//            result = specSize;
//        } else {
//            //Calculate the width according the views count
//            final int count = mViewPager.getAdapter().getCount();
//            result = (int) (getPaddingLeft() + getPaddingRight()
//                    + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
//            //Respect AT_MOST value if that was what is called for by measureSpec
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Determines the height of this view
//     *
//     * @param measureSpec A measureSpec packed into an int
//     * @return The height of the view, honoring constraints from measureSpec
//     */
//    private int measureShort(final int measureSpec) {
//        int result;
//        final int specMode = MeasureSpec.getMode(measureSpec);
//        final int specSize = MeasureSpec.getSize(measureSpec);
//
//        if (specMode == MeasureSpec.EXACTLY) {
//            //We were told how big to be
//            result = specSize;
//        } else {
//            //Measure the height
//            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
//            //Respect AT_MOST value if that was what is called for by measureSpec
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public void onRestoreInstanceState(final Parcelable state) {
//        final SavedState savedState = (SavedState) state;
//        super.onRestoreInstanceState(savedState.getSuperState());
//        mCurrentPage = savedState.currentPage;
//        mSnapPage = savedState.currentPage;
//        requestLayout();
//    }
//
//    @Override
//    public Parcelable onSaveInstanceState() {
//        final Parcelable superState = super.onSaveInstanceState();
//        final SavedState savedState = new SavedState(superState);
//        savedState.currentPage = mCurrentPage;
//        return savedState;
//    }
//
//    /**
//     * The type Saved state.
//     */
//    static class SavedState extends BaseSavedState {
//        /**
//         * The constant CREATOR.
//         */
//        @SuppressWarnings("UnusedDeclaration")
//        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
//            @Override
//            public SavedState createFromParcel(final Parcel in) {
//                return new SavedState(in);
//            }
//
//            @Override
//            public SavedState[] newArray(final int size) {
//                return new SavedState[size];
//            }
//        };
//        /**
//         * The Current page.
//         */
//        private int currentPage;
//
//        /**
//         * Instantiates a new Saved state.
//         *
//         * @param superState the super state
//         */
//        public SavedState(final Parcelable superState) {
//            super(superState);
//        }
//
//        /**
//         * SavedState , to save currentPage state
//         *
//         * @param in Parcel instance
//         */
//        private SavedState(final Parcel in) {
//            super(in);
//            currentPage = in.readInt();
//        }
//
//        /**
//         * getter of CurrentPage
//         *
//         * @return currentPage value
//         */
//        public int getCurrentPage() {
//            return currentPage;
//        }
//
//        /**
//         * setter of currentPage
//         * @param currentPage , currentPage in int form
//         */
//        public void setCurrentPage(final int currentPage) {
//            this.currentPage = currentPage;
//        }
//
//        @Override
//        public void writeToParcel(final Parcel dest, final int flags) {
//            super.writeToParcel(dest, flags);
//            dest.writeInt(currentPage);
//        }
//    }
//}
//
