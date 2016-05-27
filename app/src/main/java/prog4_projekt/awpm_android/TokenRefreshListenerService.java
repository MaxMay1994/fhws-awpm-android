package prog4_projekt.awpm_android;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by florianduenow on 24.05.16.
 */
public class TokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh(){
        Intent intent = new Intent(this, GCMRegistrationService.class);
        startService(intent);
    }
}
