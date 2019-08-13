package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.reading.ActivityReading
import huuloc.uit.edu.truyenqq.data.Chap
import java.util.*

class AdapterListChap(val context: Context, var list: List<Chap>) : RecyclerView.Adapter<AdapterListChap.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_chaps, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = list[position]
        val cal = Calendar.getInstance()
        cal.timeInMillis = p0.created.toLong()*1000
        holder.chapName.text = p0.name
        holder.chapDate.text = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ActivityReading::class.java)
            val bundle = Bundle()
            bundle.putString("book_id", p0.book_id)
            bundle.putString("chap", p0.order)
            intent.putExtra("manga", bundle)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chapName = view.findViewById<TextView>(R.id.tvChapName)
        var chapDate = view.findViewById<TextView>(R.id.tvChapDate)
    }
    fun updateData(items : List<Chap>){
        list= items
        notifyDataSetChanged()
    }
}