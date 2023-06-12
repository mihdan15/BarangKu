package com.mihdan.databarang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*


class DataAdapter (private var data: List<DataItem>?, private val click: onClickItem) :
RecyclerView.Adapter<DataAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount()=data?.size ?:0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.onBind(data?.get(position))
        holder.itemView.setOnClickListener() {
            click.clicked(data?.get(position))
        }
        holder.itemView.btnHapus.setOnClickListener(){
            click.delete(data?.get(position))
        }
    }

    fun setData(newDataList: List<DataItem>) {
        data = newDataList

        notifyDataSetChanged()
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(get: DataItem?) {
            itemView.tvId.text = get?.idBarang
            itemView.tvTgl.text = get?.tglBarang
            itemView.tvNama.text = get?.namaBarang
            itemView.tvJml.text = get?.jmlBarang
            itemView.tvKet.text = get?.ketBarang
            itemView.tvKategori.text = get?.kategori
        }
    }

    interface onClickItem{
        fun clicked (item: DataItem?)
        fun delete (item: DataItem?)
    }
}