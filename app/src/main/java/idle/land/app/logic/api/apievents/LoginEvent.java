package idle.land.app.logic.api.apievents;

import android.support.annotation.Nullable;
import idle.land.app.logic.Model.Player;
import idle.land.app.logic.api.StatusCode;

public class LoginEvent extends HeartbeatEvent {

    public LoginEvent(StatusCode code, @Nullable Player player) {
        super(code, player);
    }
}
