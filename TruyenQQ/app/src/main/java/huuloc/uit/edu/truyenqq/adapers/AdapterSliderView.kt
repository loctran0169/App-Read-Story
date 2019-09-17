package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ActivityStory
import huuloc.uit.edu.truyenqq.data.Slider

class AdapterSliderView(val context: Context, var list : List<Slider>)  : SliderViewAdapter<AdapterSliderView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_image_slider,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val p0 = list[position]
        Glide.with(context)
            .load(p0.image)
            .into(viewHolder.image)
        viewHolder.image.setOnClickListener {
            val intent1 = Intent(context, ActivityStory::class.java)
            val bundle = Bundle()
            bundle.putString("book_id", p0.bookId)
            intent1.putExtra("kind", bundle)
            context.startActivity(intent1)
        }
    }

    override fun getCount(): Int {
        return list.size
    }

    inner class ViewHolder(view : View) : SliderViewAdapter.ViewHolder(view) {
        var image = view.findViewById<ImageView>(R.id.imgImageSlider)
    }
}