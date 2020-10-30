package com.aps.assignment.adapter

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aps.assignment.databinding.ResultItemsBinding
import com.aps.assignment.model.ResponseModel
import com.aps.assignment.views.BaseFragment

/*
* Author:Ashish Savvashe
* */

class ResultAdapter(val callback: (ResponseModel.App.Data) -> Unit) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    private var mainList:ArrayList<ResponseModel.App>?= arrayListOf()
    private var list: ArrayList<ResponseModel.App> ?= arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        return ViewHolder(ResultItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun setData(list: ArrayList<ResponseModel.App>) {
        this.list = list
        this.mainList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bindData(it) }

        var currentitem = list!![position]

        holder.binding.tvDesc.text = currentitem.name
        holder.binding.tvSales.text =   currentitem.data.total_sale.total.toString() +" "+ currentitem.currency

        holder.binding.tvViewDetails.setOnClickListener {

          //  callback.invoke(currentitem.data)
        }
        /*Glide.with(mContext)
            .load(R.drawable.no_image)
            .into(holder.binding.ivIcon)*/

    }

    fun sortRecipeList(sortBy: String) {
        when(sortBy){
            "Total Sales" ->{
                list!!.sortBy { it.data.total_sale.total }
                notifyDataSetChanged()
            }

            "Add To Cart" ->{
                list!!.sortBy { it.data.add_to_cart.total }
                notifyDataSetChanged()
            }

            "Download" ->{
                list!!.sortBy { it.data.downloads.total }
                notifyDataSetChanged()
            }

            "User Sessions" ->{
                list!!.sortBy { it.name?.toString() }
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(val binding: ResultItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(item: ResponseModel.App) {
            binding.row = item
        }
    }
}