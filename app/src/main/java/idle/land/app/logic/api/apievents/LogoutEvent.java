package idle.land.app.logic.api.apievents;

import idle.land.app.logic.api.StatusCode;

public class LogoutEvent extends AbstractHeartbeatEvent{

    public LogoutEvent() {
        super(StatusCode.Successfullogout);
    }
}
