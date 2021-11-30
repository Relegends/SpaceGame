package dadm.scaffold.counter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.GameLogic;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.Background;
import dadm.scaffold.space.GameController;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.Tank;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;

    private ProgressBar progressBar;
    private TextView lives;
    private Integer printLives;
    private Handler mHandler = new Handler();

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        progressBar = view.findViewById(R.id.tank_health);
        lives = view.findViewById(R.id.livesHUD);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setSoundManager(getScaffoldActivity().getSoundManager());
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new Background(theGameEngine));
                theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine));
                theGameEngine.addGameObject(new Tank(theGameEngine));
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.addGameObject(new GameController(theGameEngine));
                theGameEngine.startGame();
            }
        });
        GameLogic.GAME.resetProgress();
        asyncTask();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
        GameLogic.GAME.isPlaying = false;
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        GameLogic.GAME.isPlaying = false;
                        ((ScaffoldActivity) getActivity()).returnToMenu();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create();
        Dialog dialog1 = dialog.create();
        dialog1.show();
        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);

    }

    private void gameOverAndShowGameOverDialog() {
        theGameEngine.pauseGame();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(R.string.game_over_dialog_title)
                .setMessage(R.string.game_over_dialog_message)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity) getActivity()).startGame();
                    }
                })
                .setNegativeButton(R.string.results, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity) getActivity()).stopGame();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create();
        Dialog dialog1 = dialog.create();
        dialog1.show();
        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);
    }

    private void winAndShowGameOverDialog() {
        theGameEngine.pauseGame();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(R.string.winner_dialog_title)
                .setMessage(R.string.winner_dialog_message)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity) getActivity()).startGame();
                    }
                })
                .setNegativeButton(R.string.results, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity) getActivity()).stopGame();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create();
        Dialog dialog1 = dialog.create();
        dialog1.show();
        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.holo_orange_light);

    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        } else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }

    private void asyncTask() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                while (GameLogic.GAME.getProgress() >= 0 && GameLogic.GAME.getLives() > 0 && GameLogic.GAME.isPlaying) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(GameLogic.GAME.getProgress());
                            printLives = new Integer(GameLogic.GAME.getLives());
                            lives.setText(printLives.toString());
                        }
                    });
                }

                mHandler.post(new Runnable() {
                    public void run() {
                        if (GameLogic.GAME.getLives() <= 0) {
                            gameOverAndShowGameOverDialog();
                        } else if (GameLogic.GAME.getProgress() <= 0) {
                            winAndShowGameOverDialog();
                        }
                    }
                });

                return null;
            }

        }.execute();
    }

}
