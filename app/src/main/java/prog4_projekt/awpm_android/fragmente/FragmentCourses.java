package prog4_projekt.awpm_android.fragmente;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.RestApi.Module.Module;
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
    int participants, id, cityidSW, cityidWUE, locationIDSHL, locationIDMstr, subjectAreaIDBIN, subjectAreaIDBWI;
    boolean voted, favorite;
    Button filter;
    SharedPreferences sharedPref;
    private String authorization;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String[] dataFromFilter = data.getStringArrayExtra("returnData");
                for (String s : dataFromFilter){
                    Log.i("Filteruebergabe", s);
                    if(s.equals("wahlZeitraum")){
                        call = ServiceAdapter.getService().getActiveModules(true);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {
                            }
                        });
                    }
 /* funktioniert ab hier noch nicht if (s.equals("Schweinfurt")){
                        call = ServiceAdapter.getService().getModulesAtLocationID(getIDFor("Schweinfurt"));
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {
                            }
                        });
                    }
                    if(s.equals("Würzburg")){
                        call = ServiceAdapter.getService().getModulesAtLocationID(getIDFor("Würzburg"));
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {
                            }
                        });
                    }
                    if(s.equals("SHL")){
                        call = ServiceAdapter.getService().getModulesAtBuildingID(getIDFor("SHL"));
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {
                            }
                        });
                    }
                    if (s.equals("Münzstr.")){
                        call = ServiceAdapter.getService().getModulesAtBuildingID(getIDFor("Münzstr."));
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {
                            }
                        });
                    }*/ // blockedFor "mich" funktioniert
                    if (s.equals("mich")){
                        call = ServiceAdapter.getService().getBlockedModules(true, authorization);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {

                            }
                        });
                    }
                 /*   if(s.equals("BIN")){
                        call = ServiceAdapter.getService().getNotBlockedFor(subjectAreaIDBIN);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {

                            }
                        });
                    }
                    if(s.equals("BWI")){
                        call = ServiceAdapter.getService().getNotBlockedFor(subjectAreaIDBWI);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList=response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {

                            }
                        });
                    }*/ //funktioniert erst wieder ab favorisierte Module

                    if(s.equals("favoredModules")){
                        call = ServiceAdapter.getService().getFavoredModules(true, authorization);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {

                            }
                        });
                    }/*else{
                        call = ServiceAdapter.getService().getAllModules(1, 50);
                        call.enqueue(new Callback<List<Module>>() {
                            @Override
                            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                                modulesList = response.body();
                                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<List<Module>> call, Throwable t) {

                            }
                        });
                    }*/
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);
        filter = (Button) view.findViewById(R.id.filter);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        call = ServiceAdapter.getService().getAllModules(1,50);
        call.enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                modulesList = response.body();
                adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {
                //Toast.makeText(getContext(), "Bitte erneut laden", Toast.LENGTH_LONG).show();
            }
        });
/*        for(Module mod:modulesList){
            if(mod.getBlockedFor().get(0).getName().equals("BIN"))
                subjectAreaIDBIN = mod.getBlockedFor().get(0).getId();
            else subjectAreaIDBIN = mod.getBlockedFor().get(1).getId();
        }
        for(Module m:modulesList){
            if(m.getBlockedFor().get(0).getName().equals("BWI")) subjectAreaIDBWI = m.getBlockedFor().get(0).getId();
            else subjectAreaIDBWI = m.getBlockedFor().get(1).getId();
        }*/
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position){
                        Call<Module> call2 = ServiceAdapter.getService().getAuthoristModule(modulesList.get(position).getId(),authorization);
                        call2.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Intent intent = new Intent(FragmentCourses.this.getContext(), CourseDetailsActivity.class);
                                Module moduleToPass = response.body();
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
                                id = moduleToPass.getId();
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
                                extras.putInt("id", id);
                                intent.putExtras(extras);
                                FragmentCourses.this.getContext().startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                            }
                        });
                    }
                })
        );
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    private int getIDFor(String location){
        for(Module m:modulesList){
            if(m.getRoom().getBuilding().getLocation().getName().equals("Schweinfurt") && location.equals("Schweinfurt")){
                cityidSW = m.getRoom().getBuilding().getLocation().getId();
                return cityidSW;
            }
            else if(m.getRoom().getBuilding().getLocation().getName().equals("Würzburg")&& location.equals("Würzburg")){
                cityidWUE = m.getRoom().getBuilding().getLocation().getId();
                return cityidWUE;
            }
            else if(m.getRoom().getBuilding().getName().equals("SHL") && location.equals("SHL")){
                locationIDSHL = m.getRoom().getBuilding().getId();
                return locationIDSHL;
            }
            else if(m.getRoom().getBuilding().getName().equals("Münzstr.") && location.equals("Münzstr.")){
                locationIDMstr = m.getRoom().getBuilding().getId();
                return locationIDMstr;
            }
        }
        return 0;
    }
}
