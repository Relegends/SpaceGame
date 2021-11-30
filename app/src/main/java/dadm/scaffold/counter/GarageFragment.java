package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.GameLogic;
import dadm.scaffold.IkarugaState;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class GarageFragment extends BaseFragment {

    ImageButton planeA, planeB;

    public GarageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_garage, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        planeA = view.findViewById(R.id.planebtnA);
        planeB = view.findViewById(R.id.planebtnB);
        planeA.setOnClickListener(v -> {
            GameLogic.GAME.setPlayerIkarugaState(IkarugaState.RED);
            GameLogic.GAME.setMarbleIkarugaState(IkarugaState.BLUE);
            GameLogic.GAME.setPaperBallIkarugaState(IkarugaState.RED);
            ((ScaffoldActivity)getActivity()).startGame();});
        planeB.setOnClickListener(v -> {
            GameLogic.GAME.setPlayerIkarugaState(IkarugaState.WHITE);
            GameLogic.GAME.setMarbleIkarugaState(IkarugaState.BLACK);
            GameLogic.GAME.setPaperBallIkarugaState(IkarugaState.WHITE);
            ((ScaffoldActivity)getActivity()).startGame();});
    }
}
