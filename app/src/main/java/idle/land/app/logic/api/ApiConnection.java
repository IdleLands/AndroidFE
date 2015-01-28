package idle.land.app.logic.api;

import com.google.gson.JsonObject;
import idle.land.app.BuildConfig;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Connection to the REST Api
 * Methods with Callback are performed asynchronous
 */
public class ApiConnection {

    private static final String BASE_URL = "http://api.idle.land";

    ApiService service;

    public ApiConnection()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
        if(BuildConfig.DEBUG)
            adapter.setLogLevel(RestAdapter.LogLevel.FULL);
        service = adapter.create(ApiService.class);
    }

    public void login(String identifier, String password, HeartbeatCallback cb)
    {
        service.login(identifier, password, cb);
    }

    public void turn(String identifier, String token, HeartbeatCallback cb)
    {
        service.turn(identifier, token, cb);
    }

    public void register(String identifier, String name, String password, Callback<JsonObject> cb)
    {
        service.register(identifier, name, password, cb);
    }

    /**
     * Asynchronous logout without callback
     */
    public void logout(String identifier, String token) { service.logout(identifier, token, new Callback<JsonObject>() {
        @Override
        public void success(JsonObject jsonObject, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {

        }
    }); }

    interface ApiService {
        @POST("/player/auth/login")
        @FormUrlEncoded
        void login(@Field("identifier") String identifier, @Field("password") String password, HeartbeatCallback callback);

        @POST("/player/action/turn")
        @FormUrlEncoded
        void turn(@Field("identifier") String identifier, @Field("token") String token, HeartbeatCallback callback);

        @POST("/player/auth/logout")
        @FormUrlEncoded
        void logout(@Field("identifier") String identifier, @Field("token") String token, Callback<JsonObject> cb);

        @PUT("/player/auth/register")
        void register(@Field("identifier") String identifier, @Field("name") String name, @Field("password") String password, Callback<JsonObject> cb);
    }
}
