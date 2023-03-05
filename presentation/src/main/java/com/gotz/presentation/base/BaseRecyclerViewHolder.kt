package com.gotz.presentation.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/***
 * T: RecyclerItem
 * U: Binding 객체
 * itemView: 실제 사용될 뷰
 */
abstract class BaseRecyclerViewHolder<T : Any, U : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding = DataBindingUtil.bind<U>(itemView)

    abstract fun bindData(item: T)
}