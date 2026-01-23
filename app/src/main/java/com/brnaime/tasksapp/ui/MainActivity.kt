package com.brnaime.tasksapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brnaime.tasksapp.R
import com.brnaime.tasksapp.data.TasksAppDB
import com.brnaime.tasksapp.data.models.Task
import com.brnaime.tasksapp.databinding.ActivityMainBinding
import com.brnaime.tasksapp.databinding.DialogAddTaskBinding
import com.brnaime.tasksapp.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TasksAppDB
    private val taskDao by lazy { database.getTaskDAO() }
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tasks"
        }.attach()
        binding.floatingActionButton.setOnClickListener {
            showAddTaskDialog()
        }
        database = TasksAppDB.getDatabase(this)


    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.imageButtonTaskDetails.setOnClickListener {
            when(dialogBinding.editTextNewTaskDetails.isVisible){
                true -> dialogBinding.editTextNewTaskDetails.visibility = View.GONE
                else -> dialogBinding.editTextNewTaskDetails.visibility = View.VISIBLE
            }
        }
        dialogBinding.buttonSaveTask.setOnClickListener {
            val task = Task(
                title = dialogBinding.editTextNewTask.text.toString().trim(),
                description = dialogBinding.editTextNewTaskDetails.text.toString().trim()
            )
            saveTask(task)
            dialog.dismiss()
            tasksFragment.fetchAllTasks()
        }
        dialog.show()
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