package com.example.ustapp.view

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ustapp.adapter.PostListAdapter
import com.example.ustapp.dao.Post
import com.example.ustapp.databinding.ActivityPostBinding
import com.example.ustapp.module.AppModule
import com.example.ustapp.repositry.DaggerAppComponent
import com.example.ustapp.repositry.ViewModelFactory
import com.example.ustapp.viewmodel.PostViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Provider


class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    var imagePath = ""
    @Inject
    lateinit var viewModelProvider: Provider<PostViewModel>

    private lateinit var viewModelFactory: ViewModelFactory
    private val postViewModel: PostViewModel by viewModels { viewModelFactory }
    lateinit var adapter: PostListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
        appComponent.inject(this)
        viewModelFactory = ViewModelFactory { viewModelProvider.get() }

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM YYYY", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        binding.edDate.setText(currentDate.toString())
        binding.edDate.setOnClickListener {
            showDatePicker()
        }
        binding.ivcamera.setOnClickListener {
            if(allPermissionsGranted()) {
                pickImage()
            }else  ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        binding.post.setOnClickListener {
            save()
        }
    }

    private val REQUEST_CODE_PERMISSIONS = 20
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    fun save() {
        if (TextUtils.isEmpty(binding.edtitle.text.toString())) {
            Toast.makeText(application, "Please Enter Title", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.edcaption.text.toString())) {
            Toast.makeText(application, "Please Enter Caption", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(imagePath)) {
            Toast.makeText(application, "Please Pick the image", Toast.LENGTH_SHORT).show()
        } else {
            val test=System.currentTimeMillis()
            val post = Post(
                test,
                binding.edtitle.text.toString(),
                binding.edDate.text.toString(),
                imagePath,
                binding.edcaption.text.toString(),false
            )
            postViewModel.insertUser((post))
            Toast.makeText(applicationContext,"Successfully inserted",Toast.LENGTH_SHORT).show()
            finish()
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