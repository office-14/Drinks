package com.office14.coffeedose.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coffeedose.R
import com.coffeedose.databinding.FragmentSelectDoseAndAddinsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.office14.coffeedose.di.InjectingSavedStateViewModelFactory
import com.office14.coffeedose.extensions.setBooleanVisibility
import com.office14.coffeedose.ui.Adapters.AddinCheckListener
import com.office14.coffeedose.ui.Adapters.AddinsListAdapter
import com.office14.coffeedose.ui.Adapters.SizeSpinnerAdapter
import com.office14.coffeedose.viewmodels.OrderDetailsViewModel
import com.office14.coffeedose.viewmodels.SelectDoseAndAddinsViewModel
import com.shawnlin.numberpicker.NumberPicker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SelectDoseAndAddinsFragment (private val onDrinkAddListener:OnDrinkAddListener, private val drinkId:Int, private val drinkName:String) :  BottomSheetDialogFragment(),
    HasAndroidInjector, HasDefaultViewModelProviderFactory {

    @Inject
    lateinit var defaultViewModelFactory: InjectingSavedStateViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val viewModel: SelectDoseAndAddinsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentSelectDoseAndAddinsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_select_dose_and_addins,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initSpinner(binding.spinnerSelectSize)
        initAddinsAdapter(binding.addinsListView)
        initCountPicker(binding.numberPicker)
        initErrorHandling(binding)
        initSwipeToRefresh(binding.swipeRefresh)
        handleNavigating()
        setTitle(binding.tvDrinkName)

        setPeekHeight(binding.root)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    private fun setPeekHeight(view:View){
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog?
            dialog?.behavior?.peekHeight = resources.getDimensionPixelSize(R.dimen.add_ins_dialog_height)
        }
    }

    private fun setTitle(textView:TextView){
        textView.text = drinkName
    }

    private fun initSwipeToRefresh(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.setOnRefreshListener { viewModel.refreshData(true) }
        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            if (it != null){
                swipeRefresh.isRefreshing = it
            }
        } )
    }

    private fun initErrorHandling(binding: FragmentSelectDoseAndAddinsBinding) {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

            if (it != null){
                binding.viewAddinsRoot.setBooleanVisibility(false)
                binding.viewAddinsError.setBooleanVisibility(true)
                binding.tvErrorText.text = it
            }
            else {
                binding.viewAddinsRoot.setBooleanVisibility(true)
                binding.viewAddinsError.setBooleanVisibility(false)
            }
        })
    }

    private  fun initSpinner(view : Spinner){
        val spinnerAdapter = SizeSpinnerAdapter(requireContext())
        view.adapter = spinnerAdapter

        viewModel.sizes.observe(viewLifecycleOwner, Observer {
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

    private fun initAddinsAdapter(view : ListView){
        val addinsListAdapter = AddinsListAdapter(requireContext(), AddinCheckListener { addin, isChecked  -> viewModel.updateTotalOnAddinCheck(addin, isChecked)})
        view.adapter = addinsListAdapter

        viewModel.addins.observe(viewLifecycleOwner, Observer {
            if (viewModel.addins.value != null)
                addinsListAdapter.setItems(viewModel.addins.value!!)
        })
    }

    private fun initCountPicker(numberPicker : NumberPicker){
        viewModel.count.observe(viewLifecycleOwner, Observer { numberPicker.value = viewModel.count.value!! })

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal -> viewModel.updateCount(newVal) }
    }

    private fun handleNavigating(){
        viewModel.navigateDrinks.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    onDrinkAddListener.onDrinkAdd()
                    viewModel.doneNavigating()
                    dismiss()
                }
            }
        })
    }

    interface OnDrinkAddListener {
        fun onDrinkAdd()
    }
    companion object{
        const val TAG = "SelectDoseAndAddinsFragment"
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.create(this, bundleOf("drinkId" to drinkId))

    /*private fun showAddOrProceedDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.AddOrProceedDialogMessage)
            .setNegativeButton(R.string.ContinueChoice,
                DialogInterface.OnClickListener { _, _ ->
                    findNavController().navigate(SelectDoseAndAddinsFragmentDirections.actionSelectDoseAndAddinsFragmentToDrinksFragment())
                })
            .setPositiveButton(R.string.OrderDetails,
                DialogInterface.OnClickListener { _, _ ->
                    findNavController().navigate(SelectDoseAndAddinsFragmentDirections.actionSelectDoseAndAddinsFragmentToOrderFragment())
                })
        builder.create().show()
    }*/

}
