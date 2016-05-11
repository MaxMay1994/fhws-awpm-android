package prog4_projekt.awpm_android.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.CourseDetailsActivity;

/**
 * Created by Ich on 22.04.2016.
 */
public class ModuleViewHolder extends RecyclerView.ViewHolder {
    TextView textview1;
    TextView textview2;


    public ModuleViewHolder(View itemView) {
        super(itemView);
        this.textview1 = (TextView) itemView.findViewById(R.id.subject);
        this.textview2 = (TextView) itemView.findViewById(R.id.lecturer);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CourseDetailsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
