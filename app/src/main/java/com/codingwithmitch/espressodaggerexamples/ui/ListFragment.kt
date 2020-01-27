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
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.ui.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.util.Event
import com.codingwithmitch.espressodaggerexamples.util.TopSpacingItemDecoration
import com.codingwithmitch.espressodaggerexamples.util.printLogD
import com.codingwithmitch.espressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.ClassCastException
import javax.inject.Inject
import javax.inject.Singleton

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

    lateinit var dataStateListener: DataStateListener

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var listAdapter: BlogPostListAdapter

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeObservers()
        swipe_refresh.setOnRefreshListener(this)
        initData()
    }

    private fun initData(){
//        viewModel.getAllBlogs()
//        viewModel.getCategories()

        viewModel.setStateEvent(GetAllBlogs())
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            if(dataState != null){
                dataStateListener.onDataStateChange(dataState)

                dataState.data?.getContentIfNotHandled()?.let { data ->
                    viewModel.handleDataEvent(data)
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if(viewState != null){

                viewState.listFragmentView.let{ view ->
                    view.blogs.let { blogs ->
                        listAdapter.submitList(blogs)
                    }
                    view.categories.let { categories ->
                        uiCommunicationListener.showCategoriesMenu(categories = categories)
                    }
                }
            }
        })

//        viewModel.blogs.observe(viewLifecycleOwner, Observer { dataState ->
//            if(dataState != null){
//                dataStateListener.onDataStateChange(dataState)
//
//                dataState.data?.let { dataEvent ->
//                    handleIncomingBlogPosts(dataEvent)
//                }
//            }
//        })
//
//        viewModel.categories.observe(viewLifecycleOwner, Observer { dataState ->
//            if(dataState != null){
//                dataStateListener.onToolbarLoading(dataState.loading.isLoading)
//
//                dataState.data?.let { dataEvent ->
//                    handleIncomingCategories(dataEvent)
//                }
//            }
//        })
    }

    override fun onRefresh() {
        initData()
        swipe_refresh.isRefreshing = false
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            listAdapter = BlogPostListAdapter(this@ListFragment)
            addItemDecoration(TopSpacingItemDecoration(30))
            adapter = listAdapter
        }
    }

    private fun handleIncomingCategories(dataEvent: Event<List<Category>>){
        dataEvent.getContentIfNotHandled()?.let{ categories ->
            uiCommunicationListener.showCategoriesMenu(categories = categories)
        }
    }

    private fun handleIncomingBlogPosts(dataEvent: Event<List<BlogPost>>){
        dataEvent.getContentIfNotHandled()?.let { blogs ->
            listAdapter.submitList(blogs)
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as BaseApplication)
            .getAppComponent()
            .inject(this)
        super.onAttach(context)

        try{
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            printLogD(CLASS_NAME, "$context must implement DataStateListener")
        }

        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch (e: ClassCastException){
            printLogD(CLASS_NAME, "$context must implement UICommunicationListener")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        viewModel.setSelectedBlogPost(blogPost = item)
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }
}





















