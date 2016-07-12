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
import android.support.v4.widget.NestedScrollView;
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
    public List<Module> modulesList = null;
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
    NestedScrollView scrollView;
    int page;
    int perPage;
    String header;
    Intent intent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_courses, null);
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new RecyclerViewAdapter(getContext());
        rv.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        scrollView = (NestedScrollView) view.findViewById(R.id.courses_scrollview);
        return view;
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        page = 1;
        perPage = 10;
        header = null;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);
        filter = (Button) view.findViewById(R.id.filter);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        //Intent returnIntent = new Intent();
        intent = new Intent();
        intent.putExtra("wahlZeitraumID",(String)null );
        intent.putExtra("locationID", (Integer)null);
        intent.putExtra("blockedForMe", (String)null);
        intent.putExtra("blockedForAll", (Integer)null);
        intent.putExtra("favoredModulesID", (String)null);

        onActivityResult(1,Activity.RESULT_OK,intent);


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








        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(!v.canScrollVertically(1)&& isNextPage(header)){
                    Log.e("header",header);
                    setPage(header);

                    String wahl = intent.getStringExtra("wahlZeitraumID");
                    String favorit = intent.getStringExtra("favoredModulesID");
                    String blockedForMe = intent.getStringExtra("blockedForMe");
                    Integer locationid = intent.getIntExtra("locationID", 0);
                    Log.i("location im Fragment", String.valueOf(locationid));
                    Integer blockedForAll = intent.getIntExtra("blockedForAll", 0);
                    Log.i("block im Fragment", String.valueOf(blockedForAll));



                    call = ServiceAdapter.getService().getAll(
                            null,
                            ((wahl != null) && (wahl.equalsIgnoreCase("wahlzeitraum"))) ? true : null,
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
                            page,
                            perPage,
                            authorization);

                    call.enqueue(new Callback<List<Module>>() {
                        @Override
                        public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                            header = response.headers().get("Link");
                            modulesList.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<Module>> call, Throwable t) {

                        }
                    });

                }
            }
        });














    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                intent = data;
                String wahl = data.getStringExtra("wahlZeitraumID");
                String favorit = data.getStringExtra("favoredModulesID");
                String blockedForMe = data.getStringExtra("blockedForMe");
                Integer locationid = data.getIntExtra("locationID", 0);
                Log.i("location im Fragment", String.valueOf(locationid));
                Integer blockedForAll = data.getIntExtra("blockedForAll", 0);
                Log.i("block im Fragment", String.valueOf(blockedForAll));


                call = ServiceAdapter.getService().getAll(
                        null,
                        ((wahl != null) && (wahl.equalsIgnoreCase("wahlzeitraum"))) ? true : null,
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
                        1,
                        10,
                        authorization);

                Log.e("response",call.request().toString());
                call.enqueue(new Callback<List<Module>>() {
                    @Override
                    public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                        header = response.headers().get("Link");

                        //if(modulesList == null) {
                            //Log.e("Hallo","Test");
                            modulesList = response.body();
                            adapter = new RecyclerViewAdapter(getActivity(), modulesList);
                            recyclerView.setAdapter(adapter);
                        //} else {
                        //    modulesList.addAll(response.body());
                        //}

                        adapter.notifyDataSetChanged();
                        //Log.e("header",header);
                        //Log.e("response", modulesList.toString());
                    }

                    @Override
                    public void onFailure(Call<List<Module>> call, Throwable t) {

                    }
                });

            }
        }

        /*scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(!v.canScrollVertically(1)&& isNextPage(header)){
                    setPage(header);
                    Log.e("header",header);
                    String wahl = intent.getStringExtra("wahlZeitraumID");
                    String favorit = intent.getStringExtra("favoredModulesID");
                    String blockedForMe = intent.getStringExtra("blockedForMe");
                    Integer locationid = intent.getIntExtra("locationID", 0);
                    Log.i("location im Fragment", String.valueOf(locationid));
                    Integer blockedForAll = intent.getIntExtra("blockedForAll", 0);
                    Log.i("block im Fragment", String.valueOf(blockedForAll));


                    call = ServiceAdapter.getService().getAll(
                            null,
                            ((wahl != null) && (wahl.equalsIgnoreCase("wahlzeitraum"))) ? true : null,
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
                            page,
                            perPage,
                            authorization);
                    call.enqueue(new Callback<List<Module>>() {
                        @Override
                        public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                            modulesList.addAll(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<Module>> call, Throwable t) {

                        }
                    });

                }
            }
        });*/
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
        return newList;
    }
    private boolean isNextPage(String line){
        if(line.contains("next"))
            return true;
        else return false;
    }

    private void setPage(String line){
        Log.e("line",line);
        String[] headers = line.split(",");
        String nextHeader = null;
        for(int i = 0; i < headers.length; i++){
            if(headers[i].contains("next")){
                nextHeader = headers[i].substring(headers[i].indexOf('<') + 1, headers[i].indexOf('>'));
                break;
            }
        }
        if(nextHeader != null){
            String[] nextHeaderParts = nextHeader.split("&");
            nextHeaderParts[0] = nextHeaderParts[0].split("\\?")[1];
            String perPageLine = "";
            String pageLine = "";

            for(int i = 0; i < nextHeaderParts.length; i++){
                if(nextHeaderParts[i].contains("per_page")){
                    perPageLine = nextHeaderParts[i];
                }
                if(nextHeaderParts[i].contains("page") && !nextHeaderParts[i].contains("per_page")){
                    pageLine = nextHeaderParts[i];
                }
            }

                perPage = Integer.parseInt(perPageLine.split("per_page=")[1]);
                page = Integer.parseInt(pageLine.split("page=")[1]);

        }
        Log.e(String.valueOf(perPage),String.valueOf(page));
    }

}
