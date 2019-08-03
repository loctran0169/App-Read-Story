package huuloc.uit.edu.truyenqq.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.databinding.FragmentHomeBinding
import huuloc.uit.edu.truyenqq.adapers.AdapterVertical
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    private val adapterNew: AdapterVertical by lazy {
        AdapterVertical(activity!!, mutableListOf())
    }
    private val adapterStoryMale: AdapterVertical by lazy {
        AdapterVertical(activity!!, mutableListOf())
    }
    private val adapterStoryFemale: AdapterVertical by lazy {
        AdapterVertical(activity!!, mutableListOf())
    }
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelHome::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding =FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this@FragmentHome
        return binding.root

    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvNewUpdate.run {
            layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
            adapter=adapterNew
            addItemDecoration(SpaceItem(4))
        }
        rcvStoryMale.run {
            layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
            adapter=adapterStoryMale
            addItemDecoration(SpaceItem(4))

        }
        rcvStoryFemale.run {
            layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
            adapter=adapterStoryFemale
            addItemDecoration(SpaceItem(4))
        }
        //New
        viewModel.sLoadingNew.observe(this@FragmentHome, Observer {
            if(it)
                progressNewStory.visibility= View.INVISIBLE
            else
                progressNewStory.visibility= View.VISIBLE
        })
        viewModel.storyNew.observe(this@FragmentHome, Observer {
            adapterNew.updateData(it)
        })
        //Male
        viewModel.sLoadingStoryMale.observe(this@FragmentHome, Observer {
            if(it)
                progressStoryMale.visibility= View.INVISIBLE
            else
                progressStoryMale.visibility= View.VISIBLE
        })
        viewModel.storyStoryMale.observe(this@FragmentHome, Observer {
            adapterStoryMale.updateData(it)
        })
        //Female
        viewModel.sLoadingStoryFemale.observe(this@FragmentHome, Observer {
            if(it)
                progressStoryFeMale.visibility= View.INVISIBLE
            else
                progressStoryFeMale.visibility= View.VISIBLE
        })
        viewModel.storyStoryFemale.observe(this@FragmentHome, Observer {
            adapterStoryFemale.updateData(it)
        })
        flipperView.isAutoStart = true
        flipperView.flipInterval = 5000
        flipperView.setInAnimation(activity!!, R.anim.slide_in_right)
        flipperView.setOutAnimation(activity!!, R.anim.slide_out_right)
        /*flipperView.setOnTouchListener { p0, event ->
            if (event!!.action == MotionEvent.ACTION_DOWN) {

                firstX = event!!.x
                println("###x${firstX}")
            } else if (event!!.action == MotionEvent.ACTION_UP) {
                secondX = event!!.y
                if (firstX < secondX) {
                    println("### next")
                    flipperView.showNext()
                }
                if (firstX > secondX) {
                    println("### previous")
                    flipperView.showPrevious()
                }
                println("###w${secondX}")
            }
            true
        }
        */
    }
}