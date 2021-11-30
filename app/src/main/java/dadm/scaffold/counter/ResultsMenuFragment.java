package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.GameLogic;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class ResultsMenuFragment extends BaseFragment implements View.OnClickListener {

    private TextView paperBallText;
    private TextView marbleText;
    private TextView heartText;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_restart).setOnClickListener(this);
        paperBallText = view.findViewById(R.id.paperBallText);
        marbleText = view.findViewById(R.id.marbleText);
        heartText = view.findViewById(R.id.heartsText);
        progressBar = view.findViewById(R.id.progressBar);


        paperBallText.setText("x " + GameLogic.GAME.getPaperBallsDestroyed());
        marbleText.setText("x " + GameLogic.GAME.getMarblesDestroyed());
        heartText.setText("x " + GameLogic.GAME.getPlayerLives());
        progressBar.setProgress(GameLogic.GAME.getProgress());
    }

    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).startGame();
    }
}
