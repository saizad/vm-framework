package com.vm.frameworkexample.components.main.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.vm.framework.enums.DataState
import com.vm.framework.utils.*
import com.vm.frameworkexample.ApiRequestCodes
import com.vm.frameworkexample.ApiRequestCodes.DEFAULT_ERROR
import com.vm.frameworkexample.ApiRequestCodes.DELAYED_RESPONSE
import com.vm.frameworkexample.ApiRequestCodes.RANDOM_REQUEST
import com.vm.frameworkexample.ApiRequestCodes.SHORT_DELAYED_RESPONSE
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainFragment
import com.vm.frameworkexample.databinding.FragmentHomeBinding
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
open class HomeFragment : MainFragment<HomeViewModel>() {


    @Inject
    lateinit var environment: MainEnvironment

    lateinit var binding: FragmentHomeBinding

    override val viewModelClassType: Class<HomeViewModel>
        get() = HomeViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)
        binding = FragmentHomeBinding.bind(view)

        binding.login.throttleClick {
            lifecycleScopeOnMain {
                currentUserType.logout {  }
            }
        }

        binding.noContentRequest.throttleClick {
            lifecycleScopeOnMain {
                viewModel().noContentResponse()
                    .loadingState {
                        vmFrameworkExampleActivity.showApiRequestLoadingDialog(it.isLoading)
                    }
                    .noContentStateToData()
                    .collect {
                        showShortToast("No Content")
                    }
            }
        }

        binding.resNotFoundErrorResponse.throttleClick {
            lifecycleScopeOnMain {
                viewModel().resourceNotFound(ApiRequestCodes.RESOURCE_NOT_FOUND)
                    .collect {
                        if (it is DataState.ApiError) {
                            showShortToast(it.apiErrorException.errorModel.message())
                        }
                    }
            }
        }

        binding.multiRequests.throttleClick {
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
                            else -> {

                            }
                        }
                    }
            }

            lifecycleScopeOnMain {
                viewModel().delayed(6, SHORT_DELAYED_RESPONSE)
                    .dataModel()
                    .collect {
                        showShortToast(it.data.size)
                    }
            }
        }

        binding.requestNoLoading.throttleClick {
            lifecycleScopeOnMain {
                viewModel().delayed(1, RANDOM_REQUEST)
                    .dataModel()
                    .collect {
                        showShortToast(it.data.size)
                    }
            }
        }

        binding.defaultError.throttleClick {
            lifecycleScopeOnMain {
                viewModel().resourceNotFound(DEFAULT_ERROR)
                    .collect {}
            }
        }

        lifecycleScopeOnMain {
            viewModel().x.collect {
                showShortToast(it.fullName)
            }
        }

        lifecycleScopeOnMain {
            currentUserType.loggedInUser()
                .collect {
                    bindUserInfo(it)
                }
        }

        binding.users.throttleClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUsersFragment())
        }

        binding.autoFillWebView.throttleClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAutoFillWebFragment())
        }

    }

    private fun bindUserInfo(reqResUser: ReqResUser) {
        val reqResUserItem = binding.currentUser.container
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

