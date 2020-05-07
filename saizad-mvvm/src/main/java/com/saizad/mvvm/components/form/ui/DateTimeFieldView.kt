package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.FragmentManager
import com.sa.easyandroidfrom.ObjectUtils
import com.sa.easyandroidfrom.field_view.BaseFieldView
import com.sa.easyandroidfrom.fields.time.DateField
import com.sa.easyandroidfrom.fields.time.DateTimeField
import com.sa.easyandroidfrom.fields.time.TimeField
import com.saizad.mvvm.ui.calendar.DatePickerFragment
import com.saizad.mvvm.ui.calendar.DateTimePickerFragment
import com.saizad.mvvm.ui.calendar.TimePickerFragment
import com.saizad.mvvm.utils.bindClick
import io.reactivex.functions.Consumer
import org.joda.time.DateTime
import rx.functions.Action1

abstract class DateTimeFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseFieldView<String>(context, attrs, defStyleAttr) {

    private lateinit var dateTimeField: DateTimeField
    var action1: Action1<Pair<String?, Boolean>> = Action1 { }
    protected abstract var dateTimeView: DateTimeViewDelegate

    open fun setupTime(fragmentManager: FragmentManager, dateFieldDate: TimeField) {
        setup(dateFieldDate)
        dateTimeView.timeView()
        dateTimeView.getTimeView().bindClick(Consumer {
            timePicker(fragmentManager, dateFieldDate)
        })
    }

    open fun setupDate(fragmentManager: FragmentManager, dateFieldDate: DateField) {
        setup(dateFieldDate)
        dateTimeView.dateView()
        dateTimeView.getDateView().bindClick(Consumer {
            datePicker(fragmentManager, dateFieldDate)
        })
    }

    open fun setupDateTime(fragmentManager: FragmentManager, dateFieldDate: DateTimeField) {
        setup(dateFieldDate)
        dateTimeView.dateTimeView()
        dateTimeView.getDateView().bindClick(Consumer {
            if (dateFieldDate.isSet) {
                datePicker(fragmentManager, dateFieldDate)
            } else {
                dateTimePicker(fragmentManager, dateFieldDate)
            }
        })
        dateTimeView.getTimeView().bindClick(Consumer {
            if (dateFieldDate.isSet) {
                timePicker(fragmentManager, dateFieldDate)
            } else {
                dateTimePicker(fragmentManager, dateFieldDate)
            }
        })
    }

    private fun datePicker(fragmentManager: FragmentManager, dateFieldDate: DateTimeField) {
        DatePickerFragment(ObjectUtils.coalesce(dateFieldDate.dateTime(), DateTime()), DateTime()) {
            dateFieldDate.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun timePicker(fragmentManager: FragmentManager, dateFieldDate: DateTimeField) {
        TimePickerFragment(ObjectUtils.coalesce(dateFieldDate.dateTime(), DateTime())) {
            dateFieldDate.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun dateTimePicker(fragmentManager: FragmentManager, dateFieldDate: DateTimeField) {
        DateTimePickerFragment(
            ObjectUtils.coalesce(dateFieldDate.dateTime(), DateTime()),
            DateTime()
        ) {
            dateFieldDate.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun setup(dateFieldDate: DateTimeField) {
        this.dateTimeField = dateFieldDate
        setField(dateFieldDate)
    }

    override fun showValue(field: String?) {
        dateTimeView.bind(dateTimeField.dateTime())
    }

    override fun fieldMandatory() {
        dateTimeView.getDateView().setMandatory(true)
        dateTimeView.getTimeView().setMandatory(true)
    }

    override fun error() {

    }

    override fun edited() {
    }

    override fun neutral() {
    }

    override fun displayError(show: Boolean, error: String?) {
        dateTimeView.getDateView().setError(show)
        dateTimeView.getTimeView().setError(show)
        action1.call(Pair(error, show))
    }
}