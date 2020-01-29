package com.codingwithmitch.espressodaggerexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    DataStateListener,
    UICommunicationListener
{

    private val CLASS_NAME = "MainActivity"

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication)
            .getAppComponent()
            .inject(this)
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
                    errorEvent.getContentIfNotHandled()?.let { error ->
                        displaySnackbar(error, Snackbar.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    override fun displayToastMessage(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }

    override fun displaySnackbar(message: String, length: Int) {
        Snackbar.make(this.window.decorView, message, length).show()
    }

    override fun onToolbarLoading(isLoading: Boolean) {
        if(isLoading){
            toolbar_progress_bar.visibility = View.VISIBLE
        }
        else{
            toolbar_progress_bar.visibility = View.GONE
        }
    }

    override fun displayMainProgressBar(isLoading: Boolean){
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
        menu.clear()
        for((index, category) in categories.withIndex()){
            menu.add(0, category.pk, index, category.category_name)
        }
        tool_bar.invalidate()
        tool_bar.setOnMenuItemClickListener { menuItem ->
           onMenuItemSelected(categories, menuItem)
        }
    }

    private fun onMenuItemSelected(categories: List<Category>, menuItem: MenuItem): Boolean{
        for(category in categories){
            if(category.pk == menuItem.itemId){
                viewModel.getBlogPosts(category = category.category_name)
                return true
            }
        }
        return false
    }

    override fun hideCategoriesMenu() {
        tool_bar.menu.clear()
        tool_bar.invalidate()
    }

    override fun hideToolbar() {
        tool_bar.visibility = View.GONE
    }

    override fun showToolbar() {
        tool_bar.visibility = View.VISIBLE
    }

    override fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        hideToolbar()
    }

    override fun showStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        showToolbar()
    }
}


















