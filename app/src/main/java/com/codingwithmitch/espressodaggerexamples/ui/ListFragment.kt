package com.codingwithmitch.espressodaggerexamples.ui


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codingwithmitch.espressodaggerexamples.BaseApplication
import com.codingwithmitch.espressodaggerexamples.R
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.util.TopSpacingItemDecoration
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class ListFragment
@Inject
constructor(
    private val viewModelFactory: MainViewModelFactory
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


    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if(viewState != null){

                viewState.listFragmentView.let{ view ->
                    view.blogs?.let { blogs ->
                        listAdapter.apply {
                            submitList(blogs)
                        }
                    }
                    view.categories?.let { categories ->
                        uiCommunicationListener.showCategoriesMenu(
                            categories = ArrayList(categories)
                        )
                    }
                }
            }
        })
    }

    override fun onRefresh() {
        initData()
        swipe_refresh.isRefreshing = false
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            addItemDecoration(TopSpacingItemDecoration(30))
            listAdapter = BlogPostListAdapter(this@ListFragment)
            adapter = listAdapter
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .inject(this)
        super.onAttach(context)

        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch (e: ClassCastException){
            printLogD(CLASS_NAME, "$context must implement UICommunicationListener")
        }
    }

    override fun restoreListPosition() {
        restoreLayoutManager()
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        viewModel.setSelectedBlogPost(blogPost = item)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }

}





















