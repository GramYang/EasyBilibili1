package com.developer.gram.easybilibili.function.user;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.gram.easybilibili.R;
import com.developer.gram.easybilibili.adapter.UserHomePagerCoinsAdapter;
import com.developer.gram.easybilibili.adapter.UserHomePagerContributeAdapter;
import com.developer.gram.easybilibili.adapter.UserHomePagerInterestQuanAdapter;
import com.developer.gram.easybilibili.adapter.UserHomePagerPlayGameAdapter;
import com.developer.gram.easybilibili.base.LazyFragment;
import com.developer.gram.easybilibili.bean.user.UserCoinsInfo;
import com.developer.gram.easybilibili.bean.user.UserContributeInfo;
import com.developer.gram.easybilibili.bean.user.UserInterestQuanInfo;
import com.developer.gram.easybilibili.bean.user.UserLiveRoomStatusInfo;
import com.developer.gram.easybilibili.bean.user.UserPlayGameInfo;
import com.developer.gram.easybilibili.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Gram on 2017/12/25.
 */

public class UserHomePageFragment extends LazyFragment {
    @BindView(R.id.contribute_count)
    TextView mContributeCount;
    @BindView(R.id.contribute_recycler)
    RecyclerView mContributeRecycler;
    @BindView(R.id.coins_count)
    TextView mCoinsCount;
    @BindView(R.id.coins_recycler)
    RecyclerView mCoinsRecycler;
    @BindView(R.id.quanzi_count)
    TextView mQuanziCount;
    @BindView(R.id.quanzi_recycler)
    RecyclerView mQuanziRecycler;
    @BindView(R.id.play_game_count)
    TextView mPlayGameCount;
    @BindView(R.id.player_game_recycler)
    RecyclerView mPlayerGameRecycler;
    @BindView(R.id.contribute_layout)
    RelativeLayout contributeLayout;
    @BindView(R.id.coins_layout)
    RelativeLayout coinsLayout;
    @BindView(R.id.quanzi_layout)
    RelativeLayout quanziLayout;
    @BindView(R.id.play_game_layout)
    RelativeLayout playGameLayout;
    @BindView(R.id.live_image)
    ImageView liveImage;
    @BindView(R.id.live_status_tv)
    TextView liveStatusTv;
    @BindView(R.id.card_view)
    CardView cardView;

    private UserCoinsInfo mUserCoinsInfo;
    private UserPlayGameInfo mUserPlayGameInfo;
    private UserContributeInfo mUserContributeInfo;
    private UserInterestQuanInfo mUserInterestQuanInfo;
    private UserLiveRoomStatusInfo mUserLiveRoomStatusInfo;

