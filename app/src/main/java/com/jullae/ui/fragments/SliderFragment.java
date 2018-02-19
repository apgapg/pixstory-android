//package com.jullae.ui.fragments;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.jullae.R;
//import com.jullae.constant.AppConstant;
//import com.jullae.ui.base.BaseFragment;
//
//import java.util.ArrayList;
//
///**
// * Created by Rahul Abrol on 12/18/17.
// * <p>
// * Class @{@link SliderFragment} used as a child of a
// * class @{@link com.jullae.ui.adapters.HomeFeedAdapter} to
// * set the title time story likes and comments etc into the view
// * for user.
// */
//
//public class SliderFragment extends BaseFragment {
//    private Activity mActivity;
//    private ArrayList<String> listItems;
//
//    /**
//     * Method used to get the instance of this
//     * fragment with bundle.
//     *
//     * @param bundle bundle to pass data.
//     * @return instance of @{@link SliderFragment}.
//     */
//    public static SliderFragment getInstance(final Bundle bundle) {
//        SliderFragment fragment = new SliderFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    public void onAttach(final Context context) {
//        super.onAttach(context);
//        mActivity = (Activity) context;
//    }
//
//    @Override
//    public void onCreate(@Nullable final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        listItems = getArguments().getStringArrayList(AppConstant.LIST);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull final LayoutInflater inflater,
// @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_slider, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        TextView tvCmntUserName = view.findViewById(R.id.tvCmntUserName);
//        TextView tvCmntUserName2 = view.findViewById(R.id.tvCmntUserName2);
//        tvCmntUserName.setText(listItems.get(0));
//        tvCmntUserName2.setText(listItems.get(0));
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mActivity = null;
//    }
//}
