package com.coffeedose.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.coffeedose.databinding.FragmentSelectDoseAndAddinsBinding
import com.coffeedose.ui.Adapters.AddinCheckListener
import com.coffeedose.ui.Adapters.AddinsListAdapter
import com.coffeedose.ui.Adapters.SizeSpinnerAdapter
import com.coffeedose.viewmodels.DrinksViewModel
import com.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import kotlinx.android.synthetic.main.fragment_select_dose_and_addins.*

/**
 * A simple [Fragment] subclass.
 */
class SelectDoseAndAddinsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val drinkId = SelectDoseAndAddinsFragmentArgs.fromBundle(arguments!!).drinkId

        val viewModel = ViewModelProviders.of(this, SelectDoseAndAddinsViewModel
            .Factory(requireNotNull(this.activity).application,drinkId)).get(SelectDoseAndAddinsViewModel::class.java)

        val binding: FragmentSelectDoseAndAddinsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_select_dose_and_addins,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val spinnerAdapter = SizeSpinnerAdapter(this.context!!)
        binding.spinnerSelectSize.adapter = spinnerAdapter

        viewModel.sizes.observe(this, Observer {
            if (viewModel.sizes.value != null)
                spinnerAdapter.setItems(viewModel.sizes.value!!)
        })

        binding.spinnerSelectSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long
            ) {
                viewModel.onSelectedSizeIndexChanged(position)
            }

        }

        val addinsListAdapter = AddinsListAdapter(this.context!!, AddinCheckListener { addin, isChecked  -> viewModel.updateTotalOnAddinCheck(addin, isChecked)})
        binding.addinsListView.adapter = addinsListAdapter

        viewModel.addins.observe(this, Observer {
            if (viewModel.addins.value != null)
                addinsListAdapter.setItems(viewModel.addins.value!!)
        })

        viewModel.getTotal().observe(this, Observer {
            binding.tvTotal.text = "${it?:0} P."
        })


        return binding.root
    }
}
