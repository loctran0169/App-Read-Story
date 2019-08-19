package huuloc.uit.edu.truyenqq.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.main.ViewModelHome
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import kotlinx.android.synthetic.main.fragment_user.*

class FragmentUser : Fragment() {
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(ViewModelHome::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val p0 = viewModel.dataLogin.value!!
        Glide.with(context!!)
            .load("http://avatar.mangaqq.com/160x160/"+p0.avatar)
            .into(imgAvatar)
    }
}