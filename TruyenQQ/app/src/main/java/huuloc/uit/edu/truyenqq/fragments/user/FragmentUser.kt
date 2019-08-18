package huuloc.uit.edu.truyenqq.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.DataLogin
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import kotlinx.android.synthetic.main.fragment_user.*

class FragmentUser : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val share = MysharedPreferences(activity!!)
    }
}