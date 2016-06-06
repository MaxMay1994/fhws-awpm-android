package prog4_projekt.awpm_android.adapter;

    import android.content.Context;
    import android.support.v7.widget.CardView;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.animation.AlphaAnimation;
    import android.view.animation.Animation;
    import android.view.animation.ScaleAnimation;

    import java.util.List;
    import java.util.Random;

    import prog4_projekt.awpm_android.R;
    import prog4_projekt.awpm_android.RestApi.Module.Building;
    import prog4_projekt.awpm_android.RestApi.Module.Location;
    import prog4_projekt.awpm_android.RestApi.Module.Module;
    import prog4_projekt.awpm_android.RestApi.Module.Room;

/**
     * Created by Ich on 22.04.2016.
     */
    public class RecyclerViewAdapter extends RecyclerView.Adapter<ModuleViewHolder> {
        Context context;
        List<Module> mList;
        private static final int FADE_DURATION = 1000;
    private int lastPosition = -1;
        public RecyclerViewAdapter(Context context){
            this.context = context;
        }

        public RecyclerViewAdapter(Context context, List<Module> mList){
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

            holder.textview1.setText(module.getName());
            String all = "";
            String buildingName = "";
            Room room;
            if((room = module.getRoom())!= null){
                Building building;
                if((building = room.getBuilding())!=null){
                    buildingName = building.getName();
                    Location location;
                    if((location = building.getLocation())!= null){
                        all = location.getName();
                    }
                }
            }
            holder.textview2.setText(module.getTeacher()+ ",  "+ all +", "+buildingName);

            setAnimation(holder.itemView, position);

        }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }


        @Override
        public int getItemCount() {
            if(mList == null)
                return 0;
            else
             return mList.size();
        }

    }




