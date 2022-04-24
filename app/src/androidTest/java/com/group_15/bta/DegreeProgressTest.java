package com.group_15.bta;


import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.adevinta.android.barista.interaction.BaristaSleepInteractions;
import com.group_15.bta.application.Services;
import com.group_15.bta.objects.Course;
import com.group_15.bta.objects.Degree;
import com.group_15.bta.objects.Student;
import com.group_15.bta.objects.User;
import com.group_15.bta.persistence.CoursePersistence;
import com.group_15.bta.persistence.DegreePersistence;
import com.group_15.bta.persistence.UserPersistence;
import com.group_15.bta.presentation.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DegreeProgressTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDatabase(){

        Degree testDegree = new Degree("Hello");
        Student testStudent = new Student("student5", "student5", "Ayman2", "Hello");
        Course testCourse = new Course("Hello 101", "Hi", "Teaches you how to say hello", 3, "English", 1782.25, "Hello");

        DegreePersistence degreePersistence = Services.getDegreePersistence();
        ArrayList<Degree> degrees = degreePersistence.getDegreesList();

        if(!degrees.contains(testDegree))
        {
            degreePersistence.insert(testDegree);
        }
        degrees = degreePersistence.getDegreesList();
        assert degrees.contains(testDegree);

        CoursePersistence coursePersistence = Services.getCoursePersistence();
        ArrayList<Course> courses = coursePersistence.getCourseList();

        if(!courses.contains(testCourse))
        {
            coursePersistence.insertCourses(testCourse);
        }

        UserPersistence userPersistence = Services.getUserPersistence();
        ArrayList<User>  users = userPersistence.getUsers();


        if(!users.contains(testStudent))
        {
            userPersistence.insertUser(testStudent);
        }



    }

    @Test
    public void checkDegreeProgress()
    {
        onView(withId(R.id.userName)).perform(typeText("student5"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("student5"));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());
        onView(ViewMatchers.withId(R.id.student_landing_page)).perform(ViewActions.swipeUp());


        onData(anything())
                .inAdapterView(withId(R.id.student_required_courses))
                .atPosition(0)
                .onChildView(withId(R.id.course_code_list_item))
                .check(matches(withText(containsString("Hello 101"))));

        pressBack();
    }

}
