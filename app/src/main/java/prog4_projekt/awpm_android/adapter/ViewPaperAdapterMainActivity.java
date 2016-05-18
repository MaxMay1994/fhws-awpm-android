package prog4_projekt.awpm_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prog4_projekt.awpm_android.fragmente.FragmentCourses;
import prog4_projekt.awpm_android.fragmente.FragmentProfil;
import prog4_projekt.awpm_android.fragmente.FragmentBallot;

public class ViewPaperAdapterMainActivity extends FragmentPagerAdapter{

    String[] title = {"Katalog","Wahlzettel","Info`s"};

    public ViewPaperAdapterMainActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 2: return new FragmentProfil();
            case 0: return new FragmentCourses();
            case 1: return new FragmentBallot();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
