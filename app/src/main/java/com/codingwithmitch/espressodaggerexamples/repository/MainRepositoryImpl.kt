package com.codingwithmitch.espressodaggerexamples.repository

import com.codingwithmitch.espressodaggerexamples.api.ApiService
import com.codingwithmitch.espressodaggerexamples.models.BlogPost
import com.codingwithmitch.espressodaggerexamples.models.Category
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainStateEvent.*
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState
import com.codingwithmitch.espressodaggerexamples.ui.viewmodel.state.MainViewState.*
import com.codingwithmitch.espressodaggerexamples.util.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class  MainRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService
) : MainRepository{

    private val CLASS_NAME = "MainRepository"

    override fun getBlogs(category: String): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getBlogPosts(category)}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return SearchBlogsByCategory(category).toString()
                    }

                }.result
            )
        }
    }

    override fun getAllBlogs(): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getAllBlogPosts()}

            emit(
                object: ApiResponseHandler<MainViewState, List<BlogPost>>(
                    IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<BlogPost>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = ListFragmentView(
                                    blogs = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return GetAllBlogs().toString()
                    }

                }.result
            )
        }
    }

    override fun getCategories(): Flow<DataState<MainViewState>> {
        return flow{

            val response = safeApiCall(IO){apiService.getCategories()}

            emit(
                object: ApiResponseHandler<MainViewState, List<Category>>(
                    IO,
                    response
                ) {
                    override fun handleSuccess(resultObj: List<Category>): DataState<MainViewState> {
                        return DataState.data(
                            data = MainViewState(
                                listFragmentView = ListFragmentView(
                                    categories = resultObj
                                )
                            ),
                            stateEventName = stateEventName()
                        )
                    }

                    override fun stateEventName(): String {
                        return GetCategories().toString()
                    }

                }.result
            )
        }
    }

}













