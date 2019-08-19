package huuloc.uit.edu.truyenqq.activities.changepassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.USER_ID
import kotlinx.android.synthetic.main.activity_changepassword.*

class ActivityChangePassWord : AppCompatActivity() {
    val viewmodel: ViewModelChangePassWord by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelChangePassWord::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)
        ViewModelProviders
            .of(this)
            .get(ViewModelChangePassWord::class.java)
        tvBackChangePassWord.setOnClickListener {
            onBackPressed()
        }
        val share = MysharedPreferences(this)
        viewmodel.changePassword.observe(this@ActivityChangePassWord, Observer {
            if (it == null)
            else {
                if (it.success != null) {
                    share.saveData(it.data!!)
                    Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setMessage("Mật khẩu củ không chính xác")
                    val dislay = dialog.create()
                    dislay.show()
                }
            }
        })
        btnChangePassWord.setOnClickListener {
            if (editNewPassWord.text.length < 6 || editNewPassWordConfirm.text.length < 6) {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Mật khẩu phải lớn hơn 6 ký tự")
                val dislay = dialog.create()
                dislay.show()
            } else if (editNewPassWord.text.toString() != editNewPassWordConfirm.text.toString()) {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Mật khẩu mới không trùng khớp")
                val dislay = dialog.create()
                dislay.show()
            } else
                viewmodel.sendChangePassword(
                    share.getShare.getString(USER_ID, null)!!,
                    editOldPassWord.text.toString(),
                    editNewPassWord.text.toString(),
                    editNewPassWordConfirm.text.toString(),
                    share.getShare.getString(USER_ID, null)
                )
        }
    }
}