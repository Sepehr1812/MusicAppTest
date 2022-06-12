package ir.balad.interviewtask.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.balad.interviewtask.databinding.ItemDataBinding
import ir.balad.interviewtask.model.DataModel

class DataAdapter(
    private val data: List<DataModel>
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = data[position]

            tvItemTitle.text = item.title
            root.backgroundTintList = ColorStateList.valueOf(item.color)
        }
    }

    override fun getItemCount() = data.size
}