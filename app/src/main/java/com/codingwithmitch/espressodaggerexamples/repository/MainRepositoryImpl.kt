package com.codingwithmitch.espressodaggerexamples.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.MainViewModel
import com.codingwithmitch.espressodaggerexamples.ui.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.ui.state.MainViewState.*
import com.codingwithmitch.espressodaggerexamples.ui.state.NetworkBoundResource
import com.codingwithmitch.espressodaggerexamples.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class  MainRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : MainRepository{

    private val CLASS_NAME = "MainRepository"

    override fun getAllBlogs(coroutineScope: CoroutineScope): LiveData<DataState<MainViewState>> {
        return launchlive

    }


//    override fun getAllBlogs(coroutineScope: CoroutineScope): LiveData<DataState<MainViewState>> {
//        return object: NetworkBoundResource<MainViewState, List<BlogPost>>(
//            coroutineScope
//        ){
//            override suspend fun getApiRequest(): List<BlogPost> {
//                return apiService.getAllBlogPosts()
//            }
//
//            override fun onSuccess(data: List<BlogPost>) {
//                result.value = DataState.data(
//                    MainViewState(
//                        ListFragmentView(blogs = data)
//                    )
//                )
//            }
//
//            override fun onGenericError(errorMessage: String) {
//                result.value = DataState.error(errorMessage)
//            }
//
//            override fun onNetworkError() {
//                result.value = DataState.error(NETWORK_ERROR)
//            }
//
//        }.getAsLiveData()
//    }

//    override suspend fun getAllBlogs(): ApiResult<List<BlogPost>> {
//        return safeApiCall(IO){apiService.getAllBlogPosts()}
//    }

    override suspend fun getBlogs(category: String): ApiResult<List<BlogPost>> {
        return safeApiCall(IO){ apiService.getBlogPosts(category) }
    }


    override suspend fun getCategories(): ApiResult<List<Category>> {
        return safeApiCall(IO){apiService.getCategories()}
    }
}













