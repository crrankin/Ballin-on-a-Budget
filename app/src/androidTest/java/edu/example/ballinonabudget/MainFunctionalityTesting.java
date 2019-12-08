package edu.example.ballinonabudget;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainFunctionalityTesting {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.ballinonabudget", appContext.getPackageName());
    }

    @Test
    public void testBudgetDeletionAfterCreation(){
        //tests that a budget is able to be deleted after
        //creating a new budget
    }

    @Test
    public void testBudgetDeletion(){
        //tests that a budget will be deleted on an instance with no budgets
    }

    @Test
    public void testItemCreation(){
        //tests that an item is created
    }

    @Test
    public void testItemDeletionAfterCreation(){
        //tests that a item can be deleted after creating it
    }

    @Test
    public void testItemDeletion(){
        //tests that an item can be deleted on an empty list of items
    }

    @Test
    public void testBudgetEditing(){
        //checks if a budget can be edited
    }
}
