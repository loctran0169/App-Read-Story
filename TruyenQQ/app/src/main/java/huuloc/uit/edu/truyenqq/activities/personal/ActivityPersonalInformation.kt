package huuloc.uit.edu.truyenqq.activities.personal

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.AVATAR
import huuloc.uit.edu.truyenqq.data.DataLogin
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.USER_ID
import kotlinx.android.synthetic.main.activity_infor.*
import java.util.*

class ActivityPersonalInformation : AppCompatActivity() {
    val viewModel: ViewModelInformation by lazy {
        ViewModelProviders
            .of(this, ViewModelInformationFactory(this))
            .get(ViewModelInformation::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infor)
        tvBackInfor.setOnClickListener {
            onBackPressed()
        }
        val share = MysharedPreferences(this)
        bindData(share.loadData()!!)
        viewModel.data.observe(this@ActivityPersonalInformation, androidx.lifecycle.Observer {
            if (it != null) {
                if (it.success != null) {
                    share.saveData(it.data!!)
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    bindData(it.data!!)
                } else {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setMessage("Lưu thông tin thất bại \nVui lòng kiềm tra lại thông tin hoặc đường truyền.")
                    val dislay = dialog.create()
                    dislay.show()
                }
            }
        })
        editDate.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                selectDay()
            }
        }
        btnSaveInfor.setOnClickListener {
            when {
                editLastName.text.toString().isEmpty() -> Toast.makeText(
                    this,
                    "Họ không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
                editFirstName.text.toString().isEmpty() -> Toast.makeText(
                    this,
                    "Tên không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    viewModel.sendChangeInformation(
                        share.getShare.getString(USER_ID, null)!!,
                        editFirstName.text.toString(),
                        editLastName.text.toString(),
                        if (checkboxMale.isChecked) "1" else "0",
                        share.getShare.getString(AVATAR, null),
                        editPhone.text.toString(),
                        editDate.text.toString().split("-")[0],
                        editDate.text.toString().split("-")[1],
                        editDate.text.toString().split("-")[2]
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    fun selectDay() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
        }, year, month, day)

        dpd.show()
    }

    @SuppressLint("SetTextI18n")
    fun bindData(data: DataLogin) {
        editFirstName.setText(data.first_name)
        editLastName.setText(data.last_name)
        editPhone.setText(data.phone)
        editEmail.setText(data.email)
        if (data.birthday_string!!.split("-")[0].toInt() > 32)
            editDate.setText(
                data.birthday_string!!.split("-")[2] + "-" + data.birthday_string!!.split("-")[1] + "-" + data.birthday_string!!.split(
                    "-"
                )[0]
            )
        else
            editDate.setText(
                data.birthday_string!!.split("-")[0] + "-" + data.birthday_string!!.split("-")[1] + "-" + data.birthday_string!!.split(
                    "-"
                )[2]
            )
        if (data.sex == "1")
            checkboxMale.isChecked = true
        else
            checkboxFemale.isChecked = true
        Glide.with(this)
            .load("http://avatar.mangaqq.com/160x160/" + data.avatar)
            .error(R.drawable.ic_noavatar)
            .into(imgAvatar)
    }

}