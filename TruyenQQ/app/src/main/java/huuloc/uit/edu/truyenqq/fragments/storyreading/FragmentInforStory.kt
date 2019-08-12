package huuloc.uit.edu.truyenqq.fragments.storyreading

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ViewModelStory
import kotlinx.android.synthetic.main.informaion_story.*
import java.util.*

class FragmentInforStory : Fragment() {
    val viewModel: ViewModelStory by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelStory::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.informaion_story, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.Story.observe(this@FragmentInforStory, androidx.lifecycle.Observer {
            val cal = Calendar.getInstance()
            cal.timeInMillis = (it.modified) * 1000
            tvStoryDate.text =
                "Cập nhật: " + "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
            if (it.name_orignal.isNullOrEmpty())
                tvStoryOriginal.text = ""
            else
                tvStoryOriginal.text = "Tên khác: " + it.name_orignal
            if (!it.info.isNullOrEmpty())
                expandableTextView.text = "Nội dung:\t" + Html.fromHtml(it.info!!)
        })

    }
}