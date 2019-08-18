package huuloc.uit.edu.truyenqq.fragments.user

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huuloc.uit.edu.truyenqq.data.DataLogin
import huuloc.uit.edu.truyenqq.data.ForgotPassword
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelUser : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val forgotPassword = MutableLiveData<ForgotPassword>().apply { value = ForgotPassword("1") }
    val register = MutableLiveData<DataLogin>().apply { value = null }
    val login = MutableLiveData<DataLogin>().apply { value = null }
    var email = MutableLiveData<String>().apply { value = "" }
    var isLogin = MutableLiveData<Boolean>().apply { value = false }
    fun setEmail(tx: Editable) {
        email.value = tx.toString()
    }

    fun sendForgotPassword(): LiveData<ForgotPassword> {
        compo.add(
            apiManager.sendForgotPassword(email.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    forgotPassword.value = it
                }, {

                })
        )
        return forgotPassword
    }

    fun sendRegister(_email: String, _password: String): LiveData<DataLogin> {
        compo.add(
            apiManager.sendRegister(_email, _password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    register.value = it
                }, {

                })
        )
        return register
    }

    fun sendLogin(_email: String, _password: String) {
        compo.add(
            apiManager.sendLogin(_email, _password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    login.value = it
                }, {

                })
        )
    }
}