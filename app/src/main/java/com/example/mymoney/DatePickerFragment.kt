package com.example.mymoney

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.mymoney.data.DatesTypeConverter
import java.time.Year
import java.util.Date

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("DATE", Date::class.java)
        } else {
            arguments?.getSerializable("DATE") as Date
        }
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get((Calendar.DAY_OF_MONTH))
        return DatePickerDialog(requireContext(), this, year, month,day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val resultDate: Date = GregorianCalendar(year, month, day).time
        val result = DatesTypeConverter().fromDate(resultDate)
        setFragmentResult("resultDate", bundleOf("date" to result))
    }
    companion object {
        fun newInstance(date: Date) = DatePickerFragment().apply {
            arguments = Bundle().apply {
                putSerializable("DATE", date)
            }
        }
    }
}