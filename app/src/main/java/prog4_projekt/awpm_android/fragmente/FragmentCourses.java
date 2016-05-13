package prog4_projekt.awpm_android.fragmente;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.Module.Room;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
import prog4_projekt.awpm_android.activities.FilterActivity;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCourses extends Fragment{

   private View view;

    RecyclerViewAdapter adapter;
    RecyclerViewAdapter adapter1;
    RecyclerView recyclerView;
    RecyclerView rv;
    public List<Module> modulesList;
    Call<List<Module>> call;
    String content, name, lecturer, start, end, examType, room, examNumber, city, location;
    int participants;
    boolean voted, favorite;
    Button filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_courses, null);
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new RecyclerViewAdapter(getContext());
        rv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filter = (Button) view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        call = ServiceAdapter.getService().getAllModules();
        call.enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                modulesList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {
                //Toast.makeText(getContext(), "Bitte erneut laden", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position){


                        Intent intent = new Intent(view.getContext(), CourseDetailsActivity.class);
                        Module moduleToPass = modulesList.get(position);

                        Bundle extras = new Bundle();
                        name = moduleToPass.getName();
                        content = moduleToPass.getContent();
                        examType = moduleToPass.getExamType();
                        lecturer = moduleToPass.getTeacher();
                        start = moduleToPass.getStart();
                        end = moduleToPass.getEnd();
                        participants = moduleToPass.getParticipants();
                        favorite = moduleToPass.isFavorite();
                        voted = moduleToPass.isVoted();
                        city = moduleToPass.getRoom().getBuilding().getLocation().getName();
                        location = moduleToPass.getRoom().getBuilding().getName();
                        room = moduleToPass.getRoom().getName();
                        examNumber = moduleToPass.getExamNumber();
                        extras.putString("name", name);
                        extras.putString("content", content);
                        extras.putString("examtype", examType);
                        extras.putString("Teacher", lecturer);
                        extras.putString("start", start);
                        extras.putString("end", end);
                        extras.putInt("participants", participants);
                        extras.putBoolean("favorite", favorite);
                        extras.putBoolean("voted", voted);
                        extras.putString("city", city);
                        extras.putString("location", location);
                        extras.putString("room", room);
                        extras.putString("examnumber", examNumber);
                        intent.putExtras(extras);
                        view.getContext().startActivity(intent);
                    }
                })
        );
    }

}
