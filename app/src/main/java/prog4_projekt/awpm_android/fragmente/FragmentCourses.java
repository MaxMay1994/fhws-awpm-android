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
import android.widget.Toast;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Modules.Modules;
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
    RecyclerView recyclerView;
    public List<Modules> modulesList;
    Call<List<Modules>> call;
    String string;
    String title;

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
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position){
                        Intent intent = new Intent(view.getContext(), CourseDetailsActivity.class);
                        Bundle extras = new Bundle();
                        title = modulesList.get(position).getName();
                        string = modulesList.get(position).getContent();
                        extras.putString("Titel", title);
                        extras.putString("Informationen", string);
                       // Log.i("MSG:", string);
                        //Log.i("Titel:", title);
                       // intent.putExtra("Title", title);
                        //intent.putExtra("Informationen", string);
                        intent.putExtras(extras);
                        view.getContext().startActivity(intent);
                    }
                })
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        call = ServiceAdapter.getService().getAllModules();
         call.enqueue(new Callback<List<Modules>>() {
            @Override
            public void onResponse(Call<List<Modules>> call, Response<List<Modules>> response) {
                modulesList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Modules>> call, Throwable t) {
                Toast.makeText(getContext(), "Bitte erneut laden", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
