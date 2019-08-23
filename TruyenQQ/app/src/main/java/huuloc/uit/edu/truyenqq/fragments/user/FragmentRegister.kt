package huuloc.uit.edu.truyenqq.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

class FragmentRegister : Fragment() {
    val viewModel: ViewModelUser by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelUser::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@FragmentRegister
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegisterSend.setOnClickListener {
            viewModel.sendRegister(editUserRegister.text.toString(), editPasswordRegister.text.toString())
                .observe(this@FragmentRegister,
                    Observer {
                        if (it == null)
                        else if (it.error == null) {
                            Toast.makeText(activity!!, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            this@FragmentRegister.activity!!.onBackPressed()
                        } else if (it.error != null) {
                            val dialog = AlertDialog.Builder(context!!)
                            var message = ""
                            if (it.error!!.email != null)
                                message += "${it.error!!.email}\n"
                            if (it.error!!.password != null)
                                message += it.error!!.password + "\n"
                            if (it.error!!.username != null)
                                message += it.error!!.username
                            dialog.setMessage(message)
                            val dislay = dialog.create()
                            dislay.show()
                            viewModel.register.value = null
                        }
                    })
        }
    }
}