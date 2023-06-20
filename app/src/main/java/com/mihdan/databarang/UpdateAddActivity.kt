package com.mihdan.databarang

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.mihdan.databarang.R
import kotlinx.android.synthetic.main.activity_update_add.*
import kotlinx.android.synthetic.main.item_data.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("SENSELESESS_COMPARISON")
class UpdateAddActivity : AppCompatActivity(), CrudView {

    private lateinit var etTgl: EditText
    private val calendar = Calendar.getInstance()
    private lateinit var presenter: Presenter2
    private lateinit var spinnerCategory: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_add)

        etTgl = findViewById(R.id.etTgl)
        etTgl.setOnClickListener {
            showDatePickerDialog()
        }

        spinnerCategory = findViewById(R.id.spinnerCategory)
        // Set up spinner adapter
        val categories = resources.getStringArray(R.array.categories)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = spinnerAdapter

        presenter = Presenter2( this)
        val itemDataItem = intent.getSerializableExtra( "dataItem")

        back.setOnClickListener{
            val intent = Intent(this@UpdateAddActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


            if (itemDataItem == null) {
                btnAction.text = "Tambah"
                etId.visibility = View.GONE
                idLabel.visibility = View.GONE
                btnAction. setOnClickListener() {
                presenter.addData(
                    etTgl.text.toString(),
                    etNama.text.toString(),
                    etJml.text.toString(),
                    etKet.text.toString(),
                    spinnerCategory.selectedItem.toString()
                )
            }
            }
            else if (itemDataItem != null) {
                btnAction.visibility = View.INVISIBLE
                val item = itemDataItem as DataItem?
                etId.setText(item?.idBarang.toString())
                etTgl. setText(item?. tglBarang. toString())
                etNama. setText(item?. namaBarang. toString())
                etJml. setText(item?. jmlBarang. toString())
                etKet.setText(item?.ketBarang.toString())

                // Mengambil daftar kategori dari resources
                val categories = resources.getStringArray(R.array.categories)

                // Mencari posisi item kategori yang cocok
                val position = categories.indexOf(item?.kategori)

                // Mengatur pilihan kategori saat ini pada spinner
                spinnerCategory.setSelection(position)
                // Mengatur inputan menjadi read-only
                etTgl.isEnabled = false
                etNama.isEnabled = false
                etJml.isEnabled = false
                etKet.isEnabled = false
                etId.isEnabled = false
                spinnerCategory.isEnabled = false

                formName.setText(item?. namaBarang. toString())

                barIcon.setImageResource(R.drawable.edit)
                barEdit.visibility = View.VISIBLE


                barIcon.setOnClickListener {
                    btnAction.visibility = View.VISIBLE
                    btnAction.text = "Update"
                    formName.setText("Update " + item?. namaBarang. toString())
                    idLabel.visibility = View.GONE
                    etId.visibility = View.GONE
                    etTgl.isEnabled = true
                    etNama.isEnabled = true
                    etJml.isEnabled = true
                    etKet.isEnabled = true
                    spinnerCategory.isEnabled = true
                    btnAction. setOnClickListener() {
                        presenter. updateData(
                            item?.idBarang?:"",
                            etTgl. text. toString() ,
                            etNama. text. toString() ,
                            etJml. text. toString() ,
                            etKet. text. toString(),
                            spinnerCategory.selectedItem.toString()
                        )

                        finish()
                }


                }
            }
    }

    override fun onSuccessAdd(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    override fun onErrorAdd(msg: String) {}

    override fun onSuccessUpdate(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorUpdate(msg: String) {}

    override fun onSuccessGet(data: List<DataItem>?) {
    }

    override fun onFailedGet(msg: String) {
    }

    override fun onSuccessDelete(msg: String) {
    }

    override fun onErrorDelete(msg: String) {
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)

                etTgl.setText(selectedDateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }



}