package com.example.bus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private ArrayList<Fragment> Fragments;

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity,ArrayList list) {
        super(fragmentActivity);
        this.Fragments=list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return Fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return Fragments.size();
    }
}
