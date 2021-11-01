package com.vm.frameworkexample.components.main.profile.updateuser

import androidx.lifecycle.SavedStateHandle
import com.vm.frameworkexample.CoroutineTest
import com.vm.frameworkexample.MVVMExampleCurrentUser
import com.vm.frameworkexample.Utils.FAKE_USER
import com.vm.frameworkexample.api.MainApi
import com.vm.frameworkexample.di.main.MainEnvironment
import com.vm.frameworkexample.models.ReqResUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class UpdateUserViewModelTest : CoroutineTest() {

    lateinit var viewModel: UpdateUserViewModel

    @Before
    fun setUp() {
        val environment = mock(MainEnvironment::class.java)
        `when`(environment.api).thenReturn(mock(MainApi::class.java))
        `when`(environment.currentUser)
            .thenReturn(mock(MVVMExampleCurrentUser::class.java))

        val savedStateHandle = SavedStateHandle().apply {
            set("user", FAKE_USER)
        }

        viewModel = UpdateUserViewModel(environment, savedStateHandle)
    }

    @Test
    fun `save user`() = coTest {
        val spyVM = spy(viewModel)
        val mockForm = mock(ReqResUser.Form::class.java)
        doReturn(mockForm).`when`(spyVM).form
        doReturn(FAKE_USER).`when`(mockForm).requiredBuild()
        Assert.assertEquals(FAKE_USER, spyVM.save().first())
    }
}