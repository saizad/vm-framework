package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sa.easyandroidfrom.field_view.BaseFieldView
import com.sa.easyandroidfrom.fields.FileField
import com.saizad.mvvm.SaizadRequestCodes
import com.saizad.mvvm.components.SaizadBaseViewModel
import com.saizad.mvvm.utils.bindClick
import io.reactivex.functions.Consumer
import sa.zad.easypermission.AppPermission
import sa.zad.easypermission.AppPermissionRequest
import sa.zad.easypermission.PermissionManager
import sa.zad.easyretrofit.Utils
import sa.zad.easyretrofit.base.ProgressObservable
import sa.zad.easyretrofit.observables.UploadObservable
import java.io.File


abstract class UriFieldView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseFieldView<Uri>(context, attrs, defStyleAttr) {
    
    private lateinit var parentFragment: Fragment
    private lateinit var baseViewModel: SaizadBaseViewModel
    private lateinit var imageField: FileField
    private lateinit var permissionManager: PermissionManager
    private lateinit var storagePermission: AppPermission
    private var undo: View? = null
    private var clear: View? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        undo = undoView()
        clear = clearView()

        initView().bindClick(Consumer {
            init()
        })
        if(undo != null) {
            undo!!.bindClick(Consumer {
                reset()
            })
        }
        if (clear != null) {
            clear!!.bindClick(Consumer {
                imageField.field = null
            })
        }
    }

    fun reset(){
        imageField.field = imageField.ogField
        showValue(imageField.ogField)
    }

    fun setup(
        fragment: Fragment,
        baseViewModel: SaizadBaseViewModel,
        permissionManager: PermissionManager,
        permCode: Int,
        fileField: FileField
    ) {
        this.storagePermission = permissionManager.getAppPermission(permCode)
        parentFragment = fragment
        this.baseViewModel = baseViewModel
        this.permissionManager = permissionManager
        this.imageField = fileField
        setField(fileField)
        init(baseViewModel)

    }

    fun <T> upload(uploadObservable: UploadObservable<T>): ProgressObservable<T> {
        return uploadObservable
            .onProgressStart({
            }, 1000, 1000, 100)
            .progressUpdate {
            }
            .onProgressCompleted {
            }
    }

    private fun init(baseViewModel: SaizadBaseViewModel) {
        baseViewModel.onNavigationResult(SaizadRequestCodes.PICK_FILE, Intent::class.java)
            .observe(parentFragment.viewLifecycleOwner, Observer {
                val fileName = getFileName(it.data!!)
                val file = File.createTempFile("$fileName _", "." + File(fileName).extension)
                Utils.writeStreamToFile(context.contentResolver.openInputStream(it.data!!)!!, file)
                val uri = Uri.fromFile(file)
                imageField.field = uri
            })
    }
    
    protected fun init(){
        requestPerm()
    }

    private fun requestPerm() {
        permissionManager.requestPermission(parentFragment, storagePermission.permissionCode)
            .filter {
                if (it.requestStatus == AppPermissionRequest.REQUEST_CANCELED) {
                    //open app setting page
                }
                it.requestStatus == AppPermissionRequest.ALL_READY_GRANTED || it.requestStatus == AppPermissionRequest.GRANTED
            }
            ?.subscribe({
                openPicker()
            }, {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = this.context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private fun openPicker() {
        parentFragment.activity?.startActivityForResult(intent(), SaizadRequestCodes.PICK_FILE)
    }
    
    abstract fun intent(): Intent

    override fun error() {
    }

    override fun edited() {
    }

    override fun neutral() {
    }

    override fun displayError(show: Boolean, error: String?) {

    }

    @CallSuper
    override fun showValue(field: Uri?) {
        if(undo != null) {
            undo!!.isVisible = imageField.isModified
        }
        if(clear != null) {
            clear!!.isVisible = !imageField.isMandatory && field != null
        }
    }

    override fun fieldMandatory() {
        if(clear != null) {
            clear!!.isVisible = false
        }
    }

    open fun clearView(): View?{
        return null
    }
    open fun undoView(): View?{
        return null
    }
    abstract fun initView(): View
}
