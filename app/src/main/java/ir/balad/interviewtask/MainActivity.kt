package ir.balad.interviewtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.balad.interviewtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // region of properties
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // END of region of properties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}