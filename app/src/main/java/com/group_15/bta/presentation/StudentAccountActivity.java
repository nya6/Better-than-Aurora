package com.group_15.bta.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.group_15.bta.objects.Student;
import com.group_15.bta.R;
import com.group_15.bta.business.AccessUsers;

import java.io.Serializable;

public class StudentAccountActivity extends AppCompatActivity {

    private NavController studentNavController;
    private Student currentUser;
    private Student studentAccountInstance = new Student();
    private AccessUsers studentLoginInstance = new AccessUsers(studentAccountInstance);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account);
        currentUser = (Student) getIntent().getSerializableExtra(studentLoginInstance.getUserTypeString(studentAccountInstance));
        BottomNavigationView studentNav = (BottomNavigationView) findViewById(R.id.student_nav_bar);
        NavHostFragment studentNavHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.student_nav_fragment);
        studentNavController = studentNavHost.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.student_home, R.id.student_courses, R.id.student_profile, R.id.student_settings)
                .build();

        NavigationUI.setupWithNavController(studentNav, studentNavController);
        NavigationUI.setupActionBarWithNavController(this, studentNavController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return studentNavController.navigateUp() || super.onSupportNavigateUp();
    }

    public static void setFragmentNavigationButton(View view, int buttonId, int fragmentActionId)
    {
        NavController navController = Navigation.findNavController(view);
        Button fragmentButton = view.findViewById(buttonId);
        View.OnClickListener fragmentButtonAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentButton.setText("Loading...");
                navController.navigate(fragmentActionId);
            }
        };
        fragmentButton.setOnClickListener(fragmentButtonAction);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}