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


    private val viewModel: SelectDoseAndAddinsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SelectDoseAndAddinsViewModel.Factory(activity.application,1)).get(
            SelectDoseAndAddinsViewModel::class.java)
    }

    //private lateinit var drinkId?


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentSelectDoseAndAddinsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_select_dose_and_addins,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
