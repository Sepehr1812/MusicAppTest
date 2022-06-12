package ir.balad.interviewtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import ir.balad.interviewtask.adapter.DataAdapter
import ir.balad.interviewtask.databinding.ActivityMainBinding
import ir.balad.interviewtask.model.DataModel

class MainActivity : AppCompatActivity() {

    // region of properties
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // END of region of properties

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialView()
    }

    private fun initialView() {
        val data = DataModel.generateMockList()
        val gridLayoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    // if position of item is dividable by 3 use two spans, otherwise use just one of them
                    if (position.plus(1).mod(3) == 0) 2 else 1
            }
        }

        with(binding.rvItemList) {
            layoutManager = gridLayoutManager
            adapter = DataAdapter(data)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}