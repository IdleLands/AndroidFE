package idle.land.app.logic.Model;

import com.google.gson.annotations.SerializedName;
import idle.land.app.R;

/**
 * Type for {@see Event}
 */
public enum EventType {
    @SerializedName("item-mod")
    ITEM_MOD(R.drawable.legal),

    @SerializedName("item-find")
    ITEM_FIND(R.drawable.gavel), // TODO correct icon

    @SerializedName("item-enchant")
    ITEM_ENCHANT(R.drawable.magic),

    @SerializedName("item-switcheroo")
    ITEM_SWITCHEROO(R.drawable.magnet), // TODO correct icon

    @SerializedName("shop")
    SHOP(R.drawable.money),

    @SerializedName("shop-fail")
    SHOP_FAIL(R.drawable.money),

    @SerializedName("profession")
    PROFESSION(R.drawable.child),

    @SerializedName("explore")
    EXPLORE(R.drawable.globe),

    @SerializedName("levelup")
    LEVELUP(R.drawable.graduation_cap), // TODO correct icon

    @SerializedName("achievement")
    ARCHIVMENT(R.drawable.shield),

    @SerializedName("party")
    PARTY(R.drawable.users),

    @SerializedName("exp")
    EXP(R.drawable.support),

    @SerializedName("gold")
    GOLD(R.drawable.money),

    @SerializedName("guild")
    GUILD(R.drawable.sitemap),

    @SerializedName("combat")
    COMBAT(R.drawable.paper_plane_o),

    @SerializedName("event")
    EVENT(R.drawable.gift),

    @SerializedName("pet")
    PET(R.drawable.paw);

    public int mIconRes;

    EventType(int resId)
    {
        mIconRes = resId;
    }

    public int getIconResId()
    {
        return mIconRes;
    }
}