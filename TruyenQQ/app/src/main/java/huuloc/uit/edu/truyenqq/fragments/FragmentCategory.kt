package huuloc.uit.edu.truyenqq.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import huuloc.uit.edu.truyenqq.activities.main.ViewModelHome
import huuloc.uit.edu.truyenqq.adapers.AdapterCategory
import huuloc.uit.edu.truyenqq.data.Category
import huuloc.uit.edu.truyenqq.databinding.FragmentCategoryBinding
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_category.*

class FragmentCategory : Fragment() {

    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelHome::class.java)
    }
    private val adapterCategory: AdapterCategory by lazy {
        AdapterCategory(activity!!, mutableListOf())
    }
    var list: List<Category> = emptyList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.svCategory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapterCategory.updateData(list as MutableList<Category>)
                adapterCategory!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                adapterCategory.updateData(list as MutableList<Category>)
                adapterCategory!!.filter.filter(query)
                return false
            }
        })
        binding.lifecycleOwner = this@FragmentCategory
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvCategory.run {
            layoutManager = GridLayoutManager(activity!!, 2)
            adapter = adapterCategory
            addItemDecoration(SpaceItem(4))
        }
        viewModel.sLoadingCategory.observe(this@FragmentCategory, Observer {
            list = it.list
            adapterCategory.updateData(it.list as MutableList<Category>)
            if (it.list.isNotEmpty())
                progressBarCategory.visibility = View.INVISIBLE
        })
    }
}
