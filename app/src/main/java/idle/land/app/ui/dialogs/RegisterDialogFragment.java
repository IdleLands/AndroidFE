package idle.land.app.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import idle.land.app.R;
import idle.land.app.logic.Logger;

public class RegisterDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public static final String TAG = RegisterDialogFragment.class.getSimpleName();

    EditText etUsername, etPassword1, etPassword2;
    CheckBox cbRemember;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.register_dialog, null, false);
        etUsername = (EditText) view.findViewById(R.id.etRegistrationUsername);
        etPassword1 = (EditText) view.findViewById(R.id.etRegistrationPassword);
        etPassword2 = (EditText) view.findViewById(R.id.etRegistrationPasswordAgain);
        cbRemember = (CheckBox) view.findViewById(R.id.cbRegistrationRemember);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getActivity().getString(R.string.register_dialog_title))
                .setPositiveButton(getActivity().getString(R.string.register_dialog_positive_button), this)
                .setNegativeButton(getActivity().getString(R.string.register_dialog_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logger.debug(TAG, "aborted");
                    }
                })
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String username = etUsername.getText().toString();
        String pass1 = etPassword1.getText().toString();
        String pass2 = etPassword2.getText().toString();
        if(!pass1.equals(pass2)) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.register_dialog_error_passwords_dont_match), Toast.LENGTH_SHORT).show();
            return;
        } else if(username.isEmpty() || pass1.isEmpty())
        {
            Toast.makeText(getActivity(), getActivity().getString(R.string.login_error_no_username_or_no_password), Toast.LENGTH_SHORT).show();
            return;
        } else
        {
            register(username, pass1);
        }
    }

    private void register(String user, String password)
    {
        Logger.debug(TAG, String.format("Registering %1$s with password %2$s", user, password));
        String appIdentifier = getActivity().getString(R.string.app_ident);
        String identifier = appIdentifier + "#" + user;
        // TODO implement
//        new ApiConnection().register(identifier, user, password, new Callback<JsonObject>() {
//            @Override
//            public void success(JsonObject jsonObject, Response response) {
//                if(jsonObject.has("isSuccess") && jsonObject.get("isSuccess").getAsBoolean())
//                {
//                    // Successful registered.
//                } else
//                {
//                    // registration failed
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
    }
}
