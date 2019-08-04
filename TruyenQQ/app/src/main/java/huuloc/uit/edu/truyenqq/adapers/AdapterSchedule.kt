package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.ScheduleStory
import java.util.*

class AdapterSchedule(val context: Context, var list: List<ScheduleStory>) :
    RecyclerView.Adapter<AdapterSchedule.BaseItem>() {
    private val cal = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItem {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)
        return BaseItem(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        val p0 = list[list.size - position - 1]
        val time = p0.time_post
        holder.name.text = p0.name
        holder.time.run {
            text = "[${time.substring(11, 16)}]"
            if (p0.status.toInt() == 0) setTextColor(Color.parseColor("#00bf8f"))
        }
    }

    inner class BaseItem(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvScheduleText)
        var time = view.findViewById<TextView>(R.id.tvScheduleTime)
        var date = view.findViewById<TextView>(R.id.tvScheduleDate)
    }

    fun updateData(item: List<ScheduleStory>) {
        list = item
        notifyDataSetChanged()
    }
}

fun timeStampToString(second: Int): String {
    return when {
        second > 31536000 -> "${second / 31536000} Năm"
        second > 2592000 -> "${second / 2592000} Tháng"
        second >= 86400 -> "${second / 86400} Ngày"
        second >= 3600 -> "${second / 3600} Giờ"
        second >= 60 -> "${second / 60} Phút"
        else -> "Tức Thời"
    }
}