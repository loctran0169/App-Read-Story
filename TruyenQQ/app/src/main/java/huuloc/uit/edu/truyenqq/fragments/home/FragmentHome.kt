package huuloc.uit.edu.truyenqq.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterSchedule
import huuloc.uit.edu.truyenqq.adapers.AdapterVerticalHtml
import huuloc.uit.edu.truyenqq.databinding.FragmentHomeBinding
import huuloc.uit.edu.truyenqq.adapers.AdapterVerticalRestFull
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import org.w3c.dom.Text
import java.util.*

class FragmentHome : Fragment() {
    val cal=Calendar.getInstance()
    private val adapterNewRestFull: AdapterVerticalRestFull by lazy {
        AdapterVerticalRestFull(activity!!, mutableListOf())
    }
    private val adapterStoryMaleHtml: AdapterVerticalHtml by lazy {
        AdapterVerticalHtml(activity!!, mutableListOf())
    }
    private val adapterStoryFemaleHtml: AdapterVerticalHtml by lazy {
        AdapterVerticalHtml(activity!!, mutableListOf())
    }
    private val adapterSchedule: AdapterSchedule by lazy {
        AdapterSchedule(activity!!, mutableListOf())
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
        var binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this@FragmentHome
        return binding.root

    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcvNewUpdate.run {
            layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            adapter = adapterNewRestFull
            addItemDecoration(SpaceItem(4))
        }
        rcvStoryMale.run {
            layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            adapter = adapterStoryMaleHtml
            addItemDecoration(SpaceItem(4))

        }
        rcvStoryFemale.run {
            layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            adapter = adapterStoryFemaleHtml
            addItemDecoration(SpaceItem(4))
        }
        //New
        viewModel.sLoadingNew.observe(this@FragmentHome, Observer {
            if (it)
                progressNewStory.visibility = View.INVISIBLE
            else
                progressNewStory.visibility = View.VISIBLE
        })
        viewModel.storyNew.observe(this@FragmentHome, Observer {
            adapterNewRestFull.updateData(it)
        })
        //Male
        viewModel.sLoadingStoryMale.observe(this@FragmentHome, Observer {
            if (it)
                progressStoryMale.visibility = View.INVISIBLE
            else
                progressStoryMale.visibility = View.VISIBLE
        })
        viewModel.storyStoryMale.observe(this@FragmentHome, Observer {
            adapterStoryMaleHtml.updateData(it)
        })
        //Female
        viewModel.sLoadingStoryFemale.observe(this@FragmentHome, Observer {
            if (it)
                progressStoryFeMale.visibility = View.INVISIBLE
            else
                progressStoryFeMale.visibility = View.VISIBLE
        })
        viewModel.storyStoryFemale.observe(this@FragmentHome, Observer {
            adapterStoryFemaleHtml.updateData(it)
        })
        //Schedule

        imgCalendar.setOnClickListener {
            viewModel.sLoadingSchedulers.observe(this@FragmentHome, Observer {
                var dialog = AlertDialog.Builder(activity!!)
                val view: View = LayoutInflater.from(activity!!).inflate(R.layout.fragment_schedule, null)
                val schedule = view.findViewById<RecyclerView>(R.id.rcvSchedule)
                val date = view.findViewById<TextView>(R.id.tvScheduleDate)
                date.text="Ngày cập nhật : ${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.MONTH)+1}-${cal.get(Calendar.YEAR)}"
                schedule.run {
                    adapter = adapterSchedule
                    layoutManager = LinearLayoutManager(activity)
                }
                adapterSchedule.updateData(it.list!!)
                dialog.setView(view)
                    .setIcon(R.drawable.ic_calendar)
                val dislay = dialog.create()
                dislay.setTitle("Lịch ra truyện")
                dislay.show()
            })

        }
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