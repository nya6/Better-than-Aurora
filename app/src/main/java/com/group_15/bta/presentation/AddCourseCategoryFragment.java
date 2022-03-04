package com.group_15.bta.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.group_15.bta.R;
import com.group_15.bta.objects.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView coursesList;
    private SimpleAdapter coursesNamesAdapted;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCourseCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_a_course_from_category.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseCategoryFragment newInstance(String param1, String param2) {
        AddCourseCategoryFragment fragment = new AddCourseCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_a_course_from_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Category selectedCategory = AddCourseCategoryFragmentArgs.fromBundle(requireArguments()).getCategory();



        coursesList = view.findViewById(R.id.courses_list_in_add_courses);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i=0; i<selectedCategory.getCourses().size(); i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("ID", selectedCategory.getCourses().get(i).getID());
            datum.put("Title", selectedCategory.getCourses().get(i).getTitle());
            data.add(datum);
        }

        coursesNamesAdapted = new SimpleAdapter(getContext(),
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"ID", "Title"},
                new int[]{android.R.id.text1, android.R.id.text2});

        coursesList.setAdapter(coursesNamesAdapted);
        NavController navController = NavHostFragment.findNavController(this);
        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                navController.navigate(AddCourseCategoryFragmentDirections.actionAddACourseFromCategoryToAddACourseWithCode(selectedCategory.getCourses().get(i)));
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.courses_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.categories_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Courses");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                coursesNamesAdapted.getFilter().filter(newText);
                return false;
            }
        });
    }
}