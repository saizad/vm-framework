package com.saizad.mvvm.components.form.ui

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.FragmentManager
import com.sa.easyandroidform.ObjectUtils
import com.sa.easyandroidform.field_view.BaseFieldView
import com.sa.easyandroidform.fields.time.DateField
import com.sa.easyandroidform.fields.time.DateTimeField
import com.sa.easyandroidform.fields.time.EndDateTimeField
import com.sa.easyandroidform.fields.time.TimeField
import com.saizad.mvvm.ui.calendar.DatePickerFragment
import com.saizad.mvvm.ui.calendar.DateTimePickerFragment
import com.saizad.mvvm.ui.calendar.TimePickerFragment
import com.saizad.mvvm.utils.bindClick
import io.reactivex.functions.Consumer
import org.joda.time.DateTime
import rx.functions.Action1
import java.lang.Exception

abstract class DateTimeFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseFieldView<String>(context, attrs, defStyleAttr) {

    private lateinit var dateTimeField: DateTimeField
    var action1: Action1<Pair<String?, Boolean>> = Action1 { }
    public abstract var dateTimeView: DateTimeViewDelegate

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

        dateTimeView.getTimeView().bindClick(Consumer {
            timePicker(fragmentManager, dateTimeField)
        })

        dateTimeView.getDateTimeView().bindClick(Consumer {
            dateTimePicker(fragmentManager, dateFieldDate)
        })
    }

    private fun datePicker(fragmentManager: FragmentManager, dateTimeField: DateTimeField) {
        var minDateTime = dateTimeField.dateTime()

        if(dateTimeField is EndDateTimeField) {
            val dateTime = dateTimeField.startDateTimeField.dateTime()
            if(dateTime != null) {
                minDateTime = dateTime
            }
        }
        if(minDateTime == null){
            minDateTime = DateTime()
        }
        DatePickerFragment(ObjectUtils.coalesce(dateTimeField.dateTime(), minDateTime), minDateTime) {
            dateTimeField.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun timePicker(fragmentManager: FragmentManager, dateTimeField: DateTimeField) {
        var selectTime = dateTimeField.dateTime()

        if(dateTimeField is EndDateTimeField && selectTime == null) {
            val dateTime = dateTimeField.startDateTimeField.dateTime()
            if(dateTime != null) {
                selectTime = dateTime
            }
        }

        if(selectTime == null){
            selectTime = DateTime()
        }

        TimePickerFragment(selectTime) {
            dateTimeField.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun dateTimePicker(fragmentManager: FragmentManager, dateTimeField: DateTimeField) {
        var minDateTime = dateTimeField.dateTime()

        if(dateTimeField is EndDateTimeField) {
            val dateTime = dateTimeField.startDateTimeField.dateTime()
            if(dateTime != null) {
                minDateTime = dateTime
            }
        }
        if(minDateTime == null){
            minDateTime = DateTime()
        }
        DateTimePickerFragment(
            ObjectUtils.coalesce(dateTimeField.dateTime(), DateTime()),
            minDateTime
        ) {
            dateTimeField.field = it.toString()
        }.show(fragmentManager, "dateTimePicker")
    }

    private fun setup(dateFieldDate: DateTimeField) {
        this.dateTimeField = dateFieldDate
        setField(dateFieldDate)
    }

    override fun showValue(field: String?) {
        try {
            if(fieldItem.isSet) {
                dateTimeView.bind(DateTime(field))
                return
            }
        }catch (e: Exception){
        }
        dateTimeView.bind(null)
    }

    override fun fieldMandatory() {
        dateTimeView.getDateView().setMandatory(true)
        dateTimeView.getTimeView().setMandatory(true)
    }

    override fun displayError(show: Boolean, error: String?) {
        dateTimeView.getDateView().setError(show)
        dateTimeView.getTimeView().setError(show)
        action1.call(Pair(error, show))
    }
}