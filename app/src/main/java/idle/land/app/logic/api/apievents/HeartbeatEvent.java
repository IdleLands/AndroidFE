package idle.land.app.logic.api.apievents;

import android.support.annotation.Nullable;
import idle.land.app.logic.Model.Player;
import idle.land.app.logic.api.StatusCode;

public class HeartbeatEvent extends AbstractHeartbeatEvent {

    public Player player;
    boolean success;

    public HeartbeatEvent(StatusCode code, @Nullable Player player) {
        super(code);
        this.player = player;
    }

    public boolean isSuccessful()
    {
        return player != null;
    }
}
