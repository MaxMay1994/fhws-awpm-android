package prog4_projekt.awpm_android.fragmente;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.RestApi.Module.Building;
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
    RecyclerViewAdapter adapter, adapter1;
    RecyclerView recyclerView, rv;
    public List<Module> modulesList;
    Call<List<Module>> call;
    String[] areasFinal;
    Call<List<Building>> callBuildings, callSubjects;
    String content, name, lecturer, start, end, examType, room, examNumber, city, location;
    int participants, id, cityidSW, cityidWUE, locationIDSHL, locationIDMstr;
    boolean voted, favorite, appearance, blocked;
    Button filter;
    SharedPreferences sharedPref;
    private String authorization;
    private List<Building> buildingList, areaList;
    FragmentLoginDialog dialog;
    LinearLayoutManager mLayoutManager;
    List<Module> hiddenList;

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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);
        filter = (Button) view.findViewById(R.id.filter);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
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
                //Toast.makeText(getContext(), "Bitte erneut laden", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, final int position){
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
                                blocked = moduleToPass.isBlocked();
                                int votes = moduleToPass.getVoted();
                                boolean mandatory = moduleToPass.isMandatory();
                                extras.putInt("votes", votes);
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
                                extras.putBoolean("blocked", blocked);
                                extras.putBoolean("appearance", mandatory);
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
                intent.putExtra("areas", areasFinal);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String wahl = data.getStringExtra("wahlZeitraumID");
                String favorit = data.getStringExtra("favoredModulesID");
                String blockedForMe = data.getStringExtra("blockedForMe");
                int locationid = data.getIntExtra("locationID", 1);
                Log.i("location im Fragment", String.valueOf(locationid));
                int blockedForAll = data.getIntExtra("blockedForAll", 1);
                Log.i("block im Fragment", String.valueOf(blockedForAll));


                call = ServiceAdapter.getService().getAll(
                        null,
                        ((wahl != null) && (wahl.equalsIgnoreCase("wahlzeitraum")))? true : null,
                        null,
                        ((blockedForMe != null) && (blockedForMe.equalsIgnoreCase("mich"))) ? false : null,
                        (blockedForAll != 0) ? blockedForAll : null,
                        null,
                        ((favorit != null) && (favorit.equalsIgnoreCase("favorites"))) ? true : null,
                        null,
                        null,
                        null,
                        null,
                        (locationid != 0) ? locationid : null,
                        null,
                        null,
                        authorization);

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
              /*  if ((dataFromFilter[0].toLowerCase().equals("null".toLowerCase()))&& (dataFromFilter[1].toLowerCase().equals("SHL".toLowerCase())) &&(dataFromFilter[2].toLowerCase().equals("null".toLowerCase()))&& (dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase()))) {
                    call = ServiceAdapter.getService().getAll(null, null, null, null, null, null, true, null, null, null, null, getIDFor("SHL"), null, null, authorization);
                    call.enqueue(new Callback<List<Module>>() {
                        @Override
                        public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                            modulesList = response.body();
                            adapter1 = new RecyclerViewAdapter(getActivity(), modulesList);
                            recyclerView.setAdapter(adapter1);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<Module>> call, Throwable t) {

                        }
                    });
                    //hiddenList = createList(modulesList, "SHL");
                    //adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                    //recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                }
                //Favoriten und Standort Münzstr.
                if (dataFromFilter[1].toLowerCase().equals("Münzstr.".toLowerCase()) && dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase())) {
                    call = ServiceAdapter.getService().getAll(null, null, null, null, null, null, true, null, null, null, null, getIDFor("Münzstr."), null, null, authorization);
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
                    //hiddenList = createList(modulesList, "Münzstr.");
                    //adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                    //recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                }
                //Favoriten und Standort Würzburg
                if (dataFromFilter[1].toLowerCase().equals("Würzburg".toLowerCase()) && dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase())) {
                    call = ServiceAdapter.getService().getAll(null, null, null, null, null, null, true, null, null, null, null, getIDFor("Würzburg"), null, null, authorization);
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
                //Favoriten und Standort Schweinfurt
                if (dataFromFilter[1].toLowerCase().equals("Schweinfurt".toLowerCase()) && dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase())) {
                    call = ServiceAdapter.getService().getAll(null, null, null, null, null, null, true, null, null, null, null, getIDFor("Schweinfurt"), null, null, authorization);
                    call.enqueue(new Callback<List<Module>>() {
                        @Override
                        public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                            modulesList  = response.body();
                            adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<Module>> call, Throwable t) {

                        }
                    });
                }
                //Wahlzeitraum und Favoriten
                if (dataFromFilter[0].toLowerCase().equals("wahlZeitraum".toLowerCase()) && dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase())) {
                    call = ServiceAdapter.getService().getAll(null, true, null, null, null, null, true, null, null, null, null, null, null,null, authorization);
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
                //aktiver Wahlzeitraum
                if (dataFromFilter[0].toLowerCase().equals("wahlZeitraum".toLowerCase())) {
                    call = ServiceAdapter.getService().getAll(null, true, null,null,null,null,null,null,null,null,null,null,null,null,null);
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
                // if (s.toLowerCase().equals("Schweinfurt".toLowerCase())){
                if (dataFromFilter[1].toLowerCase().equals("Schweinfurt".toLowerCase())) {
                    call = ServiceAdapter.getService().getModulesAtLocationID(getIDFor("Schweinfurt"));
                    call.enqueue(new Callback<List<Module>>() {
                        @Override
                        public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                            modulesList = response.body();
                            Log.i("IDSW:", String.valueOf(cityidSW));
                            adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<Module>> call, Throwable t) {
                        }
                    });
                }
                // if(s.toLowerCase().equals("Würzburg".toLowerCase())){
                if (dataFromFilter[1].toLowerCase().equals("Würzburg".toLowerCase())) {
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
                //if(s.toLowerCase().equals("SHL".toLowerCase())){
                if (dataFromFilter[1].toLowerCase().equals("SHL".toLowerCase())) {
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
                //if (s.toLowerCase().equals("Münzstr.".toLowerCase())){
                if (dataFromFilter[1].toLowerCase().equals("Münzstr.".toLowerCase())) {
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
                }
                //if (s.toLowerCase().equals("mich".toLowerCase())){
                if (dataFromFilter[1].toLowerCase().equals("mich".toLowerCase())) {
                    if (MySharedPreference.getBooleanIsLoged(sharedPref)) {
                        call = ServiceAdapter.getService().getBlockedModules(false, authorization);
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
                    } else {
                        dialog = new FragmentLoginDialog();
                        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.transperantDialog);
                        dialog.show(getFragmentManager(), "log");
                    }
                }
                // if(s.toLowerCase().equals("BIN".toLowerCase())){
                if (dataFromFilter[2].toLowerCase().equals("BIN".toLowerCase())) {
                    call = ServiceAdapter.getService().getNotBlockedFor(1);
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
                //if(s.toLowerCase().equals("BWI".toLowerCase())){
                if (dataFromFilter[2].toLowerCase().equals("BWI".toLowerCase())) {
                    call = ServiceAdapter.getService().getNotBlockedFor(2);
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

                //if(s.toLowerCase().equals("favoredModules".toLowerCase())){
                if (dataFromFilter[3].toLowerCase().equals("favoredModules".toLowerCase())) {
                    if (MySharedPreference.getBooleanIsLoged(sharedPref)) {
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
                    } else {
                        dialog = new FragmentLoginDialog();
                        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.transperantDialog);
                        dialog.show(getFragmentManager(), "log");
                    }
                }*/
            }


            if (resultCode == Activity.RESULT_CANCELED) {
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

            }
        }
    }

    private int getIDFor(String location){
        for(Module m:modulesList){
            if(m.getRoom().getBuilding().getLocation().getName().equalsIgnoreCase("Schweinfurt") && location.equalsIgnoreCase("Schweinfurt")){
                cityidSW = m.getRoom().getBuilding().getLocation().getId();
                return cityidSW;
            }
            else if(m.getRoom().getBuilding().getLocation().getName().equalsIgnoreCase("Würzburg")&& location.equalsIgnoreCase("Würzburg")){
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
    private List<Module> createList(List<Module> list, String location) {
        Log.d("Geht in createlist", "ja");
        List<Module> newList = new ArrayList<Module>();

        if (location.toLowerCase().equals("SHL")) {

            for (Module m : list) {
                if ((m.isFavorite()) && (m.getRoom().getBuilding().getName().toLowerCase().equals("SHL".toLowerCase())))
                    newList.add(m);
            }
        }
      //  if (location.toLowerCase().equals("Münzstr.")){
        //    for(Module m : list) {
          //      if((m.isFavorite()) && (m.getRoom().getBuilding().getName().toLowerCase().equals("Münzstr.".toLowerCase())))
            //        newList.add(m);
            //}
        //}
        return newList;
    }

}
