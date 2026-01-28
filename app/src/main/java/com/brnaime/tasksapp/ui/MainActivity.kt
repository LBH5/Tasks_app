package com.brnaime.tasksapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brnaime.tasksapp.R
import com.brnaime.tasksapp.databinding.ActivityMainBinding
import com.brnaime.tasksapp.databinding.DialogAddTaskBinding
import com.brnaime.tasksapp.ui.tasks.StarredTasksFragment
import com.brnaime.tasksapp.ui.tasks.TasksFragment
import com.brnaime.tasksapp.util.InputValidator.isInputValid
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)
            floatingActionButton.setOnClickListener { showAddTaskDialog() }
            viewPager.currentItem = 1
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                when(position){
                    0 -> tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.star_fill_24)
                    1 -> tab.text = "Tasks"
                    2 -> tab.customView = Button(this@MainActivity).apply {
                        text = "Add New List"
                    }
                }
            }.attach()
            setContentView(root)
        }
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            imageButtonTaskDetails.setOnClickListener {
                when(editTextNewTaskDetails.isVisible){
                    true -> editTextNewTaskDetails.visibility = View.GONE
                    else -> editTextNewTaskDetails.visibility = View.VISIBLE
                }
            }
            buttonSaveTask.isEnabled = false
            editTextNewTask.addTextChangedListener { input ->
                buttonSaveTask.isEnabled = when(isInputValid(input.toString())) {
                    true -> {
                        editTextNewTask.error = null
                        true
                    }
                    else -> {
                        editTextNewTask.error = "Minimum 5 characters required"
                        false
                    }
                }
            }
            buttonSaveTask.setOnClickListener {
                val title = editTextNewTask.text.toString().trim()
                val description = editTextNewTaskDetails.text.toString().trim()

                viewModel.createTask(title,description)
                dialog.dismiss()
            }
            dialog.show()
        }
    }




    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 ->  StarredTasksFragment()
                    1 ->  TasksFragment()
                    else -> TasksFragment()
                }
        }

        override fun getItemCount() = 3
    }
}