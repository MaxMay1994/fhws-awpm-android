package prog4_projekt.awpm_android.RestApi;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Module.Building;
import prog4_projekt.awpm_android.RestApi.Module.Date;
import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.Module.Tag;
import prog4_projekt.awpm_android.RestApi.Peroids.Periods;
import prog4_projekt.awpm_android.RestApi.UserData.Login;
import prog4_projekt.awpm_android.RestApi.UserData.User;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AwpmApi {

    //GET

    //Alle Module und ein ein Modul
    @GET("api/modules")
    Call<List<Module>> getAllModules(@Query("page")int page, @Query("per_page") int perPage); // für pagination @Path("page") int page

    @GET("api/modules/{id}")
    Call<Module> getModule(@Path("id") int id);

    @GET("api/modules/{id}")
    Call<Module> getAuthoristModule(@Path("id") int id,@Header("Authorization") String authorization);

    //Alle Locations und eine Location
    @GET("api/locations")
    Call<List<Building>> getAllLocations();

    @GET("api/locations/{id}")
    Call<Building> getLocation(@Path("id") int id);

    //Alle Subject Areas und ein Subject Area
    @GET("api/subject-areas")
    Call<List<Building>> getAllSubjectAreas();

    @GET("api/subject-areas/{id}")
    Call<Building> getSubjectAreas(@Path("id") int id);

    //Alle Tags und ein Tag z.B. Sprache oder Technik
    @GET("api/tags")
    Call<List<Tag>> getAllTags();

    @GET("api/tags/{id}")
    Call<Tag> getTag(@Path("id") int id);

    //Dates
    @GET("api/dates")
    Call<List<Date>> getAllDates();

    @GET("api/dates/{id}")
    Call<Date> getDate(@Path("id") int id);

    //Periods
    @GET("api/periods")
    Call<List<Periods>> getAllPeriods(@Query("active") boolean active);

    //Filter GET

    //Teilstück des Modul-Namens, nach dem gesucht werden soll (entspricht einem LIKE '%name%' auf der Datenbank)
    @GET("api/modules")
    Call<List<Module>>
    getMoudulesLike(@Query("name") String name);

    //true: nur aktive | false: auch welche, die in der Zukunft liegen | default: true
    @GET("api/modules")
    Call<List<Module>>
    getActiveModules(@Query("active") boolean active);

    //true: alle gesperrten Module für den aktuellen eingeloggten Benutzer | false: keine gesperrte Module | default: alle Module gemischt
    @GET("api/modules")
    Call<List<Module>>
    getBlockedModules(@Query("blocked") boolean blocked, @Header("Authorization") String authorization);

    //alle Module, die nicht für den Studiengang mit der ID not_blocked_for gesperrt sind
    @GET("api/modules")
    Call<List<Module>>
    getNotBlockedFor(@Query("not_blocked_for") int subjectAreaID);

    //true: nur Intensivkurse anzeigen | false: keine Intensivkurse (sondern alle semesterbegeleitende) | default: beide Typen gemischt
    @GET("api/modules")
    Call<List<Module>>
    getIntensiveModules(@Query("intensive") boolean intensive);

    //true: nur Favoriten | false: alle, die nicht favorisiert sind | default: alle
    @GET("api/modules")
    Call<List<Module>>
    getFavoredModules(@Query("favored") boolean favored, @Header("Authorization") String authorization);

    @GET("api/modules")
    Call<List<Module>>
    getFavoredModulesWithoutVotedModules(@Query("favored") boolean favored, @Query("voted") boolean voted, @Header("Authorization") String authorization);

    //true: nur gewählte Module | false: alle, die nicht gewählt sind | default: alle
    @GET("api/modules")
    Call<List<Module>>
    getVotedModules(@Query("voted") boolean voted, @Header("Authorization") String authorization);

    //true: nur Module, für die der Benutzer akzeptiert wurde | false: alle Module, für die der Benutzer nicht akzeptiert wurde | default: alle
    @GET("api/modules")
    Call<List<Module>>
    getAcceptedModules(@Query("accepted") boolean accepted, @Header("Authorization") String authorization);

    //nur an Standort mit ID location_id
    @GET("api/modules")
    Call<List<Module>>
    getModulesAtLocationID(@Query("location_id") int location_id);

    //nur in Gebäude mit ID building_id
    @GET("api/modules")
    Call<List<Module>>
    getModulesAtBuildingID(@Query("building_id") int building_id);

    //Komma-separierte Liste mit Tag-IDs Beispiel: "1,3,5"
    @GET("api/tags")
    Call<List<Module>>
    getModulesWithTags(@Query("tag_ids") String tag_ids);

    @GET("api/whoami")
    Call<Login>
    getWhoAmI(@Header("Authorization") String authorization);

    //PATCH

    //true: Modul wählen | false: Modul entwählen
    @PATCH("api/modules/{id}")
    Call<Module> patchVoted(@Path("id") int id, @Query("voted") boolean voted,  @Header("Authorization") String authorization);

    //true: Modul favorisieren | false: Modul entfavorisieren
    @PATCH("api/modules/{id}")
    Call<Module> patchFavored(@Path("id") int id, @Query("favored") boolean favored, @Header("Authorization") String authorization);



}
