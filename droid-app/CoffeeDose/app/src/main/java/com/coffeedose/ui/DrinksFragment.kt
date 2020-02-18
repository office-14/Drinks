package com.coffeedose.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController

import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.coffeedose.domain.Coffee
import com.coffeedose.viewmodels.DrinksViewModel

/**

 */
class DrinksFragment : Fragment() {

    private val viewModel: DrinksViewModel by lazy {

        ViewModelProviders.of(
            this,
            DrinksViewModel.Factory(requireNotNull(this.activity).application)
        ).get(DrinksViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding: FragmentDrinksBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_drinks,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        var i = 0
        viewModel.drinks.observe(this, Observer {
            i++
        })

        return binding.root
    }



    fun goToDoseAndAddins(){
        var action = DrinksFragmentDirections.actionDrinksFragmentToSelectDoseAndAddinsFragment(11)
        this.findNavController().navigate(action)
    }
}
