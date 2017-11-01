package io.inabsentia.superhangman.helper;

import android.content.Context;

import io.inabsentia.superhangman.data.dao.IMatchDAO;
import io.inabsentia.superhangman.data.dao.MatchDAO;
import io.inabsentia.superhangman.data.dto.MatchDTO;
import io.inabsentia.superhangman.logic.GameLogic;

public class Utils {

    private static Utils instance;

    public final boolean MUSIC_ENABLED = true;
    public final String WORD_URL = "https://www.nytimes.com/";

    private final GameLogic logic = GameLogic.getInstance();
    private final IMatchDAO highScoreDAO = MatchDAO.getInstance();

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

    public void createMatchAndReset(Context context) {
        double avgRoundTime = -1;

        if (logic.getTotalGames() <= 0) {
            avgRoundTime = logic.getTimeUsed();
        } else {
            avgRoundTime = logic.getTimeUsed() / logic.getTotalGames();
        }

        MatchDTO highScoreDTO = new MatchDTO("", logic.getSecretWord(), logic.getScore(), logic.getWinCount(), avgRoundTime);

        try {
            highScoreDAO.add(highScoreDTO);
            highScoreDAO.save(context);
        } catch (IMatchDAO.DALException e) {
            e.printStackTrace();
        }

        try {
            logic.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}