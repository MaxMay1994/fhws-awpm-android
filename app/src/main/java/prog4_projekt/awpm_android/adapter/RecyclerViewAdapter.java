package prog4_projekt.awpm_android.adapter;

    import android.content.Context;
    import android.content.Intent;
    import android.support.v7.widget.CardView;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import java.util.ArrayList;
    import java.util.List;

    import prog4_projekt.awpm_android.Module;
    import prog4_projekt.awpm_android.R;
    import prog4_projekt.awpm_android.activities.CourseDetailsActivity;
    import prog4_projekt.awpm_android.fragmente.FragmentCourses;

/**
     * Created by Ich on 22.04.2016.
     */
    public class RecyclerViewAdapter extends RecyclerView.Adapter<ModuleViewHolder> {
        Context context;
        List<Module> mList;
    LayoutInflater inflater;

        public RecyclerViewAdapter(Context context, ArrayList<Module> mList){
            this.context = context;
            this.mList = mList;
        }
        @Override
        public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
            view.setLayoutParams(lp);
            ModuleViewHolder moduleViewHolder = new ModuleViewHolder(view);
            return moduleViewHolder;
        }

        @Override
        public void onBindViewHolder(ModuleViewHolder holder, int position) {
            Module module = mList.get(position);
            holder.textview1.setText(String.valueOf(module.getSubject()));
            holder.textview2.setText(String.valueOf(module.getLecturer()));

        }

        @Override
        public int getItemCount() {
            if(mList == null)return 0;
            else return mList.size();
        }
    }


