package huuloc.uit.edu.truyenqq.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseItem(view : View):RecyclerView.ViewHolder(view) {
    abstract fun bind(position: Int)
}