package com.coffeedose.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.coffeedose.databinding.FragmentSelectDoseAndAddinsBinding
import com.coffeedose.viewmodels.DrinksViewModel
import com.coffeedose.viewmodels.SelectDoseAndAddinsViewModel

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
        return binding.root
    }
}
