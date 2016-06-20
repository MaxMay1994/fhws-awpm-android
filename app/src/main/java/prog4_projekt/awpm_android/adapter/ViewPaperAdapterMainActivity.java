package prog4_projekt.awpm_android.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.MainActivity;
import prog4_projekt.awpm_android.fragmente.FragmentCourses;
import prog4_projekt.awpm_android.fragmente.FragmentProfil;
import prog4_projekt.awpm_android.fragmente.FragmentBallot;

public class ViewPaperAdapterMainActivity extends FragmentPagerAdapter{

    String[] title;
    Fragment fragment0;
    Fragment fragment1;
    Fragment fragment2;

    public ViewPaperAdapterMainActivity(FragmentManager fm,Fragment f0,Fragment f1 ,Fragment f2) {
        super(fm);
        this.fragment0 = f0;
        this.fragment1 = f1;
        this.fragment2 = f2;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 2: return fragment2;
            case 0: return fragment0;
            case 1: return fragment1;
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
    public void setTitleArray(String[] array){
        this.title = array;
    }
    public String[] getTitleArray(){
        return title;
    }
}
