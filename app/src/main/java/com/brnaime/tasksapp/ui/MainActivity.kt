package com.brnaime.tasksapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brnaime.tasksapp.data.TasksAppDB
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.ActivityMainBinding
import com.brnaime.tasksapp.databinding.DialogAddTaskBinding
import com.brnaime.tasksapp.ui.tasks.TasksFragment
import com.brnaime.tasksapp.util.InputValidator.isInputValid
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val database by lazy { TasksAppDB.getDatabase(this) }
    private val taskDao by lazy { database.getTaskDAO() }
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)
            floatingActionButton.setOnClickListener { showAddTaskDialog() }
            TabLayoutMediator(tabs, viewPager) { tab, _->
                tab.text = "Tasks"
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
                val task = Task(
                    title = title,
                    description = description
                )
                saveTask(task)
                dialog.dismiss()
                tasksFragment.fetchAllTasks()

            }
            dialog.show()
        }
    }


    private fun saveTask(task:Task) {
        thread {
            taskDao.createTask(task)
            runOnUiThread {
                Toast.makeText(this, "Task saved successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun createFragment(position: Int): Fragment {
                return tasksFragment
        }

        override fun getItemCount() = 1
    }
}