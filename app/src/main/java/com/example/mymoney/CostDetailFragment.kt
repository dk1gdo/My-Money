package com.example.mymoney

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.mymoney.data.DatesTypeConverter
import com.example.mymoney.data.model.Cost
import com.example.mymoney.data.model.CostType
import com.example.mymoney.data.toEditable

class CostDetailFragment : Fragment(){
    lateinit var etDescription: EditText
    lateinit var etCost: EditText
    lateinit var etDate: EditText
    lateinit var spnTypes: Spinner
    /*lateinit var tbCostDetail: androidx.appcompat.widget.Toolbar*/
    private lateinit var btnSetDate: Button
    private var costLoaded = false
    private var typesLoaded = false

    private var currentCost: Cost = Cost(0, 0, 0F)
    private val viewModel: CostDetailViewModel by viewModels()

    private lateinit var typesAdapter: TypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val costId = it.getInt("ID", 0)
            viewModel.loadCost(costId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*tbCostDetail.menu.clear()
        tbCostDetail.inflateMenu(R.menu.main_menu)*/
        viewModel.costTypesLiveData.observe(viewLifecycleOwner) { typeList ->
            typeList?.let {
                typesAdapter = TypesAdapter(it)
                updateUI()
            }
        }
        viewModel.costLiveData.observe(viewLifecycleOwner) {cost ->
            cost?.let {
                currentCost = it
                costLoaded = true

                updateUI()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cost_detail, container, false)
        etDescription = view.findViewById(R.id.etDescription)
        etCost = view.findViewById(R.id.etCost)
        etDate = view.findViewById(R.id.etDate)
        spnTypes = view.findViewById(R.id.spnTypes)
        btnSetDate = view.findViewById(R.id.btnSetDate)
       /* tbCostDetail = view.findViewById(R.id.tbCostDetail)*/
        return view
    }

    override fun onStart() {
        super.onStart()
        btnSetDate.setOnClickListener {
            Log.d("try", "ok!!!")
            DatePickerFragment.newInstance(currentCost.buyDate).apply {
                show(this@CostDetailFragment.parentFragmentManager, "DATE")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener("resultDate"){requestKey, bundle ->
            currentCost.buyDate = DatesTypeConverter().toDate(bundle.getString("date"))!!
            etDate.text = DatesTypeConverter().fromDate(currentCost.buyDate)?.toEditable() 
        }
    }
    override fun onStop() {
        super.onStop()
        currentCost.cost = try {
            etCost.text.toString().toFloat()
        } catch (e: NumberFormatException){
            0F
        }
        currentCost.description = etDescription.text.toString()
        currentCost.typeId = spnTypes.selectedItemId.toInt()
        if (currentCost.description.isBlank() && currentCost.cost == 0F)
            viewModel.killCost(currentCost) else viewModel.saveCost(currentCost)
    }


    companion object {
        fun newInstance(id: Int) =  CostDetailFragment().apply {
           arguments = Bundle().apply {
               putInt("ID", id)
           }
        }
    }

    private fun updateUI(){
        if (costLoaded){
            etDescription.text = currentCost.description.toEditable()
            etCost.text = currentCost.cost.toString().toEditable()
            etDate.text = DatesTypeConverter().fromDate(currentCost.buyDate)?.toEditable()

        }
        if (typesLoaded){
            spnTypes.setSelection(typesAdapter.getPosition(currentCost.typeId))
        }
    }
    inner class TypesAdapter(private val items:List<CostType>): BaseAdapter() {
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        override fun getCount(): Int = items.size
        override fun getItem(pos: Int): String = items[pos].title
        override fun getItemId(pos: Int): Long = items[pos].id.toLong()
        override fun getView(pos: Int, cView: View?, parent: ViewGroup): View {
            var view = cView
            if (view == null) {
                view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
            }
            val item = getItem(pos)
            val text: TextView = view!!.findViewById(android.R.id.text1)
            text.text = item
            return view
        }
        fun getPosition(id: Int) : Int {
            var i = 0
            items.forEach {
                if (it.id == id) return i
                i++
            }
            return -1
        }
    }


}