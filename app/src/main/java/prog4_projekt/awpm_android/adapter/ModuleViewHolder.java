package prog4_projekt.awpm_android.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import prog4_projekt.awpm_android.R;

/**
 * Created by Ich on 22.04.2016.
 */
public class ModuleViewHolder extends RecyclerView.ViewHolder {
    TextView textview1;
    TextView textview2;

    public ModuleViewHolder(final View itemView) {
        super(itemView);
        this.textview1 = (TextView) itemView.findViewById(R.id.cardTitle);
        this.textview2 = (TextView) itemView.findViewById(R.id.cardSubtitle);
    }
}
