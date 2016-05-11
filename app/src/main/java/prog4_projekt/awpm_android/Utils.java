package prog4_projekt.awpm_android;

/**
 * Created by Ich on 26.04.2016.
 */
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources) {
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
