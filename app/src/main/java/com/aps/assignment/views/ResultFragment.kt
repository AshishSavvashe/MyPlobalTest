package com.aps.assignment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aps.assignment.R
import com.aps.assignment.adapter.ResultAdapter
import com.aps.assignment.databinding.FragmentResultBinding
import com.aps.assignment.model.ResponseModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rjp.assignments.viewmodel.ResultDataModel
import kotlinx.android.synthetic.main.dialog_sort.*

/*
* Author:Ashish Savavshe
* */
class ResultFragment : BaseFragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var adapter: ResultAdapter
    private var viewModel:ResultDataModel?=null
    var filterView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result,container,false)
        init()
        listeners()
        return binding.root
    }

    /*
    * Basic listeners
    * */
    private fun listeners() {
        binding.swipe.setOnRefreshListener {
            callData()
        }
    }

    /*
    * init xml components and classes
    * */

    private fun init() {

       // viewModel= ViewModelProvider(this).get(ResultDataModel::class.java)
        viewModel = ViewModelProviders.of(this).get(ResultDataModel::class.java)

        adapter = ResultAdapter(){selectedStore->

            var fragment = GraphFragment()
            var bundle = Bundle()
            bundle.putSerializable("selectedStore", selectedStore)
            fragment.arguments = bundle
            (context as MainActivity).launchFragment(
                fragment,
                "Title"
            )
        }
        binding.rvResult.setHasFixedSize(true)
        binding.rvResult.setItemViewCacheSize(20)
        binding.rvResult.adapter = adapter

        callData()

        binding.btnSort.setOnClickListener {
            showsortpopup()
        }
    }

    /*
    * Call api using observer
    * */

    private fun callData() {
        if(isNetworkConnected(context!!)) {
            binding.swipe.isRefreshing = true
            viewModel?.let {
                it.getFactsList().observe(viewLifecycleOwner, Observer<ResponseModel> { apiResponse ->
                    if (apiResponse == null) {
                        showSnack(view!!,getString(R.string.api_error))
                        return@Observer
                    }else{
                        setDataToList(apiResponse)
                    }
                })
            }
        }
        else {
            binding.swipe.isRefreshing = false
            showSnack(view!!,getString(R.string.no_internet))
        }
    }

    /*
    * Set result data to list view
    * */

     fun setDataToList(data: ResponseModel) {
        if(data.apps.isNotEmpty()) {
            binding.swipe.isRefreshing = false
            adapter.setData(data.apps)
          //  setTitle(data.)
        }
    }

    fun showsortpopup() {
        val btnsheet = layoutInflater.inflate(R.layout.dialog_sort, null)
        val dialog = BottomSheetDialog(this.requireContext())
        dialog.setContentView(btnsheet)

        dialog.tvTotalSales.setOnClickListener {
            adapter?.sortRecipeList("Total Sales")
            dialog.dismiss()
        }

        dialog.tvAddToCart.setOnClickListener {
            adapter?.sortRecipeList("Add To Cart")
            dialog.dismiss()
        }

        dialog.tvDownload.setOnClickListener {
            adapter?.sortRecipeList("Download")
            dialog.dismiss()
        }

        dialog.tvUserSesions.setOnClickListener {
            adapter?.sortRecipeList("User Sessions")
            dialog.dismiss()
        }

        dialog.show()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ResultFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
