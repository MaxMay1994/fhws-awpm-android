package prog4_projekt.awpm_android;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by florianduenow on 24.05.16.
 */
public class GCMRegistrationService extends IntentService {
    public GCMRegistrationService(){
        super("GCMRegistrationService");
    }
    String projectNummer;
    String projectID;
    String clientAPIKey;
    String registrationToken;

    @Override
    protected void onHandleIntent(Intent intent) {
        getProjectStringsFromGSJson();

        InstanceID myId = InstanceID.getInstance(this);
        try {
            registrationToken = myId.getToken("", GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Hier kann/muss dann eine Methode hin
        um den Registrierungstoken zum Server zu schicken

        sendRegistrationTokenToServer();
        */
    }
    public void getProjectStringsFromGSJson(){
        String json = null;
        try {
            InputStream is = getAssets().open("google-services.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject reader = new JSONObject(json);
            //liest project_number und project_id aus google-service.json
            //und speichert in entsprechende strings
            JSONObject pojectInfo = reader.getJSONObject("project_info");
            projectNummer = pojectInfo.getString("project_number");
            projectID = pojectInfo.getString("project_id");

            //liest api_key aus google-service.json
            //und speichert in entsprechenden string
            JSONArray client = reader.getJSONArray("client");
            JSONObject clientAtZero = client.getJSONObject(0);
            JSONArray apiKey = clientAtZero.getJSONArray("api_key");
            int i = 0;
            while(i <= apiKey.length()-1) {
                JSONObject apiKeyFromClientObject = apiKey.getJSONObject(i);
                if(apiKeyFromClientObject.has("current_key")){
                    clientAPIKey = apiKeyFromClientObject.getString("current_key");
                    i = i+1;
                }
                else{
                    i = i+1;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
