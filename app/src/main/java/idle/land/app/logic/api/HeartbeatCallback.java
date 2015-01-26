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

    /**
     * Relevant error types for heartbeat
     */
    protected enum ErrorType
    {
        BAD_TOKEN(-1),
        NO_NAME_SPECIFIED(6),
        NOT_LOGGED_IN(10),
        CANT_AUTH_VIA_PASSWORD(11),
        NO_PASSWORD_SET(12),
        PLAYER_DOESNT_EXIST(13),
        BAD_PASSWORD(14),
        ALREADY_LOGGED_IN(15),
        NO_PASSWORD_SPECIFIED(16),
        TOO_MANY_TRIES(100),
        UNKNOWN(-99);

        int code;
        ErrorType(int code)
        {
            this.code = code;
        }

        public static ErrorType byId(int id)
        {
            for(ErrorType type : ErrorType.values())
                if(type.code == id)
                    return type;
            return UNKNOWN;
        }

    }

    @Override
    public void success(JsonObject jsonObject, Response response) {
        if(jsonObject.get("isSuccess").getAsBoolean() || (jsonObject.get("code").getAsInt() == 100 && jsonObject.has("player")))
        {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            Player player = gson.fromJson(jsonObject.get("player"), Player.class);
            if(response.getUrl().contains("login"))
                onLoginSuccess(player, jsonObject.get("token").getAsString());
            else
                onHeartbeatSuccess(player);
        } else
        {
            onError(ErrorType.byId(jsonObject.get("code").getAsInt()));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        onError(ErrorType.UNKNOWN);
    }

    /**
     * Callback for a successful heartbeat
     * @param player new player object
     */
    public abstract void onHeartbeatSuccess(Player player);

    /**
     * Callback for a heartbeat error
     * @param error
     */
    public abstract void onError(ErrorType error);

    /**
     * Callback for login
     * Might be fired instead of a normal heartbeat if we got an invalid token event
     * @param player new player object
     * @param token new token from REST api
     */
    public abstract void onLoginSuccess(Player player, String token);
}
