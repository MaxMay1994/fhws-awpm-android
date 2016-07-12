package prog4_projekt.awpm_android.fragmente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rjung.util.Gravatar;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

import java.util.List;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.SimpleItemTouchHelperCallback;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapter;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapterBallot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBallot extends Fragment {

    View view;
    RecyclerView recyclerViewC, recyclerViewF;
    RecyclerViewAdapterBallot adapter;
    RecyclerViewAdapter adapter2;
    List<Module> votedList, favoriteList;
    Call<List<Module>> call, call2;
    TextView nullVoted;
    TextView sad;
    TextView textViewBallot;
    TextView textViewFavorite;
    SharedPreferences sharedPref;
    String content, name, lecturer, start, end, examType, room, examNumber, city, location;
    int participants, id, votes;
    boolean voted, favorite;
    ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ballot, null);
        nullVoted = (TextView) view.findViewById(R.id.textView_null_voted);
        sad = (TextView) view.findViewById(R.id.textView_sad);
        imageView = (ImageView) view.findViewById(R.id.imageReorder);
        textViewBallot = (TextView) view.findViewById(R.id.textViewBallot);
        textViewFavorite = (TextView) view.findViewById(R.id.textViewFavorite);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);

        recyclerViewC = (RecyclerView) view.findViewById(R.id.recyclerViewC);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewC.setNestedScrollingEnabled(false);

        recyclerViewF = (RecyclerView) view.findViewById(R.id.recyclerViewF);
        recyclerViewF.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewF.setNestedScrollingEnabled(false);

        call = ServiceAdapter.getService().getVotedModules(true, authorization);
        call.enqueue(new Callback<List<Module>>() {

            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {

                if (response.code() == 200) {

                    votedList = response.body();
                    adapter = new RecyclerViewAdapterBallot(getActivity(), votedList);
                    recyclerViewC.setAdapter(adapter);
                    ItemTouchHelper.Callback callback =
                            new SimpleItemTouchHelperCallback(adapter, votedList, authorization);
                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerViewC);


                    if (response.body().size() > 0) {
                        textViewBallot.setVisibility(View.VISIBLE);
                    }

                    if (response.body().size() == 0) {

                        textViewBallot = nullVoted;
                        textViewBallot.setVisibility(View.VISIBLE);
                        sad.setVisibility(View.VISIBLE);

                    }

                }
                if (response.code() == 401) {


                }
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });
        call2 = ServiceAdapter.getService().getAll(null, null, null, null, null, null, true, false, null, null, null, null, null, null, 1, 50, authorization);
        call2.enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {

                favoriteList = response.body();
                adapter2 = new RecyclerViewAdapter(getActivity(), favoriteList);
                recyclerViewF.setAdapter(adapter2);

                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        textViewFavorite.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });

        recyclerViewC.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        String authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);

                        Call<Module> call2 = ServiceAdapter.getService().getAuthoristModule(votedList.get(position).getId(), authorization);
                        call2.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {

                                Intent intent = new Intent(FragmentBallot.this.getContext(), CourseDetailsActivity.class);
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
                                extras.putInt("votes", votes);
                                intent.putExtras(extras);
                                FragmentBallot.this.getContext().startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {

                            }
                        });
                    }
                })
        );


    }

}
