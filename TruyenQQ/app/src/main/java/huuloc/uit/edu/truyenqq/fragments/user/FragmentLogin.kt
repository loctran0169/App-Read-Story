package huuloc.uit.edu.truyenqq.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.USER_ID
import huuloc.uit.edu.truyenqq.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin : Fragment() {
    val viewModel: ViewModelUser by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelUser::class.java)
    }
    private val sharedPreferences: MysharedPreferences by lazy {
        MysharedPreferences(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@FragmentLogin
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.login.observe(this@FragmentLogin, Observer {
            if (it != null) {
                if (it.error == null && sharedPreferences.gẹtShare.getString(USER_ID, null) == null) {
                    sharedPreferences.saveData(it)
                }
                viewModel.isLogin.value = true
            }
        })
        btnLogin.setOnClickListener {
            viewModel.sendLogin(editUser.text.toString(), editPassword.text.toString())
        }
    }
}