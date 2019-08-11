package huuloc.uit.edu.truyenqq.fragments.story

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ViewModelStory
import huuloc.uit.edu.truyenqq.adapers.AdapterListChap
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentListChaps : Fragment() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelStory::class.java)
    }
    private val apiManager: ApiManager by lazy { ApiManager() }
    val adapterListChap: AdapterListChap by lazy {
        AdapterListChap(activity!!, mutableListOf())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvHorizontal.run {
            adapter = adapterListChap
            layoutManager = LinearLayoutManager(activity!!)
        }
        viewModel.listChap.observe(this@FragmentListChaps, Observer {
            adapterListChap.updateData(it.list)
        })
//        apiManager.getListChaps(0, 20, "128")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                println("### ${it.list.size}")
//                adapterListChap.updateData(it.list)
//            }, {
//
//            })
    }
}