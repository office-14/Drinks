package com.coffeedose.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.coffeedose.R
import com.coffeedose.databinding.FragmentDrinksBinding
import com.coffeedose.extensions.setBooleanVisibility
import com.coffeedose.repository.PreferencesRepository
import com.coffeedose.ui.Adapters.CoffeeSpinnerAdapter
import com.coffeedose.viewmodels.DrinksViewModel
import kotlinx.android.synthetic.main.fragment_drinks.*


/**

 */
class DrinksFragment : Fragment() {

    private val viewModel: DrinksViewModel by lazy {
        ViewModelProvider(this,DrinksViewModel.Factory(requireNotNull(this.activity).application)).get(DrinksViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val binding: FragmentDrinksBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_drinks,container,false)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initProceedButton(binding.proceedButton)

        initDrinksSpinner(binding.drinksSpinner)

        initSwipeToRefresh(binding.swipeRefresh)

        initErrorHandling(binding)

        initToolbar()

        initObserveListeners()

        return binding.root
    }

    private fun initErrorHandling(binding: FragmentDrinksBinding) {
        viewModel.errorMessage.observe(this, Observer {

            if (it != null){
                binding.drinksRootLayout.setBooleanVisibility(false)
                binding.viewErrorDrinks.setBooleanVisibility(true)
                binding.tvErrorTextDrinks.text = it
                //viewModel.errorMessage.value ?: "Ошибка получения данных"
            }
            else {
                binding.drinksRootLayout.setBooleanVisibility(true)
                binding.viewErrorDrinks.setBooleanVisibility(false)
            }
        })
    }

    private fun initSwipeToRefresh(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.setOnRefreshListener { viewModel.refreshData(true) }
        viewModel.isRefreshing.observe(this, Observer {
            if (it != null){
                swipeRefresh.isRefreshing = it
            }
        } )
    }

    private fun initDrinksSpinner(drinksSpinner: Spinner) {
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
    }

    private fun initProceedButton(proceedButton: Button) {
        proceedButton.setOnClickListener {
            findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToSelectDoseAndAddinsFragment(viewModel.selectedDrink.value!!.id,viewModel.selectedDrink.value!!.name))
        }
    }

    private fun initToolbar(){
        val toolbar = (activity as AppCompatActivity)?.supportActionBar
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
            R.id.goToOrderDetails -> {
                findNavController().navigate(DrinksFragmentDirections.actionDrinksFragmentToOrderFragment())
                return true
            }
            else -> return false
        }
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

    private fun showEditBaseUrlDialog() {

        val builder = AlertDialog.Builder(this.context!!)
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
}
