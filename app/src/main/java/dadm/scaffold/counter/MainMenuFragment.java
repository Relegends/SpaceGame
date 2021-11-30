package dadm.scaffold.counter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class MainMenuFragment extends BaseFragment {

    Button starbtn, tutorialbtn;

    public MainMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        starbtn = view.findViewById(R.id.btn_start);
        tutorialbtn = view.findViewById(R.id.btn_tutorial);
        starbtn.setOnClickListener(v -> ((ScaffoldActivity)getActivity()).moveToGarage());
        tutorialbtn.setOnClickListener(v -> ((ScaffoldActivity)getActivity()).moveToTutorial());
    }
}
