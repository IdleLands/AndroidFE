package idle.land.app.logic.api.apievents;

import idle.land.app.logic.api.StatusCode;

public class AbstractHeartbeatEvent {

    public StatusCode code;

    public AbstractHeartbeatEvent(StatusCode type)
    {
        this.code = type;
    }
}
