package huuloc.uit.edu.truyenqq.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
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
import huuloc.uit.edu.truyenqq.activities.main.ViewModelHome
import huuloc.uit.edu.truyenqq.adapers.AdapterSchedule
import huuloc.uit.edu.truyenqq.adapers.AdapterVerticalRestFull
import huuloc.uit.edu.truyenqq.databinding.FragmentHomeBinding
import huuloc.uit.edu.truyenqq.recyclerview.SpaceItem
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class FragmentHome : Fragment() {
    val cal = Calendar.getInstance()
    private val adapterNewRestFull: AdapterVerticalRestFull by lazy {
        AdapterVerticalRestFull(activity!!, mutableListOf())
    }
    private val adapterStoryMale: AdapterVerticalRestFull by lazy {
        AdapterVerticalRestFull(activity!!, mutableListOf())
    }
    private val adapterStoryFemale: AdapterVerticalRestFull by lazy {
        AdapterVerticalRestFull(activity!!, mutableListOf())
    }
    private val adapterSchedule: AdapterSchedule by lazy {
        AdapterSchedule(activity!!, mutableListOf())
    }
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelHome::class.java)
    }
    var firstX = 0.0f
    var secondX = 0.0f
    var _new = false
    var _male = false
    var _female = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            adapter = adapterStoryMale
            addItemDecoration(SpaceItem(4))

        }
        rcvStoryFemale.run {
            layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            adapter = adapterStoryFemale
            addItemDecoration(SpaceItem(4))
        }
        //New
        viewModel.storyNew.observe(this@FragmentHome, Observer {
            adapterNewRestFull.updateData(it)
            progressNewStory.visibility = View.INVISIBLE
            _new = true
            if (_male && _female)
                refresh.isRefreshing = false
        })
        //Male
        viewModel.storyStoryMale.observe(this@FragmentHome, Observer {
            adapterStoryMale.updateData(it)
            progressStoryMale.visibility = View.INVISIBLE
            _male = true
            if (_new && _female)
                refresh.isRefreshing = false
        })
        //Female
        viewModel.storyStoryFemale.observe(this@FragmentHome, Observer {
            adapterStoryFemale.updateData(it)
            progressStoryFeMale.visibility = View.INVISIBLE
            _female = true
            if (_male && _new)
                refresh.isRefreshing = false
        })
        //Schedule
        imgCalendar.setOnClickListener {
            viewModel.sLoadingSchedulers.observe(this@FragmentHome, Observer {
                val dialog = AlertDialog.Builder(activity!!)
                val view: View = LayoutInflater.from(activity!!).inflate(R.layout.fragment_schedule, null)
                val schedule = view.findViewById<RecyclerView>(R.id.rcvSchedule)
                val date = view.findViewById<TextView>(R.id.tvScheduleDate)
                date.text =
                    "Ngày cập nhật : ${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.YEAR)}"
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
        lbCalendar.setOnClickListener {
            viewModel.sLoadingSchedulers.observe(this@FragmentHome, Observer {
                val dialog = AlertDialog.Builder(activity!!)
                val view: View = LayoutInflater.from(activity!!).inflate(R.layout.fragment_schedule, null)
                val schedule = view.findViewById<RecyclerView>(R.id.rcvSchedule)
                val date = view.findViewById<TextView>(R.id.tvScheduleDate)
                date.text =
                    "Ngày cập nhật : ${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.YEAR)}"
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
        refresh.setOnRefreshListener {
            progressNewStory.visibility = View.VISIBLE
            progressStoryMale.visibility = View.VISIBLE
            progressStoryFeMale.visibility = View.VISIBLE
            viewModel.refresh()
            val handle = Handler()
            handle.postDelayed({
                refresh.isRefreshing = false
            }, 200
            )
        }
        viewPagerSlider.setOnTouchListener { p0, event ->
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                firstX = event.x
            } else if (event.action == MotionEvent.ACTION_UP) {
                secondX = event.x
                if (firstX < secondX) {
                    if (viewPagerSlider.displayedChild != 1) {
                        viewPagerSlider.setInAnimation(activity!!, R.anim.in_from_left)
                        viewPagerSlider.setOutAnimation(activity!!, R.anim.out_from_left)
                        viewPagerSlider.showNext()
                    }
                }
                if (firstX > secondX) {
                    if (viewPagerSlider.displayedChild != 0) {
                        viewPagerSlider.setInAnimation(activity!!, R.anim.in_from_right)
                        viewPagerSlider.setOutAnimation(activity!!, R.anim.out_from_right)
                        viewPagerSlider.showPrevious()
                    }
                }
            }
            true
        }
    }
}