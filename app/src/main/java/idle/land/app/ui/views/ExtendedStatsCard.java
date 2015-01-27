package idle.land.app.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import idle.land.app.R;
import idle.land.app.logic.Model.Player;

public class ExtendedStatsCard extends CardView {

    @InjectView(R.id.tvStr)
    TextView tvStr;

    @InjectView(R.id.tvDex)
    TextView tvDex;

    @InjectView(R.id.tvAgi)
    TextView tvAgi;

    @InjectView(R.id.tvCon)
    TextView tvCon;

    @InjectView(R.id.tvWis)
    TextView tvWis;

    @InjectView(R.id.tvInt)
    TextView tvInt;

    @InjectView(R.id.tvLuck)
    TextView tvLuck;

    @InjectView(R.id.tvFire)
    TextView tvFire;

    @InjectView(R.id.tvWater)
    TextView tvWater;

    @InjectView(R.id.tvThunder)
    TextView tvThunder;

    @InjectView(R.id.tvEarth)
    TextView tvEarth;

    @InjectView(R.id.tvIce)
    TextView tvIce;

    public ExtendedStatsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.extended_stats_card, this, true);
        ButterKnife.inject(this, this);
    }

    public void setPlayer(Player player)
    {
        tvStr.setText(Integer.toString((int) (double)player.getBaseStat("str")));
        tvDex.setText(Integer.toString((int) (double)player.getBaseStat("dex")));
        tvAgi.setText(Integer.toString((int) (double)player.getBaseStat("agi")));
        tvCon.setText(Integer.toString((int) (double)player.getBaseStat("con")));
        tvInt.setText(Integer.toString((int) (double)player.getBaseStat("int")));
        tvWis.setText(Integer.toString((int) (double)player.getBaseStat("wis")));
        tvLuck.setText(Integer.toString((int) (double)player.getBaseStat("luck")));
        tvFire.setText(Integer.toString((int) (double)player.getBaseStat("fire")));
        tvWater.setText(Integer.toString((int) (double)player.getBaseStat("water")));
        tvThunder.setText(Integer.toString((int) (double)player.getBaseStat("thunder")));
        tvEarth.setText(Integer.toString((int) (double)player.getBaseStat("earth")));
        tvIce.setText(Integer.toString((int) (double)player.getBaseStat("ice")));
    }

}
