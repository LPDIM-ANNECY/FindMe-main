package fr.test200.findme.onBoarding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    // 1 - Array of colors that will be passed to PageFragment


    // 2 - Default Constructor
    public PageAdapter(FragmentManager mgr ) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        // 4 - Page to return
        return(OnBoardingViewFragment.PageFragment.Companion.newInstance(position));
    }



}

