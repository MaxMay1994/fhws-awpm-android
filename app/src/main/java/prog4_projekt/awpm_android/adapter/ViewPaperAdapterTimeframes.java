package prog4_projekt.awpm_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prog4_projekt.awpm_android.fragmente.FragmentTimeframesSw;
import prog4_projekt.awpm_android.fragmente.FragmentTimeframesWue;

/**
 * Created by florianduenow on 17.04.16.
 */
public class ViewPaperAdapterTimeframes extends FragmentPagerAdapter {
    String[] title = {"Würzburg","Schweinfurt"};

    public ViewPaperAdapterTimeframes(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentTimeframesWue();
            case 1: return new FragmentTimeframesSw();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
