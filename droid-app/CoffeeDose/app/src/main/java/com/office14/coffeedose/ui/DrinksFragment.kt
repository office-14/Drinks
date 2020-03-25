package com.office14.coffeedose.ui

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.office14.coffeedose.extensions.setBooleanVisibility
import com.office14.coffeedose.repository.PreferencesRepository
import com.office14.coffeedose.ui.Adapters.DrinksListAdapter
import com.office14.coffeedose.viewmodels.DrinksViewModel


/**

 */
class DrinksFragment : Fragment(), SelectDoseAndAddinsFragment.OnDrinkAddListener {

    private val viewModel: DrinksViewModel by lazy {
        ViewModelProvider(this,DrinksViewModel.Factory(requireNotNull(this.activity).application)).get(DrinksViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding: FragmentDrinksBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_drinks,container,false)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

       // initProceedButton(binding.proceedButton)

        //initDrinksSpinner(binding.drinksSpinner)

        initSwipeToRefresh(binding.swipeRefresh)

        initErrorHandling(binding)

        initToolbar()

        initDrinksRecyclerView(binding.drinksRv)

        handleSelectItem()

        handleOrdersNavigation()

        //initOrdersButtonVisibility(binding.ordersFab)

            //initObserveListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(requireContext(),"DrinksFragment is started", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        //val orderId = PreferencesRepository.getLastOrderId()
        //val redirToAwait = PreferencesRepository.getNavigateToOrderAwaitFrag()
        //Toast.makeText(requireContext(),"DrinksFragment is resumed ($orderId $redirToAwait)", Toast.LENGTH_LONG).show()


        //if (PreferencesRepository.getLastOrderId() != -1)
        //    findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToOrderAwaitingFragment())
    }

    private fun handleOrdersNavigation() {
        viewModel.navigatingOrders.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToOrderFragment())
                    viewModel.doneNavigatingOrders()
                }
            }
        })
    }

    private fun initErrorHandling(binding: FragmentDrinksBinding) {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

            if (it != null){
                binding.rootFl.setBooleanVisibility(false)
                binding.viewErrorDrinks.setBooleanVisibility(true)
                binding.tvErrorTextDrinks.text = it
                //viewModel.errorMessage.value ?: "Ошибка получения данных"
            }
            else {
                binding.rootFl.setBooleanVisibility(true)
                binding.viewErrorDrinks.setBooleanVisibility(false)
            }
        })
    }

    private fun initSwipeToRefresh(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.setOnRefreshListener { viewModel.refreshData(true) }
        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer {
            if (it != null){
                swipeRefresh.isRefreshing = it
            }
        } )
    }

    /*private fun initDrinksSpinner(drinksSpinner: Spinner) {
        val spinnerAdapter = CoffeeSpinnerAdapter(this.context!!)
        drinksSpinner.adapter = spinnerAdapter

        viewModel.drinks.observe(this, Observer {
            if (viewModel.drinks.value != null && viewModel.drinks.value!!.isNotEmpty()) {
                spinnerAdapter.setItems(viewModel.drinks.value!!)
                drinksSpinner.setSelection(0)
            }

        })

        drinksSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                viewModel.onSelectedItemIndexChanged(position)
            }

        }
    }*/

    /*private fun initProceedButton(proceedButton: Button) {
        proceedButton.setOnClickListener {
            findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToSelectDoseAndAddinsFragment(viewModel.selectedDrink.value!!.id,viewModel.selectedDrink.value!!.name))
        }
    }*/

    /*private fun initOrdersButtonVisibility(button: FloatingActionButton){
        viewModel.ordersButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let { button.setBooleanVisibility(it) }
        })
    }*/


    private fun initToolbar(){
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
            it.title = "Выберите напиток"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.showEnvDialog -> {
                showEditBaseUrlDialog()
                return true
            }
            R.id.login -> {
                prepareAuth()
                return true
            }
            /*R.id.goToOrderDetails -> {
                findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToOrderFragment())
                return true
            }*/
            else -> return false
        }
    }

    private fun prepareAuth(){

    }


    /*private fun initObserveListeners(){
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
    }*/

    private fun initDrinksRecyclerView(recyclerView: RecyclerView){

        val drinksAdapter = DrinksListAdapter(CoffeeItemClickListener { viewModel.selectDrink(it) })

        //val divDecor = DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL)
        //divDecor.setDrawable(R.layout.)
        //divDecor.setDrawable(R.layout.view)

        with(recyclerView){
            adapter = drinksAdapter
            layoutManager = LinearLayoutManager(this.context)
            //addItemDecoration(divDecor)
        }


        viewModel.drinks.observe(viewLifecycleOwner, Observer {
            it?.let {
                drinksAdapter.submitList(it)
            }
        })

    }

    private fun handleSelectItem(){
        viewModel.selectedId.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it != -1){
                    val selectDoseAndAddInsFragment = SelectDoseAndAddinsFragment(this,it,viewModel.getDrinkName())
                    selectDoseAndAddInsFragment.show(childFragmentManager,SelectDoseAndAddinsFragment.TAG)
                    viewModel.doneNavigatingDose()
                }
            }
        })
    }



    private fun showEditBaseUrlDialog() {

        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle(R.string.BaseURL)

        val view = layoutInflater.inflate(R.layout.view_edit_text_dialog, null)

        val categoryEditText = view.findViewById<EditText>(R.id.dialogEditText)
        categoryEditText.setText(PreferencesRepository.getBaseUrl())

        builder.setView(view)


        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            PreferencesRepository.saveBaseUrl(categoryEditText.text.toString())
        }

        builder.show()
    }

    override fun onDrinkAdd() {
        //TODO update button visibility
    }
}

class CoffeeItemClickListener(val clickListener: (Int) -> Unit) {
    fun onClick(id: Int) = clickListener(id)
}
