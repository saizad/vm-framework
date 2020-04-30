package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sa.easyandroidfrom.ObjectUtils
import com.sa.easyandroidfrom.field_view.BaseFieldView
import com.sa.easyandroidfrom.fields.FileField
import com.saizad.mvvm.R
import com.saizad.mvvm.SaizadRequestCodes
import com.saizad.mvvm.components.SaizadBaseViewModel
import com.saizad.mvvm.utils.ImageUtils
import com.saizad.mvvm.utils.ViewUtils
import kotlinx.android.synthetic.main.lib_image_field_view.view.*
import sa.zad.easypermission.AppPermission
import sa.zad.easypermission.AppPermissionRequest
import sa.zad.easypermission.PermissionManager
import sa.zad.easyretrofit.base.ProgressObservable
import sa.zad.easyretrofit.observables.UploadObservable


class ImageFieldView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseFieldView<Uri>(context, attrs, defStyleAttr) {

    override fun showValue(field: Uri?) {
        showSelectedImage(field)
        undo_avatar.isVisible = formField.isModified
    }

    override fun displayError(show: Boolean, error: String?) {

    }

    private lateinit var parentFragment: Fragment
    private lateinit var baseViewModel: SaizadBaseViewModel
    private lateinit var formField: FileField
    private lateinit var permissionManager: PermissionManager
    private lateinit var locationPermission: AppPermission

    init {

        ViewUtils.inflate(getContext(), R.layout.lib_image_field_view, this, true)

        ViewUtils.bindClick(undo_avatar) {
            formField.field = formField.ogField
            showSelectedImage(formField.ogField)
        }
    }

    fun setup(
        fragment: Fragment,
        baseViewModel: SaizadBaseViewModel,
        permissionManager: PermissionManager,
        locationPermission: AppPermission,
        fileField: FileField
    ) {
        this.locationPermission = locationPermission
        parentFragment = fragment
        this.baseViewModel = baseViewModel
        this.permissionManager = permissionManager
        this.formField = fileField
        setField(fileField)
        init(baseViewModel)

    }

    fun <T> uploadImage(uploadObservable: UploadObservable<T>): ProgressObservable<T> {
        return uploadObservable
            .onProgressStart({
            }, 1000, 1000, 100)
            .progressUpdate {
                upload_progress_loading.visibility = View.GONE
                upload_progress.progress = it.progress.toInt()
            }
            .onProgressCompleted {
                upload_progress.progress = Math.ceil(it.progress.toDouble()).toInt()
            }
    }

    private fun init(baseViewModel: SaizadBaseViewModel) {
        if (ObjectUtils.isNotNull(parentFragment)) {
            ViewUtils.bindClick(edit_avatar) {
                requestPerm()
            }
        }
        baseViewModel.onNavigationResult(SaizadRequestCodes.PICK_AVATAR, Intent::class.java)
            .observe(parentFragment.viewLifecycleOwner, Observer {
                val realPathFromURIPath = getRealPathFromURIPath(it.data!!)
                val uri = Uri.parse(realPathFromURIPath)
                formField.field = uri
            })
    }

    private fun requestPerm() {
        permissionManager.requestPermission(parentFragment, locationPermission.permissionCode)
            .filter {
                if (it.requestStatus == AppPermissionRequest.REQUEST_CANCELED) {
                    //open app setting page
                }
                it.requestStatus == AppPermissionRequest.ALL_READY_GRANTED || it.requestStatus == AppPermissionRequest.GRANTED
            }
            ?.subscribe({
                pic()
            }, {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
    }

    private fun getRealPathFromURIPath(contentURI: Uri): String {
        val cursor = this.context?.contentResolver?.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            val path = cursor.getString(idx)
            cursor.close()
            path
        }
    }

    private fun pic() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("outputX", 256)
        intent.putExtra("outputY", 256)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("return-customers", true)
        parentFragment.activity?.startActivityForResult(intent, SaizadRequestCodes.PICK_AVATAR)
    }


    private fun showSelectedImage(uri: Uri?) {
        ImageUtils.setAvatarImage(image_view, uri.toString())
    }

    override fun fieldMandatory() {
    }

    override fun error() {
    }

    override fun edited() {
    }

    override fun neutral() {
    }
}
