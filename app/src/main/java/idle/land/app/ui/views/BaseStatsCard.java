package idle.land.app.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import idle.land.app.R;
import idle.land.app.logic.Model.Player;

public class BaseStatsCard extends CardView {

    @InjectView(R.id.ivHeart)
    ImageView ivHealth;

    @InjectView(R.id.ivMana)
    ImageView ivMana;

    @InjectView(R.id.ivSpecial)
    ImageView ivSpecial;

    @InjectView(R.id.tvHealt)
    TextView tvhealth;

    @InjectView(R.id.tvMana)
    TextView tvMana;

    @InjectView(R.id.tvSpecial)
    TextView tvSpecial;

    public BaseStatsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.base_stats_card, this, true);
        ButterKnife.inject(this, this);
        ivHealth.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        ivMana.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        ivSpecial.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    public void setPlayer(Player player)
    {
        String base = "<b>%1$s</b><br>   /%2$s";
        tvhealth.setText(Html.fromHtml(String.format(base, player.getHp().getCurrent(), player.getHp().getMaximum()) ));
        tvMana.setText( Html.fromHtml(String.format(base, player.getMp().getCurrent(), player.getMp().getMaximum())));
        tvSpecial.setText(Html.fromHtml(String.format(base, player.getSpecial().getCurrent(), player.getSpecial().getMaximum())));
    }

}
