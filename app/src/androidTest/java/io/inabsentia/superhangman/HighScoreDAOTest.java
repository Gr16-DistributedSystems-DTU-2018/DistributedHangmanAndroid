package io.inabsentia.superhangman;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.inabsentia.superhangman.data.dao.HighScoreDAO;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;

import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HighScoreDAOTest {

    private IHighScoreDAO dao;
    private Context context;

    @Before
    public void setUp() throws Exception {
        dao = HighScoreDAO.getInstance();
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
        context = null;
    }

    @Test
    public void createScoreObjects() throws Exception {
        /*
         * Create 5 test HighScoreDTO objects.
         */
        HighScoreDTO[] dtos = new HighScoreDTO[]{
                new HighScoreDTO("a", "b", 100, 200, 300),
                new HighScoreDTO("c", "d", 400, 500, 600),
                new HighScoreDTO("e", "f", 700, 800, 900),
                new HighScoreDTO("g", "h", 1000, 1100, 1200),
                new HighScoreDTO("j", "k", 1300, 1400, 1500)
        };

        /*
         * Add them to the DAO.
         */
        for (HighScoreDTO dto : dtos) dao.add(dto);

        /*
         * Save the current high scores to the internal storage.
         */
        dao.save(context);

        /*
         * Get them back.
         */
        List<HighScoreDTO> dtoList = dao.getAll();

        if (dtoList == null) {
            fail("dtoList equals null!");
        }

        if (dtoList.size() == 0) {
            fail("dtoList size equals 0!");
        }


    }

}
