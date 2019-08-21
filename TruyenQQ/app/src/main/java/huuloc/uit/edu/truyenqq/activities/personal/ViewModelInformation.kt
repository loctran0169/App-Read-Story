package huuloc.uit.edu.truyenqq.activities.personal

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.ChangeInformation
import huuloc.uit.edu.truyenqq.data.DataLogin
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelInformationFactory(val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelInformation(context) as T
    }
}

class ViewModelInformation(context: Context) : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val share = MysharedPreferences(context)
    var data = MutableLiveData<ChangeInformation>().apply { value = null }

    fun sendChangeInformation(
        user_id: String,
        first_name: String,
        last_name: String,
        sex: String,
        avatar: String?,
        phone: String?,
        day: String,
        month: String,
        year: String
    ) {
        compo.add(
            apiManager.sendChangeInformation(user_id, first_name, last_name, sex, avatar,phone, day, month, year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.value = it
                }, {
                    println("### ${it.message}")
                })
        )
    }
}