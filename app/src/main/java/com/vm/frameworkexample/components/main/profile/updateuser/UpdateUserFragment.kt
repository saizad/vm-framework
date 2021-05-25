package com.vm.frameworkexample.components.main.profile.updateuser

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.vm.framework.ActivityResult
import com.vm.framework.utils.addToComposite
import com.vm.framework.utils.throttleClick
import com.vm.frameworkexample.R
import com.vm.frameworkexample.RequestCodes
import com.vm.frameworkexample.components.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_update_user.*

@AndroidEntryPoint
class UpdateUserFragment : MainFragment<UpdateUserViewModel>() {

    override val viewModelClassType: Class<UpdateUserViewModel>
        get() = UpdateUserViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_update_user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val form = viewModel().form
        fullNameField.setField(form.fullNameField)
        jobField.setField(form.jobField)

        save.throttleClick {
            viewModel().save().observe(viewLifecycleOwner, {
                finishWithResult(ActivityResult(RequestCodes.USER, it))
            })
        }

        form.validObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                save.isEnabled = it
            }.addToComposite(this)

    }
}
