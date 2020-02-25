package com.coffeedose.ui


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.coffeedose.R
import com.coffeedose.databinding.FragmentSelectDoseAndAddinsBinding
import com.coffeedose.ui.Adapters.AddinCheckListener
import com.coffeedose.ui.Adapters.AddinsListAdapter
import com.coffeedose.ui.Adapters.SizeSpinnerAdapter
import com.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import com.shawnlin.numberpicker.NumberPicker


/**
 * A simple [Fragment] subclass.
 */
class SelectDoseAndAddinsFragment : Fragment() {


    private lateinit var viewModel : SelectDoseAndAddinsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val drinkId = SelectDoseAndAddinsFragmentArgs.fromBundle(arguments!!).drinkId
        val drinkName = SelectDoseAndAddinsFragmentArgs.fromBundle(arguments!!).drinkName

        viewModel = ViewModelProvider(this, SelectDoseAndAddinsViewModel
            .Factory(requireNotNull(this.activity).application,drinkId)).get(SelectDoseAndAddinsViewModel::class.java)

        val binding: FragmentSelectDoseAndAddinsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_select_dose_and_addins,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initSpinner(binding.spinnerSelectSize)
        initAddinsAdapter(binding.addinsListView)
        initCountPicker(binding.numberPicker)
        initProceedButton(binding.addButton)

        initToolbar(drinkName)

        return binding.root
    }

    private  fun initSpinner(view : Spinner){
        val spinnerAdapter = SizeSpinnerAdapter(this.context!!)
        view.adapter = spinnerAdapter

        viewModel.sizes.observe(this, Observer {
            if (viewModel.sizes.value != null)
                spinnerAdapter.setItems(viewModel.sizes.value!!)
        })

        view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long
            ) {
                viewModel.onSelectedSizeIndexChanged(position)
            }

        }
    }

    private fun initToolbar(title:String){
        val toolbar = (activity as AppCompatActivity)?.supportActionBar
        toolbar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = title
        }
    }

    private fun initAddinsAdapter(view : ListView){
        val addinsListAdapter = AddinsListAdapter(this.context!!, AddinCheckListener { addin, isChecked  -> viewModel.updateTotalOnAddinCheck(addin, isChecked)})
        view.adapter = addinsListAdapter

        viewModel.addins.observe(this, Observer {
            if (viewModel.addins.value != null)
                addinsListAdapter.setItems(viewModel.addins.value!!)
        })
    }

    private fun initCountPicker(numberPicker : NumberPicker){
        viewModel.count.observe(this, Observer { numberPicker.value = viewModel.count.value!! })

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal -> viewModel.updateCount(newVal) }
    }

    private fun initProceedButton(button:Button){
        button.setOnClickListener {
            viewModel.saveOrderDetails()
            showAddOrProceedDialog()
        }
    }

    private fun showAddOrProceedDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setMessage(R.string.AddOrProceedDialogMessage)
            .setNegativeButton(R.string.ContinueChoice,
                DialogInterface.OnClickListener { dialog, id ->
                    findNavController().navigate(SelectDoseAndAddinsFragmentDirections.actionSelectDoseAndAddinsFragmentToDrinksFragment())
                })
            .setPositiveButton(R.string.OrderDetails,
                DialogInterface.OnClickListener { dialog, id ->
                    findNavController().navigate(SelectDoseAndAddinsFragmentDirections.actionSelectDoseAndAddinsFragmentToOrderFragment())
                })
        builder.create().show()
    }

}
