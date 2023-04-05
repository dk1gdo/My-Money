package com.example.mymoney

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoney.data.DatesTypeConverter
import com.example.mymoney.data.model.Cost

class CostListAdapter(context: Context?, private val costs: List<Cost>) :
    RecyclerView.Adapter<CostListAdapter.CostViewHolder?>() {

    private var iClickListener: ItemClickListener? = null

    fun setClickListener(itemClickListener: ItemClickListener?){
        iClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, costId: Int)
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        val view: View = layoutInflater.inflate(R.layout.cost_item, parent, false)
        return CostViewHolder(view)
    }
    override fun getItemCount(): Int = costs.size
    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        holder.bind(costs[position])
    }
    inner class CostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var cost: Cost
        var tvDate: TextView = itemView.findViewById(R.id.item_date)
        var tvPrice: TextView = itemView.findViewById(R.id.item_price)

        fun bind(costItem: Cost) {
            cost = costItem
            tvDate.text = DatesTypeConverter().fromDate(costItem.buyDate)
            tvPrice.text = costItem.cost.toString()
            itemView.setOnClickListener {
                iClickListener?.onItemClick(itemView, cost.id)
            }
        }
    }
}