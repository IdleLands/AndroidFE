package idle.land.app.logic.Model;

import com.google.gson.annotations.SerializedName;
import com.joanzapata.android.iconify.Iconify;

/**
 * Type for {@see Event}
 */
public enum EventType {
    @SerializedName("item-mod")
    ITEM_MOD(Iconify.IconValue.fa_legal),

    @SerializedName("item-find")
    ITEM_FIND(Iconify.IconValue.fa_gavel), // TODO correct icon

    @SerializedName("item-enchant")
    ITEM_ENCHANT(Iconify.IconValue.fa_magic),

    @SerializedName("item-switcheroo")
    ITEM_SWITCHEROO(Iconify.IconValue.fa_magnet), // TODO correct icon

    @SerializedName("shop")
    SHOP(Iconify.IconValue.fa_money),

    @SerializedName("shop-fail")
    SHOP_FAIL(Iconify.IconValue.fa_money),

    @SerializedName("profession")
    PROFESSION(Iconify.IconValue.fa_child),

    @SerializedName("explore")
    EXPLORE(Iconify.IconValue.fa_globe),

    @SerializedName("levelup")
    LEVELUP(Iconify.IconValue.fa_graduation_cap), // TODO correct icon

    @SerializedName("achievement")
    ARCHIVMENT(Iconify.IconValue.fa_shield),

    @SerializedName("party")
    PARTY(Iconify.IconValue.fa_users),

    @SerializedName("exp")
    EXP(Iconify.IconValue.fa_support),

    @SerializedName("gold")
    GOLD(Iconify.IconValue.fa_money),

    @SerializedName("guild")
    GUILD(Iconify.IconValue.fa_sitemap),

    @SerializedName("combat")
    COMBAT(Iconify.IconValue.fa_newspaper_o),

    @SerializedName("event")
    EVENT(Iconify.IconValue.fa_gift),

    @SerializedName("pet")
    PET(Iconify.IconValue.fa_paw);

    public Iconify.IconValue mIconVal;

    EventType(Iconify.IconValue val)
    {
        mIconVal = val;
    }

    public Iconify.IconValue getIconValue()
    {
        return mIconVal;
    }
}