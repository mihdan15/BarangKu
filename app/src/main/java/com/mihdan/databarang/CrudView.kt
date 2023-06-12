package com.mihdan.databarang

interface CrudView {
    //get data
    fun onSuccessGet(data: List<DataItem>?)
    fun onFailedGet(msg: String)
    //untuk add
    fun onSuccessAdd(msg: String)
    fun onErrorAdd(msg : String)
    //untuk update
    fun onSuccessUpdate(msg:String)
    fun onErrorUpdate(msg:String)
    //untuk delete
    fun onSuccessDelete(msg: String)
    fun onErrorDelete(msg : String)

}

//else if (itemDataItem != null) {
//    btnAction.text = "Update"
//    val item = itemDataItem as DataItem?
//    etTgl. setText(item?. tglBarang. toString())
//    etNama. setText(item?. namaBarang. toString())
//    etJml. setText(item?. jmlBarang. toString())
//    etKet.setText(item?.ketBarang.toString())
//    btnAction. setOnClickListener() {
//        presenter. updateData(
//            item?.idBarang?:"",
//            etTgl. text. toString() ,
//            etNama. text. toString() ,
//            etJml. text. toString() ,
//            etKet. text. toString())
//        finish()
//    }
//}
