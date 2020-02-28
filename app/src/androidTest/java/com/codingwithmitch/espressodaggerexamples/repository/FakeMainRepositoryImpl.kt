package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.api.FakeApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.ApiResponseHandler
import com.codingwithmitch.espressodaggerexamples.util.DataState
import com.codingwithmitch.espressodaggerexamples.util.StateEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * The only difference between this and the real MainRepositoryImpl is the ApiService is
 * fake and it's not being injected. This allows us to configure it at runtime.
 * That way I can alter the FakeApiService for each individual test.
 */
@Singleton
class FakeMainRepositoryImpl
@Inject
constructor(): MainRepository{

    lateinit var apiService: FakeApiService

    @Throws(UninitializedPropertyAccessException::class)
    override fun getBlogs(stateEvent: StateEvent, category: String): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialzied()
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getBlogPosts(category)}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getAllBlogs(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialzied()
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getAllBlogPosts()}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    @Throws(UninitializedPropertyAccessException::class)
    override fun getCategories(stateEvent: StateEvent): Flow<DataState<MainViewState>> {
        throwExceptionIfApiServiceNotInitialzied()
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getCategories()}

            emit(
                object: ApiResponseHandler<MainViewState, List<Category>>(
                    response = response,
                    stateEvent = stateEvent
                ) {
                    override fun handleSuccess(resultObj: List<Category>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    categories = resultObj
                                )
                            ),
                            stateEvent = stateEvent
                        )
                    }

                }.result
            )
        }
    }

    private fun throwExceptionIfApiServiceNotInitialzied(){
        if(!::apiService.isInitialized){
            throw UninitializedPropertyAccessException(
                "Did you forget to set the ApiService in FakeMainRepositoryImpl?"
            )
        }
    }

}




















