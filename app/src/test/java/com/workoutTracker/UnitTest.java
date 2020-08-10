package com.workoutTracker;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.workoutTracker.SelectExerciseActivity.Model;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    private Context Context;

    @Test
    public void useAppContext() {
        try {
            StrengthRanks ranks = new StrengthRanks("Bench Press","male",new File("src/StrengthRanks/benchPress.CSV"));
            String rank = ranks.getStrengthLevel(90,110);
            System.out.println(rank);
        } catch (StrengthRanks.FileIncompatibleException | IOException e) {
            e.printStackTrace();
        }
    }
}

