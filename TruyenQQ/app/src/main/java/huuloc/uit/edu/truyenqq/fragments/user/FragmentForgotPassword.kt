package huuloc.uit.edu.truyenqq.fragments.user

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.data.ForgotPassword
import huuloc.uit.edu.truyenqq.databinding.FragmentForgotpasswordBinding
import kotlinx.android.synthetic.main.fragment_forgotpassword.*

class FragmentForgotPassword : Fragment() {
    val viewModel: ViewModelUser by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelUser::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentForgotpasswordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@FragmentForgotPassword
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendForgot.setOnClickListener {
            viewModel.sendForgotPassword().observe(this@FragmentForgotPassword, Observer {
                if (it.success == "1")
                else if (!it.success.isNullOrEmpty()) {
                    val dialog = AlertDialog.Builder(context!!)
                    dialog.setMessage("Mật khẩu đã được gửi đến email của bạn.")
                        .setPositiveButton("ok") { dialogInterface: DialogInterface, i: Int ->

                        }
                    val dislay = dialog.create()
                    dislay.setTitle("Thông báo")
                    dislay.show()
                    viewModel.forgotPassword.value = ForgotPassword("")
                } else if (it.success == null) {
                    val dialog = AlertDialog.Builder(context!!)
                    dialog.setMessage("Email không tồn tại trong hệ thống")
                    val dislay = dialog.create()
                    dislay.setTitle("Thông báo")
                    dislay.show()
                    viewModel.forgotPassword.value = ForgotPassword("")
                }
            })
        }
    }
}