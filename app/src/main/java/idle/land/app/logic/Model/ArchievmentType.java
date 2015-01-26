package idle.land.app.logic.Model;

import com.google.gson.annotations.SerializedName;
import idle.land.app.R;

public enum ArchievmentType {

    @SerializedName("class")
    CLASS(R.drawable.child),

    @SerializedName("event")
    EVENT(R.drawable.info),

    @SerializedName("combat")
    COMBAT(R.drawable.magic),

    @SerializedName("special")
    SPECIAL(R.drawable.gift),

    @SerializedName("personality")
    PERSONALITY(R.drawable.group),

    @SerializedName("exploration")
    EXPLORATION(R.drawable.compass),

    @SerializedName("progress")
    PROGRESS(R.drawable.signal);

    public int iconResId;

    ArchievmentType(int resId)
    {
        iconResId = resId;
    }

    public int getIconResId()
    {
        return iconResId;
    }

}
