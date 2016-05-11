package prog4_projekt.awpm_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import prog4_projekt.awpm_android.R;

/**
 * Created by maxma on 10.05.2016.
 */
public class ModuleViewHolderBallot extends RecyclerView.ViewHolder {
    TextView textview1;
    TextView textview2;


    public ModuleViewHolderBallot(final View itemView) {
        super(itemView);
        this.textview1 = (TextView) itemView.findViewById(R.id.cardTitle_ballot);
        this.textview2 = (TextView) itemView.findViewById(R.id.cardSubtitle_ballot);


    }
}
