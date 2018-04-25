//package com.jullae.ui.adapters;
//
//import android.content.Context;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jullae.R;
//import com.jullae.constant.AppConstant;
//import com.jullae.ui.homefeed.HomeActivity;
//
//import java.util.ArrayList;
//
///**
// * Created by Rahul Abrol on 12/13/17.
// * <p>
// * Adapter class used to hold all the feeds of
// * the @{@link HomeActivity} with
// * few options like comments,likes,feeds,stories,edit stories etc.
// */
//public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ConversationHolder> {
//
//    private final LayoutInflater inflater;
//    private ArrayList<String> list;
//    private Context context;
//    private FragmentManager fragmentManager;
//    private FeedListener listener;
//
//    /**
//     * Constructor with two parameters one is the
//     * for the calling class context and other is
//     * list of feeds.
//     *
//     * @param context              context
//     * @param feedList             list items;
//     * @param childFragmentManager fragment Manager
//     */
//    public HomeFeedAdapter(final Context context, final ArrayList<String> feedList,
//                           final FragmentManager childFragmentManager) {
//        this.list = feedList;
//        this.context = context;
//        this.fragmentManager = childFragmentManager;
//        inflater = LayoutInflater.from(this.context);
//        listener = (FeedListener) context;
//    }
//
//    @Override
//    public ConversationHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
//        return new ConversationHolder(inflater
//                .inflate(R.layout.adapter_feeds, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(final ConversationHolder holder, final int position) {
//        holder.tvUserName.setText(list.get(position));
//        holder.tvLocation.setText(list.get(position));
//        holder.tvTimeInDays.setText("4 Days Ago");
//        holder.tvComments.setText("10  Comments");
//        holder.tvLikes.setText("20 Likes");
//    }
//
//    @Override
//    public int getItemCount() {
//        return list == null ? 0 : list.size();
//    }
//
//    /**
//     * Used as a Callback for click.
//     */
//    public interface FeedListener {
//        /**
//         * USed as a callback method.
//         *
//         * @param position   position of clicked item.
//         * @param clickedTag clicked item tag.
//         */
//        void onClick(int position, String clickedTag);
//    }
//
//    /**
//     * Class @{@link ConversationHolder} used to bind
//     * the views with the recyclerview.;
//     */
//    class ConversationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private ImageView ivUserPic, ivMore, ivStoryPic, ivLike, ivEditStory;
//        private TextView tvUserName, tvLocation, tvTimeInDays, tvLikes, tvComments;
//        private RecyclerView rvStories;
////        private ViewPager viewPagerSlider;
////        private CircleIndicator circleIndicator;
//
//        /**
//         * Constructor to initialize the view Attribute.
//         *
//         * @param itemView Item view.
//         */
//        ConversationHolder(final View itemView) {
//            super(itemView);
////            circleIndicator = itemView.findViewById(R.id.circleIndicator);
////            viewPagerSlider = itemView.findViewById(R.id.viewPagerSlider);
//            rvStories = itemView.findViewById(R.id.rvStories);
//            ivUserPic = itemView.findViewById(R.id.ivUserPic);
//            ivMore = itemView.findViewById(R.id.ivMore);
//            ivStoryPic = itemView.findViewById(R.id.ivStoryPic);
//            ivLike = itemView.findViewById(R.id.ivLike);
//            ivEditStory = itemView.findViewById(R.id.ivEditStory);
//            tvUserName = itemView.findViewById(R.id.tvUserName);
//            tvLocation = itemView.findViewById(R.id.tvLocation);
//            tvTimeInDays = itemView.findViewById(R.id.tvTimeInDays);
//            tvLikes = itemView.findViewById(R.id.tvLikes);
//            tvComments = itemView.findViewById(R.id.tvComments);
//            //Listeners Initializations
//            ivEditStory.setOnClickListener(this);
//            ivUserPic.setOnClickListener(this);
//            ivMore.setOnClickListener(this);
//            ivStoryPic.setOnClickListener(this);
//            tvUserName.setOnClickListener(this);
//            tvLocation.setOnClickListener(this);
//            ivEditStory.setOnClickListener(this);
//            ivLike.setOnClickListener(this);
//
////            for (int i = 0; i < 3; i++) {
////                List<Fragment> sliderFragment = new ArrayList<>();
////                Bundle bundle = new Bundle();
////                bundle.putStringArrayList(AppConstant.LIST, list);
////                SliderFragment fragmentSlide = SliderFragment.getInstance(bundle);
////                sliderFragment.add(fragmentSlide);
////                SlideAdapter sliderAdapter = new SlideAdapter(fragmentManager, sliderFragment);
////                viewPagerSlider.setAdapter(sliderAdapter);
////                circleIndicator.setViewPager(viewPagerSlider);
////            }
//        }
//
//        @Override
//        public void onClick(final View v) {
//            //1. UserPic
//            //2. More
//            //3. StoryPic
//            //4. Like
//            //5. Edit
//            //6. UserName
//            //7. Location
//
//            switch (v.getId()) {
//                case R.id.ivLike:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_LIKE);
//                    break;
//                case R.id.ivEditStory:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_EDIT);
//                    break;
//                case R.id.ivUserPic:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_USER_PIC);
//                    break;
//                case R.id.ivMore:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_MORE);
//                    break;
//                case R.id.ivStoryPic:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_STORY_PIC);
//                    break;
//                case R.id.tvUserName:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_USER_NAME);
//                    break;
//                case R.id.tvLocation:
//                    listener.onClick(getAdapterPosition(), AppConstant.ITEM_LOC);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//}
