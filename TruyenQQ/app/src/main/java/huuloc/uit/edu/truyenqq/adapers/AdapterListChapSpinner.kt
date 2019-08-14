package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.Chap

class AdapterListChapSpinner(val context: Context, var list: List<Chap>) : BaseAdapter() {
    class ViewHolder {
        lateinit var name: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewholder = ViewHolder()
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chaps, null)

            viewholder.name = view.findViewById(R.id.tvChapName) as TextView

            view.tag = viewholder
        } else {
            view = convertView
            viewholder = convertView.tag as ViewHolder
        }
        val chap = getItem(position) as Chap
        viewholder.name.text = chap.name
        return view as View
    }


    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}