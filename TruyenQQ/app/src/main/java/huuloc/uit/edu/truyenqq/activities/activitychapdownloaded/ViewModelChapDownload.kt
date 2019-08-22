package huuloc.uit.edu.truyenqq.activities.activitychapdownloaded

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import huuloc.uit.edu.truyenqq.database.ImageChapRepository

class ViewModelReadDownloadFactory(val application: Application, val context: Context, val bookId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelChapDownload(application, context, bookId) as T
    }
}

class ViewModelChapDownload(val application: Application, val context: Context, val bookId: String) : ViewModel() {
    val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }
    val listChap = MutableLiveData<List<String>>().apply { value = null }


}