    public static UserHomePageFragment newInstance(UserContributeInfo userContributeInfo,
                                                   UserInterestQuanInfo userInterestQuanInfo,
                                                   UserCoinsInfo userCoinsInfo,
                                                   UserPlayGameInfo userPlayGameInfo,
                                                   UserLiveRoomStatusInfo userLiveRoomStatusInfo) {

        UserHomePageFragment mFragment = new UserHomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.USER_CONTRIBUTE, userContributeInfo);
        bundle.putParcelable(ConstantUtil.USER_INTEREST_QUAN, userInterestQuanInfo);
        bundle.putParcelable(ConstantUtil.USER_COINS, userCoinsInfo);
        bundle.putParcelable(ConstantUtil.USER_PLAY_GAME, userPlayGameInfo);
        bundle.putParcelable(ConstantUtil.USER_LIVE_STATUS, userLiveRoomStatusInfo);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_home_page;
    }

    @Override
    protected void initVariables() {
        mUserContributeInfo = getArguments().getParcelable(ConstantUtil.USER_CONTRIBUTE);
        mUserInterestQuanInfo = getArguments().getParcelable(ConstantUtil.USER_INTEREST_QUAN);
        mUserCoinsInfo = getArguments().getParcelable(ConstantUtil.USER_COINS);
        mUserPlayGameInfo = getArguments().getParcelable(ConstantUtil.USER_PLAY_GAME);
        mUserLiveRoomStatusInfo = getArguments().getParcelable(ConstantUtil.USER_LIVE_STATUS);
    }

    @Override
    protected void initViews() {
        setLive();
        setContribute();
        setCoins();
        setQuanzi();
        setPlayGame();
    }

    @SuppressLint("SetTextI18n")
    private void setLive() {
        int roomStatus = mUserLiveRoomStatusInfo.getData().getRoomStatus();
        if (roomStatus == 0) {
            //用户没有直播
            liveImage.setImageResource(R.drawable.ic_live_line);
            liveImage.setImageTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.font_normal)));
            liveStatusTv.setText(R.string.live_message);
            liveStatusTv.setTextColor(getActivity().getResources().getColor(R.color.font_normal));
        } else {
            //用户正在直播
            liveImage.setImageResource(R.drawable.ic_live_fill);
            liveImage.setImageTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.colorPrimary)));
            liveStatusTv.setText("正在直播 :" + mUserLiveRoomStatusInfo.getData().getTitle());
            liveStatusTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        }
    }

    private void setPlayGame() {
        int count = mUserPlayGameInfo.getData().getCount();
        if (count == 0) {
            gone(playGameLayout);
        }
        List<UserPlayGameInfo.DataBean.GamesBean> games = mUserPlayGameInfo.getData().getGames();
        mPlayerGameRecycler.setHasFixedSize(false);
        mPlayerGameRecycler.setNestedScrollingEnabled(false);
        mPlayerGameRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlayerGameRecycler.setAdapter(new UserHomePagerPlayGameAdapter(mPlayerGameRecycler, games));
        mPlayGameCount.setText(String.valueOf(count));
    }

    private void setQuanzi() {
        int total_count = mUserInterestQuanInfo.getData().getTotal_count();
        if (total_count == 0) {
            gone(quanziLayout);
        }
        List<UserInterestQuanInfo.DataBean.ResultBean> result = mUserInterestQuanInfo.getData().getResult();
        mQuanziRecycler.setHasFixedSize(false);
        mQuanziRecycler.setNestedScrollingEnabled(false);
        mQuanziRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuanziRecycler.setAdapter(new UserHomePagerInterestQuanAdapter(mQuanziRecycler, result));
        mQuanziCount.setText(String.valueOf(total_count));
    }

    private void setCoins() {
        int count = mUserCoinsInfo.getData().getCount();
        if (count == 0) {
            gone(coinsLayout);
        }
        List<UserCoinsInfo.DataBean.ListBean> list = mUserCoinsInfo.getData().getList();
        mCoinsRecycler.setHasFixedSize(false);
        mCoinsRecycler.setNestedScrollingEnabled(false);
        mCoinsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mCoinsRecycler.setAdapter(new UserHomePagerCoinsAdapter(mCoinsRecycler, list));
        mCoinsCount.setText(String.valueOf(count));
    }

    private void setContribute() {
        int count = mUserContributeInfo.getData().getCount();
        if (count == 0) {
            gone(contributeLayout);
        }
        List<UserContributeInfo.DataBean.VlistBean> vlist = mUserContributeInfo.getData().getVlist();
        mContributeRecycler.setHasFixedSize(false);
        mContributeRecycler.setNestedScrollingEnabled(false);
        mContributeRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mContributeRecycler.setAdapter(new UserHomePagerContributeAdapter(mContributeRecycler, vlist));
        mContributeCount.setText(String.valueOf(count));
    }


    @OnClick({R.id.contribute_more, R.id.quanzi_more, R.id.coins_more, R.id.play_game_more})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.contribute_more:
                //更多投稿
                ((UserInfoDetailsActivity) getActivity()).switchPager(1);
                break;
            case R.id.quanzi_more:
                //更多圈子
                ((UserInfoDetailsActivity) getActivity()).switchPager(2);
                break;
            case R.id.coins_more:
                //更多投币
                ((UserInfoDetailsActivity) getActivity()).switchPager(3);
                break;
            case R.id.play_game_more:
                //更多游戏
                ((UserInfoDetailsActivity) getActivity()).switchPager(4);
                break;

        }
    }
}