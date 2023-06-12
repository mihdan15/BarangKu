package com.mihdan.databarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_add.*
import kotlinx.android.synthetic.main.activity_update_add.spinnerCategory
import kotlinx.android.synthetic.main.filter_dialog.*

class MainActivity : AppCompatActivity(), CrudView {
    private lateinit var presenter: Presenter
    private val dataList: MutableList<DataItem> = mutableListOf()
    private lateinit var adapter: DataAdapter

    private lateinit var rangeSliderQuantity: RangeSlider
    private lateinit var tvMinQuantity: TextView
    private lateinit var tvMaxQuantity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        presenter = Presenter(this)
        presenter.getData()
        setupSearch()

        adapter = DataAdapter(dataList, object : DataAdapter.onClickItem {
            override fun clicked(item: DataItem?) {
                val bundle = Bundle()
                bundle.putSerializable("dataItem", item)
                val intent = Intent(applicationContext, UpdateAddActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun delete(item: DataItem?) {
                DeleteConfirm(item)
            }

        })

        rvCategory.adapter = adapter

        btnTambah.setOnClickListener{
            startActivity(Intent(applicationContext, UpdateAddActivity::class.java))
            finish()
        }

        val logoImageView: ImageView = findViewById(R.id.logo)
        logoImageView.setOnClickListener {
            showOverflowMenu(logoImageView)
        }

        appName.setOnClickListener {
            showFilterOptions()
        }

    }



    override fun onSuccessGet(data: List<DataItem>?) {
        dataList.clear()
        data?.let { dataList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onFailedGet(msg: String) {
        // Tampilkan pesan gagal memperoleh data
    }

    override fun onSuccessDelete(msg: String) {
        presenter.getData()
    }

    override fun onErrorDelete(msg: String) {
        // Tampilkan pesan error saat menghapus data
    }

    override fun onSuccessAdd(msg: String) {
        // Tampilkan pesan berhasil menambahkan data
    }

    override fun onErrorAdd(msg: String) {
        // Tampilkan pesan error saat menambahkan data
    }

    override fun onSuccessUpdate(msg: String) {
        // Tampilkan pesan berhasil memperbarui data
    }

    override fun onErrorUpdate(msg: String) {
        // Tampilkan pesan error saat memperbarui data
    }

    private enum class SortingType {
        NAMA_BARANG,
        ID_BARANG,
        JML_BARANG,
        KATEGORI
    }

    private fun sortData(sortingType: SortingType) {
        when (sortingType) {
            SortingType.NAMA_BARANG -> {
                dataList.sortBy { it.namaBarang }
            }
            SortingType.ID_BARANG -> {
                dataList.sortBy { it.idBarang }
            }
            SortingType.JML_BARANG -> {
                dataList.sortBy { it.jmlBarang }
            }
            SortingType.KATEGORI -> {
                dataList.sortBy { it.kategori }
            }
        }
        adapter.notifyDataSetChanged()
    }



    private fun search(query: String) {
        val filteredData = dataList.filter { data ->
            data.namaBarang?.contains(query, ignoreCase = true) == true ||
                    data.idBarang?.contains(query, ignoreCase = true) == true ||
                        data.jmlBarang?.contains(query, ignoreCase = true) == true ||
                            data.kategori?.contains(query, ignoreCase = true) == true
        }

        adapter.setData(filteredData)
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }
        })
    }

    private fun DeleteConfirm(item: DataItem?) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus data ini?")
            .setPositiveButton("Ya") { dialog, _ ->
                presenter.hapusData(item?.idBarang)
                presenter.getData()
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun showOverflowMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.overflow_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_sort_nama_barang -> {
                    // Tambahkan logika pengurutan berdasarkan nama barang
                    sortData(SortingType.NAMA_BARANG)
                    Toast.makeText(this, "Sort by Nama Barang", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_sort_id_barang -> {
                    // Tambahkan logika pengurutan berdasarkan ID barang
                    sortData(SortingType.ID_BARANG)
                    Toast.makeText(this, "Sort by ID Barang", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_sort_jumlah_barang -> {
                    // Tambahkan logika pengurutan berdasarkan jumlah barang
                    sortData(SortingType.JML_BARANG)
                    Toast.makeText(this, "Sort by Jumlah Barang", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_sort_kategori -> {
                    // Tambahkan logika pengurutan berdasarkan kategori
                    sortData(SortingType.KATEGORI)
                    Toast.makeText(this, "Sort by Kategori", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_nama_barang -> {
                // Tambahkan logika pengurutan berdasarkan nama barang
                Toast.makeText(this, "Sort by Nama Barang", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_sort_id_barang -> {
                // Tambahkan logika pengurutan berdasarkan ID barang
                Toast.makeText(this, "Sort by ID Barang", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_sort_jumlah_barang -> {
                // Tambahkan logika pengurutan berdasarkan jumlah barang
                Toast.makeText(this, "Sort by Jumlah Barang", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_sort_kategori -> {
                // Tambahkan logika pengurutan berdasarkan kategori
                Toast.makeText(this, "Sort by Kategori", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterOptions() {
        val dialogView = layoutInflater.inflate(R.layout.filter_dialog, null)
        val builder = AlertDialog.Builder(this)
            .setTitle("Filter Options")
            .setView(dialogView)
            .setPositiveButton("Apply") { dialog, _ ->
                applyFilter()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun applyFilter() {
        val selectedCategory = spinnerCategory.selectedItem.toString()

        val filteredData = dataList.filter { data ->
            (selectedCategory == "All" || data.kategori == selectedCategory)
        }
        adapter.setData(filteredData)
    }




}