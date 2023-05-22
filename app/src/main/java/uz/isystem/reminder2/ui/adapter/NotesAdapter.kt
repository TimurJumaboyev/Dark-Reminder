package uz.isystem.reminder2.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import uz.isystem.reminder2.R
import uz.isystem.reminder2.databinding.ItemNotesBinding
import uz.isystem.reminder2.model.Notes
import uz.isystem.reminder2.ui.fragments.HomeFragment
import uz.isystem.reminder2.ui.fragments.HomeFragmentDirections
import java.text.SimpleDateFormat

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    private val data: ArrayList<Notes> = ArrayList()

    var onCheckBoxChanged:((task:Notes,position:Int)->Unit)?=null
    var onItemClicked:((task:Notes)->Unit)?=null



    fun setTasks(d: ArrayList<Notes>) {
        this.data.addAll(d)
        notifyItemRangeInserted(data.size - d.size, d.size)
    }

    fun filtering(newFilteredList: ArrayList<Notes>) {
        notesList=newFilteredList
        notifyDataSetChanged()
    }

   inner  class NotesVH( val binding: ItemNotesBinding):RecyclerView.ViewHolder(binding.root){

       fun bindData(notes: Notes){
           binding.notesTitle.text=notes.title
           binding.notesDate.text=getParsedTime(notes.date)
        binding.taskCheck.setImageResource(if (notes.isCompleted) R.drawable.checked else R.drawable.not_checked)

           if (notes.isCompleted) {
               binding.notesTitle.paintFlags = binding.notesTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
           }else{
               binding.notesTitle.paintFlags = binding.notesTitle.paintFlags and ( Paint.STRIKE_THRU_TEXT_FLAG.inv())
           }

           binding.taskCheck.setOnClickListener {
               val newTask = notes
               newTask.isCompleted=!notes.isCompleted
               onCheckBoxChanged?.invoke(newTask,adapterPosition)
           }

           binding.root.setOnClickListener {
               onItemClicked?.invoke(notes)
               val action=HomeFragmentDirections.actionHomeFragmentToEditFragment(notes)
               Navigation.findNavController(it).navigate(action)

           }

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {

        return NotesVH(ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        holder.bindData(data[position])
//        val data=notesList[position]
//        holder.binding.notesTitle.text=data.title
//        holder.binding.notesDate.text=data.date
//


       /* when (data.priority){
            "1"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green)
            } "2"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow)
            } "3"->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red)
            }
        }
        */


    }

    override fun getItemCount()=notesList.size

    private fun getParsedTime(completeTime: String): String {
        val old=completeTime.replace("T"," ").replace("Z","")
        val oldDate = SimpleDateFormat("yyyy-MM-dd HH:mm:sss").parse(old)
        val newDate = SimpleDateFormat("HH:mm dd-MMM yyyy").format(oldDate)
        return newDate
    }

}