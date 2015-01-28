package idle.land.app.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import idle.land.app.R;
import idle.land.app.logic.Model.Player;
import idle.land.app.logic.Model.ValuePair;

public class BaseInformationCard extends CardView {

    @InjectView(R.id.tvName)
    TextView tvName;

    @InjectView(R.id.tvTitle)
    TextView tvTitle;

    @InjectView(R.id.tvAge)
    TextView tvAge;

    @InjectView(R.id.tvPosition)
    TextView tvPosition;

    @InjectView(R.id.tvParty)
    TextView tvParty;

    @InjectView(R.id.tvGuild)
    TextView tvGuild;

    @InjectView(R.id.pbExperience)
    ProgressBar experienceBar;

    @InjectView(R.id.tvExperience)
    TextView tvExperience;

    @InjectView(R.id.tvGold)
    TextView tvGold;

    public BaseInformationCard(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.CardViewStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.base_information_card, this, true);
        ButterKnife.inject(this, this);
    }

    public void setPlayer(Player player)
    {
        tvName.setText(player.getName());
        tvTitle.setText(String.format(getContext().getString(R.string.overview_title), player.getLevel().getCurrent(), player.getProfessionName()));
        tvAge.setText(String.format(getContext().getString(R.string.overview_age), DateUtils.getRelativeDateTimeString(getContext(), player.getRegistrationDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, 0) ));
        tvPosition.setText(String.format(getContext().getString(R.string.overview_position), player.getMap(), player.getMapRegion()));
        tvParty.setText((player.getPartyName().isEmpty()) ? getContext().getString(R.string.overview_no_party) : player.getPartyName());
        tvGuild.setText("IMPLEMENT ME");
        setExperienceBar(player.getXp());
        tvGold.setText(Integer.toString(player.getGold().getCurrent()));
    }


    private void setExperienceBar(ValuePair xp)
    {
        experienceBar.setMax(xp.getMaximum());
        experienceBar.setProgress(xp.getCurrent());
        String experience = String.format(getContext().getString(R.string.overview_experience_progress), xp.getCurrent(), xp.getMaximum(), (int)(((float) xp.getCurrent())/xp.getMaximum() * 100) );
        tvExperience.setText(experience);
    }

}
