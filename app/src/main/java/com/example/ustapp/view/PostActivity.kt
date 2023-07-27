package com.example.ustapp.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ustapp.dao.Post
import com.example.ustapp.databinding.ActivityPostBinding
import com.example.ustapp.viewmodel.PostViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    var imagePath = ""
    lateinit var postViewModel: PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("DD MMM YYYY", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        binding.edDate.setText(currentDate.toString())
        binding.edDate.setOnClickListener {
            showDatePicker()
        }
        binding.ivcamera.setOnClickListener {
            pickImage()
        }
        binding.post.setOnClickListener {
            save()
        }
    }

    fun save() {
        if (TextUtils.isEmpty(binding.edtitle.text.toString())) {
            Toast.makeText(application, "Please Enter Title", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.edcaption.text.toString())) {
            Toast.makeText(application, "Please Enter Caption", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(imagePath)) {
            Toast.makeText(application, "Please Pick the image", Toast.LENGTH_SHORT).show()
        } else {
            val post = Post(
                1,
                binding.edtitle.text.toString(),
                binding.edDate.text.toString(),
                imagePath,
                binding.edcaption.text.toString(),false
            )
            postViewModel.insertUser((post))
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var cal = Calendar.getInstance()
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd MMM YYYY"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edDate.setText(sdf.format(cal.time))
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    private val IMAGE_REQUEST = 1999
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {
                val selectedImageUri: Uri = data!!.data!!
                binding.ivcamera.setImageURI(selectedImageUri)
                val picturePath: String =
                    getPath(applicationContext, selectedImageUri)
                imagePath = picturePath
               Log.d("image",imagePath)
            } catch (ex: Exception) {

            }
        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri!!, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }
}