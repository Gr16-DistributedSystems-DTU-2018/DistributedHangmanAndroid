package io.inabsentia.superhangman;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.inabsentia.superhangman.data.dao.IMatchDAO;
import io.inabsentia.superhangman.data.dao.MatchDAO;
import io.inabsentia.superhangman.data.dto.MatchDTO;

import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HighScoreDAOTest {

    private IMatchDAO dao;
    private Context context;

    @Before
    public void setUp() throws Exception {
        dao = MatchDAO.getInstance();
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
         * Create 5 test MatchDTO objects.
         */
        MatchDTO[] dtos = new MatchDTO[]{
                new MatchDTO("a", "b", 100, 200, 300),
                new MatchDTO("c", "d", 400, 500, 600),
                new MatchDTO("e", "f", 700, 800, 900),
                new MatchDTO("g", "h", 1000, 1100, 1200),
                new MatchDTO("j", "k", 1300, 1400, 1500)
        };

        /*
         * Add them to the DAO.
         */
        for (MatchDTO dto : dtos) dao.add(dto);

        /*
         * Save the current high scores to the internal storage.
         */
        dao.save(context);

        /*
         * Get them back.
         */
        List<MatchDTO> dtoList = dao.getAll();

        if (dtoList == null) {
            fail("dtoList equals null!");
        }

        if (dtoList.size() == 0) {
            fail("dtoList size equals 0!");
        }


    }

}
