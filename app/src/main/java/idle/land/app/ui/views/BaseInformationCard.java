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

public class BaseInformationCard extends CardView {

    @InjectView(R.id.tvName)
    TextView tvName;

    @InjectView(R.id.tvLevel)
    TextView tvLevel;

    @InjectView(R.id.tvTitle)
    TextView tvTitle;

    public BaseInformationCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.base_information_card, this, true);
        ButterKnife.inject(this, this);
    }

    public void setPlayer(Player player)
    {
        tvName.setText(player.getName());
        tvLevel.setText("(99)");
        tvTitle.setText(String.format(getContext().getString(R.string.base_information_title), player.getProfessionName()));
    }

}
