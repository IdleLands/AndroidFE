package idle.land.app.logic.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import idle.land.app.logic.Model.Player;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Date;

/**
 * Abstract Callback class for Heartbeat methods
 * @see idle.land.app.logic.api.ApiConnection
 * @see idle.land.app.logic.api.HeartbeatService
 */
public abstract class HeartbeatCallback implements Callback<JsonObject> {

    @Override
    public void success(JsonObject jsonObject, Response response) {
        boolean success = jsonObject.get("isSuccess").getAsBoolean();
        StatusCode code = StatusCode.byCode(jsonObject.get("code").getAsInt());

        if(success)
        {
            Player player = deserializePlayer(jsonObject.get("player").getAsJsonObject());
            if(response.getUrl().contains("login"))
                onLoginSuccess(code, player, jsonObject.get("token").getAsString());
            else
                onHeartbeatSuccess(code, player);
        } else
            onError(code);
    }

    private Player deserializePlayer(JsonObject playerObject)
    {
        return getPlayerDeserializer().fromJson(playerObject, Player.class);
    }

    private Gson getPlayerDeserializer()
    {
        return new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
    }

    @Override
    public void failure(RetrofitError error) {
        onError(StatusCode.UNKNOWN);
    }

    /**
     * Callback for a successful heartbeat
     * @param player new player object
     */
    public abstract void onHeartbeatSuccess(StatusCode code, Player player);

    /**
     * Callback for a heartbeat error
     * @param errorCode
     */
    public abstract void onError(StatusCode errorCode);

    /**
     * Callback for login
     * Might be fired instead of a normal heartbeat if we got an invalid token event
     * @param player new player object
     * @param token new token from REST api
     */
    public abstract void onLoginSuccess(StatusCode code, Player player, String token);
}
