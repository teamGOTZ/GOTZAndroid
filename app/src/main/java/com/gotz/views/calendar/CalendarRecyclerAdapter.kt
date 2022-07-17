package com.gotz.views.calendar

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotz.databinding.RecyclerCalendarBinding
import com.gotz.util.CalendarUtil.Companion.charDayOfWeek
import com.gotz.util.CalendarUtil.Companion.getStrTime
import com.gotz.views.calendar.memo.CalendarMemoDetailActivity
import com.gotz.views.frame.FrameActivity
import org.joda.time.DateTime

class CalendarRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder>() {
    var lists = mutableListOf<CalendarRecyclerItem>()

    inner class ViewHolder(private val binding: RecyclerCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarRecyclerItem, flag:Boolean = false) = with(binding){
            data = item

            if(flag) binding.clRecyclerCalendar.setPadding(dp2px(20F).toInt(), dp2px(32F).toInt(),dp2px(20F).toInt(), dp2px(12F).toInt())
            if(itemViewType == 1){
                binding.tvRecyclerTitle.visibility = View.GONE
                binding.tvRecyclerTime.visibility = View.GONE

                binding.ivRecyclerMargin1.visibility = View.GONE
                binding.ivRecyclerMargin2.visibility = View.GONE

                binding.tvRecyclerDay.text = item.idx.toString()
                binding.tvRecyclerDayofweek.text = "(" + charDayOfWeek(DateTime(item.start).dayOfWeek) + ")"

                binding.clRecyclerCalendar.setBackgroundColor(Color.parseColor("#FAFAFA"))

            }else if(itemViewType == -2){
                binding.clRecyclerCalendar.setPadding(dp2px(0F).toInt(), dp2px(0F).toInt(),dp2px(0F).toInt(), dp2px(0F).toInt())

                binding.tvRecyclerTitle.visibility = View.GONE
                binding.tvRecyclerTime.visibility = View.GONE
                binding.tvRecyclerDay.visibility = View.GONE
                binding.tvRecyclerDaytext.visibility = View.GONE
                binding.tvRecyclerDayofweek.visibility = View.GONE
                if(item.start == 1L){
                    binding.ivRecyclerMargin1.visibility = View.GONE
                    binding.ivRecyclerMargin2.visibility = View.VISIBLE
                }else if (item.end == 1L){
                    binding.ivRecyclerMargin1.visibility = View.VISIBLE
                    binding.ivRecyclerMargin2.visibility = View.GONE
                }else{
                    binding.ivRecyclerMargin1.visibility = View.VISIBLE
                    binding.ivRecyclerMargin2.visibility = View.VISIBLE
                }

            }else if(itemViewType == -3){
                binding.tvRecyclerDay.visibility = View.GONE
                binding.tvRecyclerDaytext.visibility = View.GONE
                binding.tvRecyclerDayofweek.visibility = View.GONE
                binding.tvRecyclerTime.visibility = View.GONE

                binding.ivRecyclerMargin1.visibility = View.GONE
                binding.ivRecyclerMargin2.visibility = View.GONE


                binding.tvRecyclerTitle.setTextColor(Color.parseColor("#BDBDBD"))
                binding.clRecyclerCalendar.setBackgroundColor(Color.parseColor("#FAFAFA"))

            }else{
                binding.tvRecyclerDay.visibility = View.GONE
                binding.tvRecyclerDaytext.visibility = View.GONE
                binding.tvRecyclerDayofweek.visibility = View.GONE

                binding.ivRecyclerMargin1.visibility = View.GONE
                binding.ivRecyclerMargin2.visibility = View.GONE


                if(item.isAllDay){
                    binding.tvRecyclerTime.visibility = View.GONE
                }
                else{
                    binding.tvRecyclerTime.visibility = View.VISIBLE
                    if(getStrTime(DateTime(item.start)).equals(getStrTime(DateTime(item.end)))){
                        binding.tvRecyclerTime.text = getStrTime(DateTime(item.start))
                    }else{
                        binding.tvRecyclerTime.text = getStrTime(DateTime(item.start)) + " - " + getStrTime(DateTime(item.end))
                    }
                }

                binding.clRecyclerCalendar.setBackgroundColor(Color.parseColor("#FAFAFA"))

                binding.clRecyclerCalendar.setOnClickListener {
                    (context as FrameActivity).openActivityForResult(CalendarMemoDetailActivity(), item.uid)
                }
            }
        }
    }

    fun getListPosition(position: Int):Int{
        if(position == 0 || getItemViewType(position) != 0 || lists.get(position).isAllDay) return 0
        return getListPosition(position-1) + 1
    }

    override fun getItemViewType(position: Int): Int {
        val key = lists.get(position).idx
        if(key >= 0) return 1
        else if(key == -2 )return key
        else if(key == -3 )return key
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position!= 0)holder.bind(lists.get(position))
        else holder.bind(lists.get(position), true)
    }

    override fun getItemCount(): Int = lists.size

    fun setData(data: ArrayList<CalendarRecyclerItem>){
        lists = data
        notifyDataSetChanged()
    }

    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}