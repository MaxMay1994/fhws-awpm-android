package prog4_projekt.awpm_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Module.Module;

/**
 * Created by maxma on 10.05.2016.
 */
public class RecyclerViewAdapterBallot extends RecyclerView.Adapter<ModuleViewHolderBallot> implements ItemTouchHelperAdapter {
    Context context;
    List<Module> mList;
    LayoutInflater inflater;
    RecyclerView rv;
    public RecyclerViewAdapterBallot(Context context){
        this.context = context;
    }

    public RecyclerViewAdapterBallot(Context context, List<Module> mList){
        this.context = context;
        this.mList = mList;
    }
    @Override
    public ModuleViewHolderBallot onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_ballot, parent, false);
        view.setLayoutParams(lp);
        ModuleViewHolderBallot moduleViewHolderBallot = new ModuleViewHolderBallot(view);
        return moduleViewHolderBallot;
    }

    @Override
    public void onBindViewHolder(ModuleViewHolderBallot holder, int position) {
        Module module = mList.get(position);
        holder.textview1.setText(module.getName());
        holder.textview2.setText(module.getTeacher());

    }

    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;
        else
            return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        String line;
        line = "form: " + fromPosition+" to: " + toPosition;
        Log.e("REORDER",line);
        return true;

    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
}
