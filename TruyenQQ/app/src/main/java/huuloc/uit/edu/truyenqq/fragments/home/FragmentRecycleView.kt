package huuloc.uit.edu.truyenqq.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterHorizontal
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.network.ApiManager
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_item_vertical.*

class FragmentRecycleView(private val url: String, private val country: Int? = null) : Fragment() {
    private var offset: Int = 0
    private val apiManager: ApiManager by lazy { ApiManager() }
    private val adapterHorizontal: AdapterHorizontal by lazy {
        AdapterHorizontal(activity!!, ListStory(mutableListOf()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_item_vertical, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcvHorizontal.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterHorizontal
            addItemDecoration(SpaceItem(4))
        }
        /*apiManager.getListStory(url, country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapterHorizontal.updateData(it)
            }, {

            })*/
        apiManager.geListTop(0, 20, "views_day")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapterHorizontal.updateData(it)
            }, {

            })
    }
}