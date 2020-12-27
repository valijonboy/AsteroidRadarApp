package uz.pop.astroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.R
import uz.pop.astroidradar.database.AsteroidDatabase
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.databinding.FragmentMainBinding
import uz.pop.astroidradar.repository.AsteroidRepository
import uz.pop.astroidradar.work.AsteroidActivity

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {

        ViewModelProvider(this).get(MainViewModel::class.java)
    }

   private var adapter : AsteroidAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val database = AsteroidDatabase.getDatabase(application)
        val repository = AsteroidRepository(database)

        val factory = MainViewModel.Factory(repository)
        val mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel

        binding.asteroidRecycler.adapter = adapter

        mainViewModel.asteroids.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.submitList(it)
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_all_menu -> viewModel.setFilter(AsteroidFilter.WEEKLY)
            R.id.show_rent_menu -> viewModel.setFilter(AsteroidFilter.TODAY)
            else -> viewModel.setFilter(AsteroidFilter.ALL)
        }
        return true
    }
}