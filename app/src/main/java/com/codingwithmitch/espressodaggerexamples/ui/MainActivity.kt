package com.codingwithmitch.espressodaggerexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    DataStateListener,
    UICommunicationListener
{

    private val CLASS_NAME = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
    }

    // Handles loading and errors
    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let{
            GlobalScope.launch(Dispatchers.Main){

                displayMainProgressBar(it.loading.isLoading)

                it.errorEvent?.let { errorEvent ->
//                    TODO("handle error event")
                }
            }
        }
    }

    override fun onToolbarLoading(isLoading: Boolean) {
        if(isLoading){
            toolbar_progress_bar.visibility = View.VISIBLE
        }
        else{
            toolbar_progress_bar.visibility = View.GONE
        }
    }

    private fun displayMainProgressBar(isLoading: Boolean){
        if(isLoading){
            main_progress_bar.visibility = View.VISIBLE
        }
        else{
            main_progress_bar.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        tool_bar.setupWithNavController(
            findNavController(R.id.nav_host_fragment)
        )
    }

    override fun showCategoriesMenu(categories: List<Category>) {
        val menu = tool_bar.menu
        for(category in categories){
            menu.add(category.category_name)
        }
        tool_bar.invalidate()
        onCreateOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        printLogD(CLASS_NAME, "cLICKED")
        return super.onOptionsItemSelected(item)
    }

    override fun hideCategoriesMenu() {
        tool_bar.menu.clear()
        tool_bar.invalidate()
    }

}


















