package prog4_projekt.awpm_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPaperAdapter extends FragmentPagerAdapter{

    String[] title = {"Infos","Kurse","Profil"};

    public ViewPaperAdapter(FragmentManager fm) {
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
