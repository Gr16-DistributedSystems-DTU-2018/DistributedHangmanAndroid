package io.inabsentia.superhangman.utils;

import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dao.PHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;
import io.inabsentia.superhangman.logic.GameLogic;

public class Utils {

    private static Utils instance;
    private final GameLogic logic = GameLogic.getInstance();
    private final IHighScoreDAO highScoreDAO = PHighScoreDAO.getInstance();

    public final boolean MUSIC_ENABLED = false;

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

    public void createScoreAndReset() {
        double avgRoundTime = -1;

        if (logic.getTotalGames() <= 0) {
            avgRoundTime = logic.getTimeUsed();
        } else {
            avgRoundTime = logic.getTimeUsed() / logic.getTotalGames();
        }

        HighScoreDTO highScoreDTO = new HighScoreDTO("", logic.getSecretWord(), logic.getScore(), logic.getWinCount(), avgRoundTime);

        try {
            highScoreDAO.add(highScoreDTO);
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