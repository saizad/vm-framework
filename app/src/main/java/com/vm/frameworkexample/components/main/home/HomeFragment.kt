package com.vm.frameworkexample.components.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.vm.framework.enums.DataState
import com.vm.framework.utils.lifecycleScopeOnMain
import com.vm.framework.utils.noContentStateToData
import com.vm.framework.utils.stateToData
import com.vm.framework.utils.throttleClick
import com.vm.frameworkexample.ApiRequestCodes.DEFAULT_ERROR
import com.vm.frameworkexample.ApiRequestCodes.DELAYED_RESPONSE
import com.vm.frameworkexample.ApiRequestCodes.RANDOM_REQUEST
import com.vm.frameworkexample.ApiRequestCodes.SHORT_DELAYED_RESPONSE
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainFragment
import com.vm.frameworkexample.components.main.users.ReqResUserItem
import com.vm.frameworkexample.models.ReqResUser
import com.vm.frameworkexample.service.SampleWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : MainFragment<HomeViewModel>() {

    private val reqResUserItem by lazy { currentUser as ReqResUserItem }

    override val viewModelClassType: Class<HomeViewModel>
        get() = HomeViewModel::class.java


    companion object {
        const val SAMPLE_WORKER_TAG = "sample_worker"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)

        logout.throttleClick {
            viewModel().logout()
        }

        noContentRequest.throttleClick {
            lifecycleScopeOnMain {
                viewModel().noContentResponse()
                    .noContentStateToData()
                    .collect {
                        showShortToast("No Content")
                    }
            }
        }

        resNotFoundErrorResponse.throttleClick {
            lifecycleScopeOnMain {
                viewModel().resourceNotFound()
                    .collect {
                        if (it is DataState.ApiError) {
                            showShortToast(it.apiErrorException.errorModel.message())
                        }
                    }
            }
        }

        multiRequests.throttleClick {
            lifecycleScopeOnMain {
                viewModel().delayed(5, DELAYED_RESPONSE)
                    .collect {
                        when (it) {
                            is DataState.Success -> {
                                showShortToast(it.data!!.data.size)
                            }
                            is DataState.Loading -> {
                                if (it.isLoading) {
                                    showShortToast("Loading....")
                                } else {
                                    showShortToast("Loading Completed!!")
                                }
                            }
                        }
                    }
            }

            lifecycleScopeOnMain {
                viewModel().delayed(6, SHORT_DELAYED_RESPONSE)
                    .stateToData()
                    .collect {
                        showShortToast(it.data.size)
                    }
            }
        }

        requestNoLoading.throttleClick {
            lifecycleScopeOnMain {
                viewModel().delayed(1, RANDOM_REQUEST)
                    .stateToData()
                    .collect {
                        showShortToast(it.data.size)
                    }
            }
        }

        defaultError.throttleClick {
            lifecycleScopeOnMain {
                viewModel().resourceNotFound(DEFAULT_ERROR)
                    .collect {}
            }
        }


        currentUserType.loggedInUser()
            .observe(viewLifecycleOwner, {
                bindUserInfo(it)
            })

        users.throttleClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUsersFragment())
        }

        runSampleWorker()
    }

    private fun runSampleWorker() {
        Log.d(SAMPLE_WORKER_TAG, "RunWorkManager")

        val oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(SampleWorker::class.java)
                .addTag(SAMPLE_WORKER_TAG)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(requireContext()).beginWith(oneTimeWorkRequest).enqueue()

        WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observeForever{

            }
    }

    private fun bindUserInfo(reqResUser: ReqResUser) {
        reqResUserItem.bind(reqResUser)
        reqResUserItem.throttleClick {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToUpdateUserFragment(
                    reqResUser
                )
            )
        }
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }
}

