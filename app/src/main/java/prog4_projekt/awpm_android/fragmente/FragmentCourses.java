package prog4_projekt.awpm_android.fragmente;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;

import prog4_projekt.awpm_android.Module;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapter;


public class FragmentCourses extends Fragment{

   private View view;

   RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Module> mList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_courses, null);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), fillList());
        recyclerView.setAdapter(adapter);







    }
    private ArrayList<Module> fillList() {
        mList = new ArrayList<Module>();
        Module m;
        for(int i = 0; i <= 80; i++){
            m = new Module("AWPM #" + i, "Dozent", false);
            mList.add(m);
        }
        return mList;
    }


}
