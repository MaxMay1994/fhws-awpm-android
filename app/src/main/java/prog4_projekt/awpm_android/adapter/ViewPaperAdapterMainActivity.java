package prog4_projekt.awpm_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prog4_projekt.awpm_android.fragmente.FragmentCourses;
import prog4_projekt.awpm_android.fragmente.FragmentInformations;
import prog4_projekt.awpm_android.fragmente.FragmentProfile;

public class ViewPaperAdapterMainActivity extends FragmentPagerAdapter{

    String[] title = {"Infos","Kurse","Profil"};

    public ViewPaperAdapterMainActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentInformations();
            case 1: return new FragmentCourses();
            case 2: return new FragmentProfile();
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
