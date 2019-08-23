package huuloc.uit.edu.truyenqq.activities.reading

import android.content.Context
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.data.ListChap
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.StoryImage
import huuloc.uit.edu.truyenqq.data.USER_ID
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelReadingFactory(val bookId: String, val chap: String, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelReading(bookId, chap, context) as T
    }
}

class ViewModelReading(val bookId: String, var chap: String, val context: Context) : ViewModel() {
    val share = MysharedPreferences(context)
    val user_id = share.getShare.getString(USER_ID, null)
    val compo: CompositeDisposable by lazy { CompositeDisposable() }
    val apiManager: ApiManager by lazy { ApiManager() }
    var story = MutableLiveData<StoryImage>().apply { value = StoryImage("0.0", "0.0", "0.0", "0.0", mutableListOf()) }
    var listChap = MutableLiveData<ListChap>().apply { value = ListChap(mutableListOf()) }

    init {
        getListChap()
        loadImage()
        if (user_id != null)
            setHistory()
    }

    fun loadImage() {
        compo.add(
            apiManager.getListImage(bookId, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {

                })
        )
    }

    fun loadImageNextOrPrev(_chap: String) {
        this.chap = _chap
        compo.clear()
        compo.add(
            apiManager.getListImage(bookId, _chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    story.value = it
                }, {

                })
        )
    }

    fun setHistory() {
        compo.add(
            apiManager.setHistory(bookId, user_id!!, chap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        )
    }

    fun getListChap() {
        compo.add(
            apiManager.getListChaps(0, null, bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listChap.value = it
                }, {

                })
        )
    }

    fun postComment(edit : EditText, name : String, email : String, content: String, level : String, type_book: String, status: String, user_parent: String){
        compo.add(
            apiManager.postComment(bookId, user_id ?:"0", name, email, content, level, type_book, status, user_parent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.status=="1"){
                        edit.text.clear()
                        Toast.makeText(context,"Đã gữi comment",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        edit.text.clear()
                        Toast.makeText(context,"Gữi comment thất bại",Toast.LENGTH_SHORT).show()
                    }
                }, {

                })
        )
    }
}