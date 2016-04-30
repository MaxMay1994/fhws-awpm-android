package prog4_projekt.awpm_android.RestApi;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Modules.Building;
import prog4_projekt.awpm_android.RestApi.Modules.Date;
import prog4_projekt.awpm_android.RestApi.Modules.Modules;
import prog4_projekt.awpm_android.RestApi.Modules.Tag;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AwpmApi {

    //Get

    //Alle Module und ein ein Modul
    @GET("api/modules")Call<List<Modules>> getAllModules(); // für pagination @Path("page") int page
    @GET("api/modules/{id}")Call<Modules> getModule(@Path("id") int id);

    //Alle Locations und eine Location
    @GET("api/locations")Call<List<Building>> getAllLocations();
    @GET("api/locations/{id}")Call<Building> getLocation(@Path("id") int id);

    //Alle Subject Areas und ein Subject Area
    @GET("api/subject-areas")Call<List<Building>> getAllSubjectAreas();
    @GET("api/subject-areas/{id}")Call<Building> getSubjectAreas(@Path("id") int id);

    //Alle Tags und ein Tag z.B. Sprache oder Technik
    @GET("api/tags")Call<List<Tag>> getAllTags();
    @GET("api/tags/{id}")Call<Tag> getTag(@Path("id") int id);

    //Dates
    @GET("api/dates")Call<List<Date>> getAllDates();
    @GET("api/dates/{id}")Call<Date> getDate(@Path("id") int id);


    //Periods


    //Filter

    //true: nur aktive | false: auch welche, die in der Zukunft liegen | default: true
    @GET("api/modules")Call<List<Modules>>
    getActiveModules(@Query("active") boolean active);

    //true: alle gesperrten Module für den aktuellen eingeloggten Benutzer | false: keine gesperrte Module | default: alle Module gemischt
    @GET("api/modules")Call<List<Modules>>
    getBlockedModules(@Query("blocked") boolean blocked, @Header("Authorization") String authorization);

    //alle Module, die nicht für den Studiengang mit der ID not_blocked_for gesperrt sind
    @GET("api/subject-areas")Call<List<Modules>>
    getNotBlockedFor(@Query("not_blocked_for") int subjectAreaID);

    //true: nur Intensivkurse anzeigen | false: keine Intensivkurse (sondern alle semesterbegeleitende) | default: beide Typen gemischt
    @GET("api/modules")Call<List<Modules>>
    getIntensiveModules(@Query("intensive") boolean intensive);

    //true: nur Favoriten | false: alle, die nicht favorisiert sind | default: alle
    @GET("api/modules")Call<List<Modules>>
    getFavoredModules(@Query("favorid") boolean favorid, @Header("Authorization") String authorization);

    //true: nur gewählte Module | false: alle, die nicht gewählt sind | default: alle
    @GET("api/modules")Call<List<Modules>>
    getVotedModules(@Query("voted") boolean voted, @Header("Authorization") String authorization);

    //true: nur Module, für die der Benutzer akzeptiert wurde | false: alle Module, für die der Benutzer nicht akzeptiert wurde | default: alle
    @GET("api/modules")Call<List<Modules>>
    getAcceptedModules(@Query("accepted") boolean accepted, @Header("Authorization") String authorization);

    //nur an Standort mit ID location_id
    @GET("api/locations")Call<List<Modules>>
    getModulesAtLocationID(@Query("location_id") int location_id);

    //nur in Gebäude mit ID building_id
    @GET("api/locations")Call<List<Modules>>
    getModulesAtBuildingID(@Query("building_id") int building_id);

    //Komma-separierte Liste mit Tag-IDs Beispiel: "1,3,5"
    @GET("api/tags")Call<List<Modules>>
    getModulesWithTags(@Query("tag_ids") String tag_ids);


}
