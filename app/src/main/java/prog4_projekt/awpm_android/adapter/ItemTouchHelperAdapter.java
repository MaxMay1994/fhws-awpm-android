package prog4_projekt.awpm_android.adapter;

/**
 * Created by maxma on 11.05.2016.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
