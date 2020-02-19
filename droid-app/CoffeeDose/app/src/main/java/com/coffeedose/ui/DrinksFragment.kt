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
import com.bumptech.glide.Glide

import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.coffeedose.domain.Coffee
import com.coffeedose.viewmodels.DrinksViewModel
import kotlinx.android.synthetic.main.fragment_drinks.*

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

        initObserveListeners()

        return binding.root
    }


    private fun initObserveListeners(){
        viewModel.selectedDrink?.observe(this, Observer {
            it?.let {
                drinkDescription.text = it.description

                Glide.with(drinkPhoto.context)
                    .load(it.photoUrl)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .centerCrop()
                    .into(drinkPhoto)
            }
        })

        viewModel.drinks.observe(this, Observer { viewModel.onSelectedItemIndexChanged(0) }) // TODO drop and do well
    }

    fun goToDoseAndAddins(){
        var action = DrinksFragmentDirections.actionDrinksFragmentToSelectDoseAndAddinsFragment(11)
        this.findNavController().navigate(action)
    }
}
