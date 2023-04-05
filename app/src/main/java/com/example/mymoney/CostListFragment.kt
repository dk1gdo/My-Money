package com.example.mymoney

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class CostListFragment: Fragment() {
    private val viewModel: CostListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.menuAdd -> {
                        /*activity?.supportFragmentManager?.beginTransaction()?.
                            replace(R.id.fragment_container_view,
                                CostDetailFragment.newInstance(0))?.
                            addToBackStack(null)?.commit()*/
                        val bundle = Bundle().apply { putInt("ID", 0) }
                        findNavController().navigate(
                            R.id.action_costListFragment_to_costDetailFragment,
                            bundle)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val view = inflater.inflate(R.layout.fragment_cost_list, container, false)
        if (view is RecyclerView)
            viewModel.costsLiveData.observe(viewLifecycleOwner) {
                view.adapter = CostListAdapter(context, it).apply {
                    setClickListener(object : CostListAdapter.ItemClickListener{
                        override fun onItemClick(view: View?, costId: Int) {
                            val bundle = Bundle().apply { putInt("ID", costId) }
                            findNavController().navigate(
                                R.id.action_costListFragment_to_costDetailFragment,
                                bundle)
                        }
                    })
                }
            }
        return view
    }
}