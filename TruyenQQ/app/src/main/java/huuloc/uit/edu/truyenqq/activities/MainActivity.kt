package huuloc.uit.edu.truyenqq.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.fragments.home.FragmentHome
import huuloc.uit.edu.truyenqq.fragments.user.FragmentUser
import huuloc.uit.edu.truyenqq.fragments.home.ViewModelHome
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var idSelect = R.id.navHome
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.frmMain, FragmentHome())
            .commit()
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    showFragment(FragmentHome())
                    true
                }
                R.id.navUser -> {
                    showFragment(FragmentUser())
                    true
                }
                else -> {
                    println("### nothing")
                    true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("###")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frmMain, fragment).commit()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("4")
            .add(R.id.frmMain, fragment)
            .commit()
    }

    fun openActivityRank(view: View) {
        val intent =Intent(this, Activity_Rank::class.java)
        startActivity(intent)
    }

    fun showScheduler(view: View) {
        var dialog = AlertDialog.Builder(this)
        dialog.setView(R.layout.fragment_schedule)
        val dislay = dialog.create()
        dislay.setTitle("Lịch ra truyện")
        dislay.show()
    }

    fun showNewStory(view: View) {
        startActivity(Intent(this, Activity_NewStory::class.java))
    }
}