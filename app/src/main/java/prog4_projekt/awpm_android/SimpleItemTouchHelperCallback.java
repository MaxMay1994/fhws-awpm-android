package prog4_projekt.awpm_android;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
import prog4_projekt.awpm_android.activities.MainActivity;
import prog4_projekt.awpm_android.adapter.ItemTouchHelperAdapter;
import prog4_projekt.awpm_android.fragmente.FragmentBallot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maxma on 11.05.2016.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private List<Module> list;
    private static int changedItemId = 0;
    String authorization = "";
    Call<Module> call;

    public SimpleItemTouchHelperCallback(
            ItemTouchHelperAdapter adapter, List<Module> list, String authorization) {
        mAdapter = adapter;
        this.list = list;
        this.authorization = authorization;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        //Log.e("Hallo",String.valueOf(source.getLayoutPosition()));

        changedItemId = target.getAdapterPosition();

        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if(actionState == 0) {
            list.get(changedItemId).setVotePosition(changedItemId+1);

            call = ServiceAdapter.getService().patchVotePosition(list.get(changedItemId).getId(),changedItemId+1,authorization);
            call.enqueue(new Callback<Module>() {
                @Override
                public void onResponse(Call<Module> call, Response<Module> response) {

                    if(response.code() == 200){
                        Log.e("Update","VotedList");
                    }

                }

                @Override
                public void onFailure(Call<Module> call, Throwable t) {
                    Log.e("Update","Error");
                }
            });


        }

    }


}
