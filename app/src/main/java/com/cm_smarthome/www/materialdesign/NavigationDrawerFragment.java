package com.cm_smarthome.www.materialdesign;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {

    UserData u1 = new UserData();

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DEAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private VivzAdapter adapter;

    private boolean mUserLearnedDrawer;
    private boolean mFromSaveInstanceState;
    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreference(getActivity(), KEY_USER_LEARNED_DEAWER, "false"));

        if (savedInstanceState != null) {
            mFromSaveInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        MainActivity activity = (MainActivity) getActivity();
        String username = activity.getUsernameToFragment();
        String name = activity.getNameToFragment();
        String email = activity.getEmailToFragment();

        TextView tvUsername = (TextView) layout.findViewById(R.id.tvUsername);
        TextView tvName = (TextView) layout.findViewById(R.id.tvName);
        TextView tvEmail = (TextView) layout.findViewById(R.id.tvEmail);

        tvUsername.setText("รหัสนิสิต : "+username);
        tvName.setText(name);
        tvEmail.setText(email);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new VivzAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<InformationSet> getData() {
        List<InformationSet> data = new ArrayList<>();
        int[] icon = {R.drawable.study_icon, R.drawable.test_icon, R.drawable.activity_icon, R.drawable.historygrade_icon
                , R.drawable.caculator_icon, R.drawable.glow_icon, R.drawable.place_icon, R.drawable.chart_icon};
        String[] titles = {"ตารางเรียน", "ตารางสอบ", "ชั่วโมงกิจกรรม", "เกรดที่ผ่านมา", "ทดลองคำนวณเกรด"
                , "กราฟชีวิต", "ติดต่อเรา", "เกี่ยวกับแอพ"};

        for (int i = 0; i < titles.length && i < icon.length; i++) {
            InformationSet current = new InformationSet();
            current.iconId = icon[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getView().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DEAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset / 2);
                }
            }
        };

        if (!mUserLearnedDrawer && !mFromSaveInstanceState) {
            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String perferenceName, String perferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(perferenceName, perferenceValue);
        editor.apply();
    }

    public static String readFromPreference(Context context, String perferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(perferenceName, defaultValue);
    }
}