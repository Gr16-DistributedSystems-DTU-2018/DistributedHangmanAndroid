package io.inabsentia.superhangman.utils;

import android.content.Context;

import io.inabsentia.superhangman.data.dao.HighScoreDAO;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;
import io.inabsentia.superhangman.logic.GameLogic;

public class Utils {

    private static Utils instance;

    public final boolean MUSIC_ENABLED = true;

    private final GameLogic logic = GameLogic.getInstance();
    private final IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();

    private Utils() {

    }

    static {
        try {
            instance = new Utils();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Utils getInstance() {
        return instance;
    }

    public void createScoreAndReset(Context context) {
        double avgRoundTime = -1;

        if (logic.getTotalGames() <= 0) {
            avgRoundTime = logic.getTimeUsed();
        } else {
            avgRoundTime = logic.getTimeUsed() / logic.getTotalGames();
        }



        HighScoreDTO highScoreDTO = new HighScoreDTO("", logic.getSecretWord(), logic.getScore(), logic.getWinCount(), avgRoundTime);

        try {
            highScoreDAO.add(highScoreDTO);
            highScoreDAO.save(context);
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }

        try {
            logic.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}