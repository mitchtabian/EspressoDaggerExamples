package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.util.ApiResponseHandler
import com.codingwithmitch.espressodaggerexamples.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockMainRepositoryImpl
constructor(
    private val apiService: ApiService
): MainRepository{

    private val CLASS_NAME = "MainRepository"

    override fun getBlogs(category: String): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getBlogPosts(category)}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    Dispatchers.IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return MainStateEvent.SearchBlogsByCategory(category).toString()
                    }

                }.result
            )
        }
    }

    override fun getAllBlogs(): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getAllBlogPosts()}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    Dispatchers.IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return MainStateEvent.GetAllBlogs().toString()
                    }

                }.result
            )
        }
    }

    override fun getCategories(): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(Dispatchers.IO){apiService.getCategories()}

            emit(
                object: ApiResponseHandler<MainViewState, List<Category>>(
                    Dispatchers.IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<Category>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = MainViewState.ListFragmentView(
                                    categories = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return MainStateEvent.GetCategories().toString()
                    }

                }.result
            )
        }
    }

}