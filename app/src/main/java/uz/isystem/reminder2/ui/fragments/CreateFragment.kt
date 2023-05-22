package uz.isystem.reminder2.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import uz.isystem.reminder2.R
import uz.isystem.reminder2.databinding.FragmentCreateBinding
import uz.isystem.reminder2.model.Notes
import uz.isystem.reminder2.viewmodel.NotesViewModel
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*


class CreateFragment : Fragment() {


    lateinit var binding: FragmentCreateBinding

    var priority:String="1"

    val viewModel:NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreateBinding.inflate(layoutInflater, container, false)

        binding.pGreen.setImageResource(R.drawable.ic_done)

        binding.pGreen.setOnClickListener {
            priority="1"
            binding.pGreen.setImageResource(R.drawable.ic_done)
            binding.pYellow.setImageResource(0)
            binding.pRed.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority="2"
            binding.pYellow.setImageResource(R.drawable.ic_done)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
        }

        binding.pRed.setOnClickListener {
            priority="3"
            binding.pRed.setImageResource(R.drawable.ic_done)
            binding.pYellow.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }
        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {

        val title=binding.edtTitle.text.toString()
        val subtitle=binding.edtSubTitle.text.toString()
        val notes=binding.edtNotes.text.toString()
        val notes2=binding.timeBtn.is24HourView.toString()
        val d= Date()
        val notesDate: CharSequence= DateFormat.format("MMMM d, yyyy ", d.time)

        val data= Notes(null,
            title = title, 
            subTitle=subtitle,
            notes = notes,
            date = notesDate.toString(),
           // priority,
            isCompleted = false)
        
        viewModel.addNotes(data)

        Toast.makeText(requireContext(), "Notes Created Succesfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createFragment_to_homeFragment)

    }

    private fun getParsedTime(completeTime: String): String {
        val old=completeTime.replace("T"," ").replace("Z","")
        val oldDate = SimpleDateFormat("yyyy-MM-dd HH:mm:sss").parse(old)
        val newDate = SimpleDateFormat("HH:mm dd-MMM yyyy").format(oldDate)
        return newDate
    }

}