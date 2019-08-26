package huuloc.uit.edu.truyenqq.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.main.MainActivity

class WaitingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
        Handler().postDelayed({
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
            finish()
        }, 1000)
    }
}
