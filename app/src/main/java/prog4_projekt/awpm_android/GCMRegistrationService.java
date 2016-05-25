package prog4_projekt.awpm_android;


import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by florianduenow on 24.05.16.
 */
public class GCMRegistrationService extends IntentService {
    public GCMRegistrationService(){
        super("GCMRegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myId = InstanceID.getInstance(this);
        try {
            String registrationToken = myId.getToken("string = SenderID", GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        Hier kann/muss dann eine Methode hin
        um den Registrierungstoken zum Server zu schicken

        sendRegistrationTokenToServer();
        */
    }
}
