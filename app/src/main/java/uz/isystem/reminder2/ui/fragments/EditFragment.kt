package uz.isystem.reminder2.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.isystem.reminder2.R
import uz.isystem.reminder2.databinding.FragmentEditBinding
import uz.isystem.reminder2.model.Notes
import uz.isystem.reminder2.viewmodel.NotesViewModel
import java.util.*

@Suppress("DEPRECATION")
class EditFragment : Fragment() {

    val oldNotes by navArgs<EditFragmentArgs>()
    lateinit var binding: FragmentEditBinding
    var priority:String="1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentEditBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.edtSubTitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.notes)
    /*
        when (oldNotes.data.priority){
            "1"->{
                priority="1"
                binding.pGreen.setImageResource(R.drawable.ic_done)
                binding.pYellow.setImageResource(0)
                binding.pRed.setImageResource(0)
            }
            "2"->{
                priority="2"
                binding.pYellow.setImageResource(R.drawable.ic_done)
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(0)
            }
            "3"->{
                priority="3"
                binding.pRed.setImageResource(R.drawable.ic_done)
                binding.pYellow.setImageResource(0)
                binding.pGreen.setImageResource(0)
        }
     }
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
    */
        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }


        return binding.root
    }

    private fun updateNotes(it: View?) {

        val title=binding.edtTitle.text.toString()
        val subtitle=binding.edtSubTitle.text.toString()
        val notes=binding.edtNotes.text.toString()
        val d= Date()
        val notesDate: CharSequence= DateFormat.format("MMMM d, yyyy ", d.time)

        val data= Notes(oldNotes.data.id,
            title = title,
            subTitle=subtitle,
            notes = notes,
            date = notesDate.toString(),
//            priority,
            isCompleted=false)

        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "Notes Updated Succesfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editFragment_to_homeFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){

            val bottomSheet:BottomSheetDialog= BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val tvYes=bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val tvNo=bottomSheet.findViewById<TextView>(R.id.dialog_no)

            tvYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()
                Navigation.findNavController(it!!).navigate(R.id.action_editFragment_to_homeFragment)

            }

            tvNo?.setOnClickListener {
                bottomSheet.dismiss()

            }


            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }
}