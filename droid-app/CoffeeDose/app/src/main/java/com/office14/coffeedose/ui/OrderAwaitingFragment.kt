package com.office14.coffeedose.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.coffeedose.R
import com.coffeedose.databinding.FragmentOrderAwaitingBinding
import com.office14.coffeedose.extensions.setBooleanVisibility
import com.office14.coffeedose.viewmodels.OrderAwaitingViewModel
import kotlinx.android.synthetic.main.fragment_order_awaiting.*

/**
 * A simple [Fragment] subclass.
 */
class OrderAwaitingFragment : Fragment() {

    private lateinit var viewModel : OrderAwaitingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val orderId = OrderAwaitingFragmentArgs.fromBundle(requireArguments()).orderId

        viewModel = ViewModelProvider(this,OrderAwaitingViewModel.Factory(requireNotNull(this.activity).application,orderId)).get(OrderAwaitingViewModel::class.java)
        val binding : FragmentOrderAwaitingBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_order_awaiting,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initToolbar()

        initToolbarTitle()

        initNavigateOutOnApprove(binding.bvApprove)

        return binding.root
    }

    private fun initNavigateOutOnApprove(bvApprove: Button) {
        bvApprove.setOnClickListener {
            viewModel.approve()
            findNavController().navigate(OrderAwaitingFragmentDirections.actionOrderAwaitingFragmentToDrinksFragment())
        }
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
        })
    }


    private fun initToolbar(){
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }
}
