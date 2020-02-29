package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.fragments.MainNavHostFragment
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ListFragment
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
) : Fragment(R.layout.fragment_list),
    BlogPostListAdapter.Interaction,
    SwipeRefreshLayout.OnRefreshListener
{

    private val CLASS_NAME = "ListFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var listAdapter: BlogPostListAdapter

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh.setOnRefreshListener(this)
        initRecyclerView()
        subscribeObservers()
        initData()
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    private fun saveLayoutManagerState(){
        recycler_view.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.setLayoutManagerState(lmState)
        }
    }

    fun restoreLayoutManager() {
        viewModel.getLayoutManagerState()?.let { lmState ->
            recycler_view?.layoutManager?.onRestoreInstanceState(lmState)
        }
    }

    private fun initData(){
        val viewState = viewModel.getCurrentViewStateOrNew()
        if(viewState.listFragmentView.blogs == null
            || viewState.listFragmentView.categories == null){
            viewModel.setStateEvent(GetAllBlogs())
            viewModel.setStateEvent(GetCategories())
        }
    }

    /*
     I'm creating an observer in this fragment b/c I want more control
     over it. When a blog is selected I immediately stop observing.
     Mainly for hiding the menu in DetailFragment.
     "uiCommunicationListener.hideCategoriesMenu()"
    */
    val observer: Observer<MainViewState> = Observer { viewState ->
        if(viewState != null){

            viewState.listFragmentView.let{ view ->
                view.blogs?.let { blogs ->
                    listAdapter.apply {
                        submitList(blogs)
                    }
                    displayTheresNothingHereTV((blogs.size > 0))
                }
                view.categories?.let { categories ->
                    uiCommunicationListener.showCategoriesMenu(
                        categories = ArrayList(categories)
                    )
                }
            }
        }
    }

    private fun displayTheresNothingHereTV(isDataAvailable: Boolean){
        if(isDataAvailable){
            no_data_textview.visibility = View.GONE
        }
        else{
            no_data_textview.visibility = View.VISIBLE
        }
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, observer)
    }

    override fun onRefresh() {
        initData()
        swipe_refresh.isRefreshing = false
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            addItemDecoration(TopSpacingItemDecoration(30))
            listAdapter = BlogPostListAdapter(requestManager, this@ListFragment)
            adapter = listAdapter
        }
    }

    override fun restoreListPosition() {
        restoreLayoutManager()
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        removeViewStateObserver()
        viewModel.setSelectedBlogPost(blogPost = item)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }

    private fun removeViewStateObserver(){
        viewModel.viewState.removeObserver(observer)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUICommunicationListener(null)
    }


    fun setUICommunicationListener(mockUICommuncationListener: UICommunicationListener?){

        // TEST: Set interface from mock
        if(mockUICommuncationListener != null){
            this.uiCommunicationListener = mockUICommuncationListener
        }
        else{ // PRODUCTION: if no mock, get from context
            try {
                uiCommunicationListener = (context as UICommunicationListener)
            }catch (e: Exception){
                Log.e(CLASS_NAME, "$context must implement UICommunicationListener")
            }
        }
    }

}
