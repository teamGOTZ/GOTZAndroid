package com.gotz.presentation.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/***
 * T: RecyclerItem
 * U: Binding 객체
 * id: 레이아웃 아이디
 */
abstract class BaseRecyclerAdapter<T : Any, U : ViewDataBinding>(@LayoutRes val id: Int) : RecyclerView.Adapter<BaseRecyclerViewHolder<T, U>>() {
    private val items = mutableListOf<T>()

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<T, U> {
        return createHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T, U>, position: Int) {
        holder.bindData(items[position])


        onItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener { view ->
                listener.onItemClick(view, position)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    abstract fun createHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<T, U>

    fun initItems(items: List<T>) {
        if (items.isNotEmpty()) {
            this.items.run {
                clear()
                addAll(items)
            }
            notifyDataSetChanged()
        }
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun getItem(index: Int): T = this.items[index]

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}