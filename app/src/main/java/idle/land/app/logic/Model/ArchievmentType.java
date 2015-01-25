package idle.land.app.logic.Model;

import com.google.gson.annotations.SerializedName;
import com.joanzapata.android.iconify.Iconify;

public enum ArchievmentType {

    @SerializedName("class")
    CLASS(Iconify.IconValue.fa_child),

    @SerializedName("event")
    EVENT(Iconify.IconValue.fa_info),

    @SerializedName("combat")
    COMBAT(Iconify.IconValue.fa_magic),

    @SerializedName("special")
    SPECIAL(Iconify.IconValue.fa_gift),

    @SerializedName("personality")
    PERSONALITY(Iconify.IconValue.fa_group),

    @SerializedName("exploration")
    EXPLORATION(Iconify.IconValue.fa_compass),

    @SerializedName("progress")
    PROGRESS(Iconify.IconValue.fa_signal);

    public Iconify.IconValue mIconVal;

    ArchievmentType(Iconify.IconValue val)
    {
        mIconVal = val;
    }

    public Iconify.IconValue getIconValue()
    {
        return mIconVal;
    }

}
