package com.office14.coffeedose.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.coffeedose.R
import com.coffeedose.databinding.FragmentOrderAwaitingBinding
import com.office14.coffeedose.di.InjectingSavedStateViewModelFactory
import com.office14.coffeedose.extensions.setBooleanVisibility
import com.office14.coffeedose.viewmodels.MenuInfoViewModel
import com.office14.coffeedose.viewmodels.OrderAwaitingViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_order_awaiting.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class OrderAwaitingFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    @Inject
    lateinit var defaultViewModelFactory: InjectingSavedStateViewModelFactory

    //private lateinit var viewModel : OrderAwaitingViewModel
    private val viewModel: OrderAwaitingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        //viewModel = ViewModelProvider(this,OrderAwaitingViewModel.Factory(requireNotNull(this.activity).application)).get(OrderAwaitingViewModel::class.java)
        val binding : FragmentOrderAwaitingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_order_awaiting,container,false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initToolbar()

        initToolbarTitle()

        initNavigation()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //val viewModelFactory = defaultViewModelFactory.create(this)
        //viewModel = ViewModelProvider(this,viewModelFactory).get(OrderAwaitingViewModel::class.java)
    }


    private fun initToolbarTitle() {
        viewModel.order.observe(viewLifecycleOwner, Observer {
            if (it != null)
            {
                val toolbar = (activity as AppCompatActivity).supportActionBar
                toolbar?.title = "Заказ ${it.orderNumber}"
                tv_order_status.text = "Статус: ${it.statusName}"

                if (it.statusCode.toLowerCase() == "ready"){
                    bv_approve.setBooleanVisibility(true)
                }
            }
            else{
                val toolbar = (activity as AppCompatActivity).supportActionBar
                toolbar?.title = "Заказ не создан"
            }
        })
    }


    private fun initToolbar(){
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }

    private fun initNavigation(){
        viewModel.navigateToCoffeeList.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    findNavController().navigate(OrderAwaitingFragmentDirections.actionOrderAwaitingFragmentToDrinksFragment())
                    viewModel.doneNavigation()
                }
            }
        })
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.create(this)
}
