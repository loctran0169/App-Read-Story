package huuloc.uit.edu.truyenqq.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.fragments.user.FragmentForgotPassword
import huuloc.uit.edu.truyenqq.fragments.user.FragmentLogin
import huuloc.uit.edu.truyenqq.fragments.user.FragmentRegister
import huuloc.uit.edu.truyenqq.fragments.user.ViewModelUser

class ActivityUser : AppCompatActivity() {
    val viewModel: ViewModelUser by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelUser::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProviders
            .of(this)
            .get(ViewModelUser::class.java)
        setContentView(R.layout.activity_user)
        supportFragmentManager.beginTransaction()
            .add(R.id.frmUser, FragmentLogin(), "aa")
            .commit()
        viewModel.isLogin.observe(this@ActivityUser, Observer {
            if (it == true){
                onBackPressed()
            }
        })
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frmUser, fragment)
            .addToBackStack("bb")
            .commit()
    }

    fun forgotPassword(view: View) {
        showFragment(FragmentForgotPassword())
    }

    fun registerUser(view: View) {
        showFragment(FragmentRegister())
    }
}