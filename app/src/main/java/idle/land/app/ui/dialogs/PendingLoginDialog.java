package idle.land.app.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.api.HeartbeatEvent;

public class PendingLoginDialog extends DialogFragment {

    public static final String TAG = PendingLoginDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setMessage(getActivity().getString(R.string.login_dialog_message));
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onReceiveEvent(HeartbeatEvent e)
    {
        dismiss();
    }
}
