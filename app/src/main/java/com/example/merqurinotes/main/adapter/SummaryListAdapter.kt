package com.example.merqurinotes.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.merqurinotes.R
import com.example.merqurinotes.base.model.SummarizeData
import com.example.merqurinotes.databinding.ListSummarizeCategoryItemBinding

class SummaryListAdapter(private val mList: List<SummarizeData>) :
    RecyclerView.Adapter<SummaryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_summarize_category_item, parent, false)
        //val inflater = LayoutInflater.from(parent.context)
        //val binding = ListSummarizeCategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mList[position]
        holder.categoryTextView.text = items.category
        holder.totalContentTextView.text = holder.itemView.context.getString(
            R.string.summarize_count_statement,
            items.totalCategoryRecords
        )
        //val arrayCategory = holder.itemView.context.resources.getStringArray(R.array.category_array)

        when (items.category) {
            "Work and Study" -> holder.categoryImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.work_study_icon
                )
            )

            "Life" -> holder.categoryImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.life_icon
                )
            )

            "Health and Well-being" -> holder.categoryImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.health_and_well_being_icon
                )
            )

            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = this.itemView.findViewById(R.id.category_tv)
        val totalContentTextView: TextView = this.itemView.findViewById(R.id.total_content_tv)
        val categoryImageView: ImageView = this.itemView.findViewById(R.id.category_iv)
        init {
            val detailButton : AppCompatButton = this.itemView.findViewById(R.id.details_btn)
            detailButton.setOnClickListener {
                Toast.makeText(this.itemView.context,"Pressed",Toast.LENGTH_SHORT).show()
            }
        }
    }

//    class ViewHolder(viewBinding: ListSummarizeCategoryItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
//        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
//        //val textView: TextView = itemView.findViewById(R.id.textView)
//    }
}