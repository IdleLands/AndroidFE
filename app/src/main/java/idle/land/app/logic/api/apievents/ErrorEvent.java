package idle.land.app.logic.api.apievents;

import idle.land.app.logic.api.StatusCode;

public class ErrorEvent extends AbstractHeartbeatEvent {
    public ErrorEvent(StatusCode type) {
        super(type);
    }
}
