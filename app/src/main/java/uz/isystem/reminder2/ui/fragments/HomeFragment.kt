package uz.isystem.reminder2.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.isystem.reminder2.R
import uz.isystem.reminder2.databinding.FragmentHomeBinding
import uz.isystem.reminder2.model.Notes
import uz.isystem.reminder2.ui.adapter.NotesAdapter
import uz.isystem.reminder2.viewmodel.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes= arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        val linearLayoutManager=LinearLayoutManager(requireContext())
        binding.rcvAllNotes.layoutManager=linearLayoutManager

        viewModel.getNotes().observe(viewLifecycleOwner,{notesList->
        oldMyNotes=notesList as ArrayList<Notes>
        adapter=NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter=adapter
        })

        binding.filterHigh.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner,{notesList->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter
            })
        }

        /*
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner,{notesList->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter
            })
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner,{notesList->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter
            })
        }
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner,{notesList->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter
            })
        }

        */



        binding.filterCompleted.setOnClickListener {
            viewModel.getCompleted().observe(viewLifecycleOwner,{notesList->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter=NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter=adapter
            })
        }


        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item=menu!!.findItem(R.id.app_bar_search)
        val searchView=item.actionView as SearchView
        searchView.queryHint="Enter Notes Here..."
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFiltering(p0)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(p0: String?) {

        val newFilteredList= arrayListOf<Notes>()
        for(i in oldMyNotes){
            if(i.title!!.contains(p0!!) || i.subTitle!!.contains(p0)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
        adapter.setTasks(oldMyNotes)
    }
}

