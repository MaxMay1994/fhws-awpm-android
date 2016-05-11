package prog4_projekt.awpm_android.fragmente;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCourses extends Fragment{

   private View view;

    RecyclerViewAdapter adapter;
    //RecyclerViewAdapter adapter1;
    RecyclerView recyclerView;
    //RecyclerView rv;
    public List<Module> moduleList;
    Call<List<Module>> call;
    String string;
    String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_courses, null);
        //rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        //rv.setLayoutManager(new LinearLayoutManager(getContext()));
       // adapter1 = new RecyclerViewAdapter(getContext());
        //rv.setAdapter(adapter1);
        //adapter1.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        call = ServiceAdapter.getService().getAllModules();
        call.enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                moduleList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), moduleList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {
                Log.d("Failure", t.toString());
            }
        });
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(view.getContext(), CourseDetailsActivity.class);
                        Bundle extras = new Bundle();
                        title = moduleList.get(position).getName();
                        string = moduleList.get(position).getContent();
                        extras.putString("Titel", title);
                        extras.putString("Informationen", string);
                        intent.putExtras(extras);
                        view.getContext().startActivity(intent);
                    }
                })
        );
    }

}
